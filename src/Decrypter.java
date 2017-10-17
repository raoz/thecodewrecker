import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public abstract class Decrypter {
    Class[] decrypters = new Class[]{CaesarDecrypter.class}; //List the default decrypters
    abstract Decryption decrypt(String s);
    public Decryption findBest() {
        List<Decryption> decryptionList = new ArrayList<Decryption>();
        for(Class c : decrypters) {
            try {
                decryptionList.add((Decryption)c.getMethod("findBest").invoke(""));

            } catch (NoSuchMethodException e) {
                System.out.println("Invalid decrypter in default decrypter list!");
            } catch (IllegalAccessException e) {
                System.out.println("Invalid decrypter in default decrypter list!");
            } catch (InvocationTargetException e) {
                System.out.println("Invalid decrypter in default decrypter list!");
            }
        }
        return decryptionList.stream().sorted().findFirst().get(); //get the first one in the sorted(desc by conf) list
    }
}
