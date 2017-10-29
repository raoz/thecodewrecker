import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FrequencyAnalysis<T> implements Analysis{
    private Map<T, Double> map = new HashMap<>();
    Function<String, List<T>> tokenizer;

    FrequencyAnalysis(String s, Function<String, List<T>> tokenizer) {
        this.tokenizer = tokenizer;
        s = s.toUpperCase();
        List<T> tokens = tokenizer.apply(s);
        for (int i = 0; i < s.length(); i++) {
            if (map.containsKey(tokens.get(i))) {
                map.put(tokens.get(i), map.get(tokens.get(i)) + 1.0);
            } else {
                map.put(tokens.get(i), 1.0);
            }
        }
        for (Map.Entry<T, Double> entry : map.entrySet()) {
            map.put(entry.getKey(), entry.getValue() / s.length());
        }
    }

    public FrequencyAnalysis(Function<String, List<T>> tokenizer) {
        this.tokenizer = tokenizer;
    }

    @Override
   public void fromString(String s) {
        Function<String, T> read = tokenizer.andThen(a -> a.stream().findFirst().orElse(null)); //Get the first token
        Scanner sc = new Scanner(s);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] pieces = line.split("\t");
            map.put(read.apply(pieces[0]), Double.parseDouble(pieces[1]));
        }
    }

    public static void main(String[] args) throws Exception{
        //String in = Util.readWholeStream(System.in);
        //System.out.println(frequencyMap(in, true));
        //Map<Character, Double> thisMap = frequencyMap(in, true);
        File fr = new File("frequency.txt");
        FrequencyAnalysis<Character> characterFrequencyAnalysis = new FrequencyAnalysis<Character>(NaturalLanguage::characters);
        characterFrequencyAnalysis.fromString(Util.readWholeStream(new FileInputStream(fr)));
        System.out.println(characterFrequencyAnalysis);
        String in = Util.readWholeStream(System.in);
        System.out.println(new FrequencyAnalysis<Character>(in, NaturalLanguage::characters));
    }
    @Override
    public double similarity(String other) {
        return this.similarity(new FrequencyAnalysis<T>(other, tokenizer));
    }

    public Map<T, Double> getMap() {
        return map;
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
