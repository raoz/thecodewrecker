#!/usr/bin/env bash
# Headless smoke test: run the console cracker (CUI.CodeWrecker) from the
# built jar on test.enc and check that the best decryption matches test.dec.
# Usage: scripts/test.sh <path-to-thecodewrecker.jar>
set -euo pipefail
cd "$(dirname "$0")/.."
JAR="$(cd "$(dirname "$1")" && pwd)/$(basename "$1")"

mkdir -p build/test
run() { # $1 = comma separated analysis files, $2 = output file
  # Answers to the interactive prompts: analysis files, number of solutions,
  # input file, output file.
  printf '%s\n5\ntest.enc\n%s\n' "$1" "$2" | java -cp "$JAR" CUI.CodeWrecker > /dev/null
}

# Expected best solution = first block of test.dec (header line + plaintext
# up to the next block header).
awk 'NR>1 && /^--------Core.Decryption/{exit} {print}' test.dec > build/test/expected-best.txt
expected_plain="$(tail -n +2 build/test/expected-best.txt)"

status=0
for tables in estcharfreq.txt estsylfreq.txt data/estcharmarkov.txt data/estsylmarkov.txt; do
  out="build/test/$(basename "$tables" .txt).dec"
  run "$tables" "$out"
  awk 'NR>1 && /^--------Core.Decryption/{exit} {print}' "$out" > "$out.best"
  echo "== $tables: $(head -n 1 "$out.best")"
  if cmp -s "$out" test.dec; then echo "   output identical to test.dec"; fi
  if [ "$(tail -n +2 "$out.best")" = "$expected_plain" ] && head -n 1 "$out.best" | grep -q 'key=17}$'; then
    echo "   PASS: best decryption (key=17) matches test.dec"
  else
    echo "   FAIL: best decryption differs from test.dec"
    diff <(head -n 5 build/test/expected-best.txt) <(head -n 5 "$out.best") || true
    status=1
  fi
done
# test.dec was generated with the Estonian syllable table; the whole output
# (all 5 ranked solutions and confidences) must be reproduced exactly.
if cmp -s build/test/estsylfreq.dec test.dec; then
  echo "PASS: output with estsylfreq.txt is identical to test.dec"
else
  echo "FAIL: output with estsylfreq.txt differs from test.dec"
  diff test.dec build/test/estsylfreq.dec | head -n 20 || true
  status=1
fi
exit $status
