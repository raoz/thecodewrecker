import javafx.util.Pair;

import java.lang.reflect.InvocationTargetException;

public interface Decrypter {
    static Class[] decrypters = new Class[CaesarDecrypter]; //List the default decrypters
    public Decryption decrypt(String s);
    static Decryption findBest() {
        for(Decrypter)
        try {
            decrypters[0].getMethod("findBest").invoke(""));

        } catch (NoSuchMethodException e) {
            System.out.println("Invalid decrypter in default decrypter list!");
        } catch (IllegalAccessException e) {
            System.out.println("Invalid decrypter in default decrypter list!");
        } catch (InvocationTargetException e) {
            System.out.println("Invalid decrypter in default decrypter list!");
        }
    }
}
