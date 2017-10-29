import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FrequencyAnalysis implements Analysis{
    static Map<Character, Double> frequencyMap(String s, boolean ignoreCase){
        Map<Character, Double> map = new HashMap<>();
        if (ignoreCase){
            s = s.toUpperCase();
        }
        for (int i = 0; i < s.length(); i++){
            if (map.containsKey(s.charAt(i))) {
                map.put(s.charAt(i), map.get(s.charAt(i)) + 1.0);
            }
            else{
                map.put(s.charAt(i), 1.0);
            }
        }
        for (Map.Entry<Character, Double> entry : map.entrySet()){
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
    }
}
