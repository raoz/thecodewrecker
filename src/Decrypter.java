import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Comparator.comparing;

/**
 * An interface for a Decrypter, implementations of this should have a static findBest method.
 */
public interface Decrypter {
    /**
     * A list of different decrypter classes
     */
    List<Util.TriFunction<String, Integer, Analysis, List<Decryption>>> decrypters = new ArrayList<>();
    static void registerDecrypterFinder(Util.TriFunction<String, Integer, Analysis, List<Decryption>> d) {
        decrypters.add(d);
    }

    /**
     * Find n best-matching decryptions for a given string using different decrypters
     *
     * @param s The string to find decryptions for
     * @param n The number of decryptions to find
     * @return An array of the found decryptions
     */
    static Decryption[] findBest(String s, int n, Analysis analysis) {
        List<Decryption> decryptions = new ArrayList<Decryption>();
        for (Util.TriFunction<String, Integer, Analysis, List<Decryption>> fBest : decrypters) { //For every decrypter
            try {
                fBest.apply( s, n, analysis) //Call that method with the same parameters
                .forEach(decryptions::add); //Of the results, add each one to the list
            } catch (InsufficientDataException e) {
                System.out.println(e);
            }
        }
        return decryptions.stream().
                sorted(comparing(Decryption::getConfidence).reversed()). //Sort descendingly by confidence
                limit(n).toArray(Decryption[]::new); //Take the first n and return an array
    }

    /**
     * @param s The string to decrypt
     * @return A Decryption containing the decrypted string
     */
    Decryption decrypt(String s);
}
