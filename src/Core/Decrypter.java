package Core;

import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;

/**
 * An interface for a Core.Decrypter, implementations of this should have a static findBest method.
 */
interface Decrypter {
    /**
     * A static list of different decrypter functions
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
     * @param analysis Core.Analysis to use for getting confidence and finding decrypters
     * @return An array of the found decryptions
     */
    static Decryption[] findBest(String s, int n, Analysis analysis) {
        List<Decryption> decryptions = new ArrayList<>();
        for (Util.TriFunction<String, Integer, Analysis, List<Decryption>> fBest : decrypters) { //For every decrypter
                decryptions.addAll(fBest.apply( s, n, analysis));
        }
        return decryptions.stream().
                sorted(comparing(Decryption::getConfidence).reversed()). //Sort descendingly by confidence
                limit(n).toArray(Decryption[]::new); //Take the first n and return an array
    }

    /**
     * @param s The string to decrypt
     * @param analysis The analysis to analyse the decryption with
     * @return A Core.Decryption containing the decrypted string
     */
    @SuppressWarnings("unused")
    Decryption decrypt(String s, Analysis analysis);
}
