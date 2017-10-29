import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

public class FrequencyAnalysis<T> implements Analysis{
    static <T> Map<T, Double> frequencyMap(String s, Function<String, List<T>> tokenizer, boolean ignoreCase) {
        Map<T, Double> map = new HashMap<>();
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
        return map;
    }
    static Map<Character, Double> readMapFromFile(File fail) throws Exception{
        Scanner sc = new Scanner(fail, "UTF-8");
        Map<Character, Double> map = new HashMap<>();
        while (sc.hasNextLine()) {
            String rida = sc.nextLine();
            String[] tükid = rida.split("   ");
            map.put(tükid[0].charAt(0), Double.parseDouble(tükid[1]));

        }
        return map;
    }

    public static void main(String[] args) throws Exception{
        //String in = Util.readWholeStream(System.in);
        //System.out.println(frequencyMap(in, true));
        //Map<Character, Double> thisMap = frequencyMap(in, true);
        File fr = new File("frequency.txt");
        System.out.println(readMapFromFile(fr));
        String in = Util.readWholeStream(System.in);
        System.out.println(frequencyMap(in, NaturalLanguage::characters, true));
    }

    @Override
    public double similarity(String other) {
        return 0;
    }

    @Override
    public void fromString(String lines) {
    }
}
