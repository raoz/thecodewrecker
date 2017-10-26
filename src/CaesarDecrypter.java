import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.Arrays;

public class CaesarDecrypter extends KeyedDecrypter{

    /**
     * @param s The string to decrypt
     * @param n How many decryptions to return
     * @return The n best Caesar decryptions of s
     */
    public static Decryption[] findBest(String s, int n) {
        //Try all possible keys
        Decryption[] decryptions = new Decryption[26];
        for(int i = 0; i < 26; ++i) {
            decryptions[i] = new CaesarDecrypter(i).decrypt(s);
        }
        return (Decryption[])Arrays.stream(decryptions).sorted().limit(n).toArray(); //Take n first from the sorted arr
    }

    public CaesarDecrypter(Integer key) {
        this.setKey(key);
    }

    /**
     * @param s String to decrypt
     * @return The decryption
     */
    @Override
    public Decryption decrypt(String s) {
        //Decryption is the same as encryption with reverse key
        CaesarEncrypter ce = new CaesarEncrypter(-getKey());
        return new Decryption(ce.encrypt(s), this);
    }

    @Override
    public Integer getKey() {
        return (Integer)super.getKey();
    }

    @Override
    public void setKey(Object key) {
        if(key instanceof Integer) {
            super.setKey(key);
        } else {
            throw new IllegalArgumentException("CaesarDecrypter key must be Integer");
        }
    }
}
