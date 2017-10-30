import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static java.util.Comparator.comparing;

public class CaesarDecrypter implements KeyedDecrypter {
    /**
     * Number of places the plaintext is shifted by
     */
    private final int key;

    private CaesarDecrypter(Integer key) {
        super();
        this.key = key;
    }

    /**
     * @param s The string to decrypt
     * @param n How many decryptions to return
     * @return The n best Caesar decryptions of s
     */
    public static List<Decryption> findBest(String s, int n, Analysis analysis) {
        //Try all possible keys
        Decryption[] decryptions = new Decryption[26];
        for (int i = 0; i < 26; ++i) {
            decryptions[i] = new CaesarDecrypter(i).decrypt(s, analysis);
        }
        return Arrays.stream(decryptions).sorted(comparing(Decryption::getConfidence).reversed()).//Sort by confidence descending
                limit(n).collect(Collectors.toList()); //Take n first from the sorted stream
    }

    /**
     * @param s String to decrypt
     * @return The decryption
     */
    @Override
    public Decryption decrypt(String s, Analysis analysis) {
        //Decryption is the same as encryption with reverse key
        CaesarEncrypter ce = new CaesarEncrypter(26-this.key);
        String plaintext = ce.encrypt(s);
        return new Decryption(plaintext, this, analysis.similarity(plaintext));
    }

    @Override
    public String toString() {
        return "CaesarDecrypter{" +
                "key=" + key +
                '}';
    }
}
