public class Decryption implements Comparable<Decryption>{
    private double confidence;
    private String plaintext;
    private Decrypter creator;

    public Decryption(String plaintext, Decrypter creator) {
        this.plaintext = plaintext;
        this.creator = creator;
        this.confidence = 0; //TODO: Implement confidence calculation

    }

    public double getConfidence() {
        return confidence;
    }

    public String getPlaintext() {
        return plaintext;
    }

    public Decrypter getCreator() {
        return creator;
    }

    @Override
    public int compareTo(Decryption o) {
        return -Double.compare(this.confidence, o.confidence);
    }
}
