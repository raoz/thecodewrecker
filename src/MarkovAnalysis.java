import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Override
    public double similarity(String other) {
        return this.similarity(new MarkovAnalysis<>(other, tokenizer));
    }

    public double similarity(MarkovAnalysis<T> other) {
        Map<T, Map<T, Double>> f1 = this.getData();
        Map<T, Map<T, Double>> f2 = other.getData();
        Set<T> tokens = Stream.concat(f1.keySet().stream(), f2.keySet().stream()).collect(Collectors.toSet());
        double difference = 0;
        for (T token : tokens) {
            if(!(f1.containsKey(token) && f2.containsKey(token))) {
                difference += 1;
                continue;
            }
            Map<T, Double> sf1 = f1.get(token);
            Map<T, Double> sf2 = f2.get(token);
            Set<T> subTokens = Stream.concat(sf1.keySet().stream(), sf2.keySet().stream()).collect(Collectors.toSet());
            difference += subTokens.stream().
                    mapToDouble(k -> sf1.getOrDefault(k, 0.0) + sf2.getOrDefault(k, 0.0))
                    .sum();
        }
        difference = difference / tokens.size();
        return 1 - difference;
    }
}
