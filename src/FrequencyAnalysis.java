import java.util.HashMap;
import java.util.Map;

public class FrequencyAnalysis {
    Map<Character, Integer> frequencyMap(String s, boolean ignoreCase){
        Map<Character, Integer> map = new HashMap<>();
        if (ignoreCase){
            s = s.toUpperCase();
        }
        for (int i = 0; i < s.length(); i++){
            if (map.containsKey(s.charAt(i))) {
                map.put(s.charAt(i), map.get(s.charAt(i)) + 1);
            }
            else{
                map.put(s.charAt(i), 1);
            }
        }
        System.out.println(map);
        return map;
    }
    public static void main(String[] args){
        FrequencyAnalysis fa = new FrequencyAnalysis();
        String in = Util.readWholeStream(System.in);
        fa.frequencyMap(in, true);
    }
}
