import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

public class FrequencyAnalysis<T> implements Analysis{
    Map<T, Double> map = new HashMap<>();
    Function<String, List<T>> tokenizer;

    FrequencyAnalysis(String s, Function<String, List<T>> tokenizer, boolean ignoreCase) {
        this.tokenizer = tokenizer;
        if (ignoreCase) {
            s = s.toUpperCase();
        }
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
        FrequencyAnalysis<Character> characterFrequencyAnalysis = new FrequencyAnalysis<>(NaturalLanguage::characters);
        characterFrequencyAnalysis.fromString(Util.readWholeStream(new FileInputStream(fr)));
        System.out.println(characterFrequencyAnalysis);
        String in = Util.readWholeStream(System.in);
        System.out.println(new FrequencyAnalysis<>(in, NaturalLanguage::characters, true));
    }

    @Override
    public double similarity(String other) {
        return 0;
    }

}
