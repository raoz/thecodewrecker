public class Decryption implements Comparable<Decryption>{
    private double confidence;
    private String plaintext;
    private Decrypter creator;

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
