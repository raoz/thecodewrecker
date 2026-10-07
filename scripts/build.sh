#!/usr/bin/env bash
# Build thecodewrecker.jar with plain javac + jar (no build tool needed)
# and package it with the data tables into dist/codewrecker-<version>.zip,
# using the same layout as the original 0.0.2 release.
# Usage: scripts/build.sh [version]
set -euo pipefail
cd "$(dirname "$0")/.."

VERSION="${1:-dev}"
NAME="codewrecker-${VERSION}"

rm -rf build dist
mkdir -p build/classes "dist/${NAME}"

# Sources target Java 8 (IntelliJ project language level JDK_1_8)
find src -name '*.java' > build/sources.txt
javac --release 8 -encoding UTF-8 -Xlint:unchecked -d build/classes @build/sources.txt

# Resource bundles used by the GUI
cp src/*.properties build/classes/

# Jar with GUI.GUI as Main-Class (from src/META-INF/MANIFEST.MF)
jar cfm "dist/${NAME}/thecodewrecker.jar" src/META-INF/MANIFEST.MF -C build/classes .

# Analysis tables beside the jar
for f in engcharfreq engcharmarkov engsylfreq engsylmarkov \
         estcharfreq estcharmarkov estsylfreq estsylmarkov; do
  cp "data/${f}.txt" "dist/${NAME}/"
done

(cd dist && zip -qr "${NAME}.zip" "${NAME}")
echo "Built dist/${NAME}.zip"
unzip -l "dist/${NAME}.zip"
