package Core;

import java.util.Random;

public class CaesarEncrypter implements KeyedEncrypter {
    /**
     * Number of places to shift by
     */
    private final int key;

    public CaesarEncrypter(int key) {
        super();
        if(key > 26 || key < -26) {
            throw new IllegalArgumentException("Key must be in range [-26,26]");
        }
        this.key = key;
    }

    public CaesarEncrypter(){
        Random r = new Random();
        this.key = r.nextInt(26);
    }


    /**
     * @param s String to encrypt
     * @return The encrypted string
     */
    @Override
    public String encrypt(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            int c = (int) s.charAt(i);
            if(Character.isAlphabetic(c)) { //Leave the special characters as-is
                Character identity = Character.isUpperCase(c) ? 'A' : 'a';
                c = (c - (int) identity + key + 26) % 26 + (int) identity; //Calculate the shifted key
            }
            sb.append((char) c);
        }

        return sb.toString();
    }
}
