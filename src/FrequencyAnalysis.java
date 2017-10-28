import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class FrequencyAnalysis<T> implements Analysis{
    static <T> Map<T, Double> frequencyMap(String s, Function<String, List<T>> tokenizer, boolean ignoreCase){
        Map<T, Double> map = new HashMap<>();
        if (ignoreCase){
            s = s.toUpperCase();
        }
        List<T> tokens = tokenizer.apply(s);
        for (int i = 0; i < s.length(); i++){
            if (map.containsKey(tokens.get(i))) {
                map.put(tokens.get(i), map.get(tokens.get(i)) + 1.0);
            }
            else{
                map.put(tokens.get(i), 1.0);
            }
        }
        for (Map.Entry<T, Double> entry : map.entrySet()){
            map.put(entry.getKey(), entry.getValue() / s.length());
        }
        return map;
    }
    public static void main(String[] args){
        String in = Util.readWholeStream(System.in);
        System.out.println(frequencyMap(in, NaturalLanguage::characters, true));
    }

    @Override
    public double similarity(String other) {
        return 0;
    }
}
