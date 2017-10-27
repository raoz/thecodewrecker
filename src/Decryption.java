/**
 * Representation of a decryption, containing the plaintext, the confidence, and the decrypter used
 */
public class Decryption implements Comparable<Decryption> {
    private final double confidence;
    private final String plaintext;
    private final Decrypter creator;

    /**
     * @param plaintext The result of decryption
     * @param creator   The Decrypter used to decrypt
     */
    public Decryption(String plaintext, Decrypter creator) {
        this.plaintext = plaintext;
        this.creator = creator;
        this.confidence = 0; //TODO: Implement confidence calculation

    }

    @Override
    public String toString() {
        return "--------Decryption with confidence " + confidence + " and algorithm " + creator + "\n" + plaintext;
    }

    /**
     * @return The confidence of this being the correct decryption
     */
    public double getConfidence() {
        return confidence;
    }

    /**
     * @return The plain-test result of decryption
     */
    public String getPlaintext() {
        return plaintext;
    }

    /**
     * @return The decrypter used to generate this decryption
     */
    public Decrypter getCreator() {
        return creator;
    }

    @Override
    public int compareTo(Decryption o) {
        return Double.compare(this.confidence, o.confidence);
    }
}
