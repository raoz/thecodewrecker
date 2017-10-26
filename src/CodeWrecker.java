
public class CodeWrecker {
    public static void main(String[] args) {
        String in = Util.readWholeStream(System.in);
        System.out.println(in);
        Decryption[] ds = Decrypter.findBest(in, 26);
        for (Decryption d : ds) {
            System.out.println(d);
        }
    }
}
