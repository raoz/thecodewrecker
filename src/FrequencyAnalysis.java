import java.util.HashMap;
import java.util.Map;

public class FrequencyAnalysis implements Analysis {
    static Map<Character, Double> frequencyMap(String s, boolean ignoreCase) {
        Map<Character, Double> map = new HashMap<>();
        if (ignoreCase) {
            s = s.toUpperCase();
        }
        for (int i = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                map.put(s.charAt(i), map.get(s.charAt(i)) + 1.0);
            } else {
                map.put(s.charAt(i), 1.0);
            }
        }
        for (Map.Entry<Character, Double> entry : map.entrySet()) {
            map.put(entry.getKey(), entry.getValue() / s.length());
        }
        return map;
    }

    public static void main(String[] args) {
        String in = Util.readWholeStream(System.in);
        System.out.println(frequencyMap(in, true));
    }
}
