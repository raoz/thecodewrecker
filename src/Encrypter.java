/**
 * A object that can encrypt strings.
 */
@SuppressWarnings("unused") //It is expected that in the future there will be more implementations
public interface Encrypter {
    /**
     * @param s String to encrypt
     * @return Encrypted string
     */
    String encrypt(String s);
}
