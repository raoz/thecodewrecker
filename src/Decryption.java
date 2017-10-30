/**
 * Representation of a decryption, containing the plaintext, the confidence, and the decrypter used
 */
public class Decryption implements Comparable<Decryption> {
    private final double confidence;
    private final String plaintext;
    private final Decrypter creator;

    /**
     * @param plaintext The result of decryption
     * @param creator The Decrypter used to decrypt
     * @param confidence Likelihood of this being the correct decryption according to analysis
     */
    public Decryption(String plaintext, Decrypter creator, double confidence) {
        this.plaintext = plaintext;
        this.creator = creator;
        this.confidence = confidence;

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

    @Override
    public int compareTo(Decryption o) {
        return Double.compare(this.confidence, o.confidence);
    }
}
