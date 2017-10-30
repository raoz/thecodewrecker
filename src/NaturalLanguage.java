import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Class for natural language tools
 */
public class NaturalLanguage {
    private static final Pattern reverseSyllablePattern = Pattern.compile(
            "[bcdfghjklmnpqrstvwxz]*[aeiouy]+[bcdfghjklmnpqrstvwxz]?([bcdfghjklmnpqrstvwxz]*\b)?");
    /**
     * Gets syllables, naïvely, ignoring language
     * @param s String to get syllables from
     * @return List of syllables in order.
     */
    static List<String> naiveSyllables(String s) {
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

    /**
     * Convert a string to list of alphabetic characters
     * @param s String to convert
     * @return List of characters in the string
     */
    static List<Character> characters(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFD) //convert into normal form
                .replaceAll("[^A-Za-z]", "")  //remove non-alphabetic
                .chars().mapToObj(c -> (char)c).collect(Collectors.toList()); //Create a list of characters
    }
}
