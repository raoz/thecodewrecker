import jdk.nashorn.internal.ir.FunctionCall;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class MarkovAnalysis<T> implements Analysis{
    private Function<String, List<T>> tokenizer;
    private final Map<T, Map<T, Double>> markovTable = new HashMap<>();

    public void addData(String s) {
        List<T> tokens = tokenizer.apply(s);
        for (int i = 0; i < tokens.size() - 1; i++) { //Iterate over every adjacent pair of tokens
            T tok = tokens.get(i);
            T tokNext = tokens.get(i+1);
            markovTable.putIfAbsent(tok, new HashMap<>());
            Map<T, Double> occTable = markovTable.get(tok);
            occTable.putIfAbsent(tokNext, 0.0);
            occTable.compute(tokNext, (__, n) -> n+1);
        }
        for (Map<T, Double> occTable : markovTable.values()) {
            double sum = occTable.values().stream().mapToDouble(d->d).sum();
            occTable.replaceAll((__, val) -> val/sum);
        }

    }

    /**
     * @param s String to analyse
     */
   public MarkovAnalysis(String s, Function<String, List<T>> tokenizer) {
       this.tokenizer = tokenizer;
       addData(s);
   }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (Map.Entry<T, Map<T, Double>> markovPresent : markovTable.entrySet()) {
            b.append(markovPresent.getKey());
            b.append('\n');
            markovPresent.getValue().forEach((key, value) -> {
                b.append('\t');
                b.append(key);
                b.append('\t');
                b.append(value);
                b.append('\n');
            });
        }
        return b.toString();
    }

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        MarkovAnalysis<String> sa = new MarkovAnalysis<>("", NaturalLanguage::naiveSyllables);
        br.lines().forEach(sa::addData);
        System.out.println(sa);
    }
}
