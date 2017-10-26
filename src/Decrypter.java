import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public abstract class Decrypter {
    private static final Class[] decrypters = new Class[]{CaesarDecrypter.class}; //List the default decrypters
    abstract Decryption decrypt(String s);
    public static Decryption[] findBest(String s, int n) {
        List<Decryption> decryptionList = new ArrayList<Decryption>();
        for(Class c : decrypters) {
            try {
                decryptionList.add((Decryption[])c.getMethod("findBest").invoke(null, s, n);

            } catch (NoSuchMethodException e) {
                System.out.println("Invalid decrypter in default decrypter list!");
            } catch (IllegalAccessException e) {
                System.out.println("Invalid decrypter in default decrypter list!");
            } catch (InvocationTargetException e) {
                System.out.println("Invalid decrypter in default decrypter list!");
            }
        }
        return (Decryption[])decryptionList.stream().sorted().limit(n).toArray(); //get the first one in the sorted(desc by conf) list
    }
}
