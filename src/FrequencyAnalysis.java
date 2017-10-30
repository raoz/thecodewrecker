import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FrequencyAnalysis<T> implements Analysis{
    private Map<T, Double> freqMap = new HashMap<>();
    private Map<T, Long> occMap = new HashMap<>();
    Function<String, List<T>> tokenizer;

    FrequencyAnalysis(String s, Function<String, List<T>> tokenizer) {
        this.tokenizer = tokenizer;
    }

    public FrequencyAnalysis(Function<String, List<T>> tokenizer) {
        this.tokenizer = tokenizer;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        getMap().forEach((key, value) -> {
            b.append(key);
            b.append('\t');
            b.append(value);
            b.append('\n');
        });
        return b.toString();
    }

    @Override
   public void fromString(String s) {
        occMap = null;
        Function<String, T> read = tokenizer.andThen(a -> a.stream().findFirst().orElse(null)); //Get the first token
        Scanner sc = new Scanner(s);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] pieces = line.split("\t");
            freqMap.put(read.apply(pieces[0]), Double.parseDouble(pieces[1]));
        }
    }

    @Override
    public void addData(String data) {
        if(occMap == null) {
            throw new IllegalStateException("Frequency analysis has no occurrence table.");
        }
        freqMap = null; //Reset the frequency map
        data = data.toUpperCase();
        List<T> tokens = tokenizer.apply(data);
        for (int i = 0; i < data.length(); i++) {
            if (occMap.containsKey(tokens.get(i))) {
                occMap.put(tokens.get(i), occMap.get(tokens.get(i)) + 1);
            } else {
                occMap.put(tokens.get(i), 1L);
            }
        }
    }

    @Override
    public double similarity(String other) {
        return this.similarity(new FrequencyAnalysis<>(other, tokenizer));
    }

    public Map<T, Double> getMap() {
        if(freqMap != null) {
            return freqMap;
        }
        freqMap = new HashMap<>();
        long sum = occMap.values().stream().mapToLong(l->l).sum();
        for (Map.Entry<T, Long> entry : occMap.entrySet()) {
            freqMap.put(entry.getKey(), (double)entry.getValue() / sum);
        }
        return freqMap;
    }

    public double similarity(FrequencyAnalysis<T> other){
        Map<T, Double> f1 = this.getMap();
        Map<T, Double> f2 = other.getMap();
        double difference = 0;
        Set<T> subTokens = Stream.concat(f1.keySet().stream(), f2.keySet().stream()).collect(Collectors.toSet());
        difference += subTokens.stream().
                mapToDouble(k ->
                        Math.abs(f1.getOrDefault(k, 0.0) - f2.getOrDefault(k, 0.0))
                ).sum() / subTokens.size();

        return 1 - difference;
    }

}
