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
    Class[] decrypters = new Class[]{CaesarDecrypter.class, SubstitutionDecrypter.class}; //List the default decrypters

    /**
     * @param s The string to decrypt
     * @return A Decryption containing the decrypted string
     */
    Decryption decrypt(String s);

    /**
     * Find n best-matching decryptions for a given string using different decrypters
     * @param s The string to find decryptions for
     * @param n The number of decryptions to find
     * @return An array of the found decryptions
     */
    static Decryption[] findBest(String s, int n, Analysis analysis) {
        List<Decryption> decryptions = new ArrayList<Decryption>();
        for(Class c : decrypters) { //For every decrypter
            try {
                Arrays.stream(
                            (Decryption[]) (c.getMethod("findBest", String.class, int.class, Analysis.class). //Take the "findBest(String, int)" method of the decrypter
                            invoke(null, s, n, analysis)) //Call that method with the same parameters
                    ).forEach(decryptions::add); //Of the results, add each one to the list
            } catch (NoSuchMethodException e) {
                System.out.println("Invalid decrypter in default decrypter list!\n" + e);
            } catch (IllegalAccessException e) {
                System.out.println("Invalid decrypter in default decrypter list!\n" + e);
            } catch (InvocationTargetException e) {
                System.out.println("Decrypter error:\n" + e.getCause());
            }
        }
        return decryptions.stream().
                sorted(comparing(Decryption::getConfidence).reversed()). //Sort descendingly by confidence
                limit(n).toArray(Decryption[]::new); //Take the first n and return an array
    }
}
