import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FrequencyAnalysis<T> implements Analysis{
    /**
     * Frequency map: For each element, value in [0..1] of how often it occurs
     */
    private Map<T, Double> freqMap = new HashMap<>();
    /**
     * Occurance map: For each element, number of times it has occured
     */
    private Map<T, Long> occMap = new HashMap<>();
    /**
     * Function to get tokens from a string
     */
    private final Function<String, List<T>> tokenizer;

    public FrequencyAnalysis(Function<String, List<T>> tokenizer) {
        this.tokenizer = tokenizer;
    }

    private FrequencyAnalysis(String s, Function<String, List<T>> tokenizer) {
        this.tokenizer = tokenizer;
        addData(s);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("MarkovAnalysis\n");
        b.append(freqMap.keySet().stream().findFirst().orElse(null).getClass().getSimpleName()); //HACK, get the type of first key in map
        b.append('\n');
        getMap().forEach((key, value) -> {
            b.append(key);
            b.append('\t');
            b.append(value);
            b.append('\n');
        });
        return b.toString();
    }

    /**
     * @param s String to read the table from
     */
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

    /**
     * @param data Additional data to analyse
     */
    @Override
    public void addData(String data) {
        if(occMap == null) {
            throw new IllegalStateException("Frequency analysis has no occurrence table.");
        }
        freqMap = null; //Reset the frequency map
        data = data.toUpperCase();
        List<T> tokens = tokenizer.apply(data);
        for (T token : tokens) {
            if (occMap.containsKey(token)) {
                occMap.put(token, occMap.get(token) + 1);
            } else {
                occMap.put(token, 1L);
            }
        }
    }


    /**
     * @return The frequency table
     */
    Map<T, Double> getMap() {
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

    /**
     * @param other string to comparatively analyse
     * @return the likelihood of the other string being of the same type as analysed
     */
    @Override
    public double similarity(String other) {
        return this.similarity(new FrequencyAnalysis<>(other, tokenizer));
    }

    /**
     * @param other Frequency analysis to compare to
     * @return 1 — average difference of the frequency maps
     */
    double similarity(FrequencyAnalysis<T> other){
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
