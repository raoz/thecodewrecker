import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SyllableAnalysis implements Analysis{
    private Map<String, Map<String, Double>> markovTable;
    private static final Pattern reverseSyllablePattern = Pattern.compile(
            "[bcdfghjklmnpqrstvwxz]*[aeiouy]+[bcdfghjklmnpqrstvwxz]?([bcdfghjklmnpqrstvwxz]*\b)?");

    /**
     * @param s String to analyse
     */
   public SyllableAnalysis(String s) {
       List<String> syllables = naïveSyllables(s);
       this.markovTable = new HashMap<>();
       for (int i = 0; i < syllables.size() - 1; i++) { //Iterate over every adjacent pair of syllables
           String syl = syllables.get(i);
           String syln = syllables.get(i+1);
           markovTable.putIfAbsent(syl, new HashMap<>());
           Map<String, Double> occTable = markovTable.get(syl);
           occTable.putIfAbsent(syln, 0.0);
           occTable.compute(syln, (__,n) -> n+1);
       }
       for (Map<String, Double> occTable : markovTable.values()) {
           double sum = occTable.values().stream().mapToDouble(d->d).sum();
           occTable.replaceAll((__, val) -> val/sum);
       }
   }

    /**
     * Gets syllables, naïvely, ignoring language
     * @param s String to get syllables from
     * @return List of syllables in order.
     */
    static List<String> naïveSyllables(String s) {
        String normalized = Normalizer.normalize(s, Normalizer.Form.NFD);
        String stripped = normalized.replaceAll("[^A-Za-z\\s]+", ""); //Remove unnecessary characters
        String reverse = new StringBuilder(stripped).reverse().toString(); //Reverse the string
        Matcher revM = reverseSyllablePattern.matcher(reverse.toLowerCase()); //Find the reverse syllables
        ArrayList<String> syllables = new ArrayList<>();
        while(revM.find()){ //Add them to the list
            syllables.add(0, Util.reverseString(revM.group()));
        }
        return syllables;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (Map.Entry<String, Map<String, Double>> markovPresent : markovTable.entrySet()) {
            System.out.println(markovPresent.getKey());
            for (Map.Entry<String, Double> freqs : markovPresent.getValue().entrySet()) {
                System.out.println("\t" + freqs.getKey() + "\t" + freqs.getValue());
            }
        }
        return b.toString();
    }

    public static void main(String[] args) {
        String in = Util.readWholeStream(System.in);
        SyllableAnalysis sa = new SyllableAnalysis(in);
        System.out.println(sa);
    }
}
