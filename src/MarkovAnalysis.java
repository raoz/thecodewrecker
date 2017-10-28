import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class MarkovAnalysis<T> implements Analysis{
    private Function<String, List<T>> tokenizer;
    private Map<T, Map<T, Long>> markovOccTable = new HashMap<>();
    private T lastToken = null;

    /**
     * Add data to the occurrence Markov table
     * @param s String to tokenize and add
     */
    public void addData(String s) {
        List<T> tokens = tokenizer.apply(s);
        if(lastToken != null) {
            tokens.add(0, lastToken);
        }
        for (int i = 0; i < tokens.size() - 1; i++) { //Iterate over every adjacent pair of tokens
            T tok = tokens.get(i);
            T tokNext = tokens.get(i+1);
            markovOccTable.putIfAbsent(tok, new HashMap<>());
            Map<T, Long> occTable = markovOccTable.get(tok);
            occTable.putIfAbsent(tokNext, 0L);
            occTable.compute(tokNext, (__, n) -> n+1);
        }
        if(tokens.size() > 0) {
            lastToken = tokens.get(tokens.size() - 1);
        }
    }

    /**
     * @return The Markov frequency table
     */
    private Map<T, Map<T, Double>> getData() {
        Map<T, Map<T, Double>> freqTable = new HashMap<>();
        for (Map.Entry<T, Map<T, Long>> occEntry : markovOccTable.entrySet()) {
            Map<T, Double> freqSubTable = new HashMap<>();
            Double sum = occEntry.getValue().values().stream().mapToDouble(d->d).sum();
            occEntry.getValue().forEach((key, val) -> freqSubTable.put(key, val/sum));
            freqTable.put(occEntry.getKey(), freqSubTable);
        }
        return freqTable;
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
        for (Map.Entry<T, Map<T, Double>> markovPresent : getData().entrySet()) {
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

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        MarkovAnalysis<String> sa = new MarkovAnalysis<>("", NaturalLanguage::naiveSyllables);
        String line;
        while((line = br.readLine()) != null) {
            sa.addData(line);
        }
        System.out.println(sa);
    }
}
