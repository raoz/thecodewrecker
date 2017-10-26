import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Decrypter {
    private static final Class[] decrypters = new Class[]{CaesarDecrypter.class}; //List the default decrypters
    abstract Decryption decrypt(String s);
    public static Decryption[] findBest(String s, int n) {
        List<Decryption> decryptionList = new ArrayList<Decryption>();
        for(Class c : decrypters) {
            try {
                Arrays.stream((Decryption[]) (c.getMethod("findBest", String.class, int.class).invoke(null, s, n))).forEach(decryptionList::add);

            } catch (NoSuchMethodException e) {
                System.out.println("Invalid decrypter in default decrypter list!\n" + e);
            } catch (IllegalAccessException e) {
                System.out.println("Invalid decrypter in default decrypter list!\n" + e);
            } catch (InvocationTargetException e) {
                System.out.println("Decrypter error:\n" + e.getCause());
            }
        }
        return decryptionList.stream().sorted().limit(n).toArray(Decryption[]::new); //get the first one in the sorted(desc by conf) list
    }
}
