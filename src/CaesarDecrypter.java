
import java.util.Arrays;
import java.util.List;

public class CaesarDecrypter implements KeyedDecrypter{
    private int key;

    /**
     * @param s The string to decrypt
     * @param n How many decryptions to return
     * @return The n best Caesar decryptions of s
     */
    @SuppressWarnings("unused") //This method is called via reflection
    public static Decryption[] findBest(String s, int n, Analysis analysis) {
        //Try all possible keys
        Decryption[] decryptions = new Decryption[26];
        for(int i = 0; i < 26; ++i) {
            decryptions[i] = new CaesarDecrypter(i).decrypt(s);
        }
        return Arrays.stream(decryptions).sorted().limit(n).toArray(Decryption[]::new); //Take n first from the sorted arr
    }

    public CaesarDecrypter(Integer key) {
        super();
        this.key = key;
    }

    /**
     * @param s String to decrypt
     * @return The decryption
     */
    @Override
    public Decryption decrypt(String s) {
        //Decryption is the same as encryption with reverse key
        CaesarEncrypter ce = new CaesarEncrypter(-this.key);
        System.out.println(ce.encrypt(s));
        return new Decryption(ce.encrypt(s), this);
    }

}
