public class CodeWrecker {
    public static void main(String[] args) {
        Decrypter.registerDecrypterFinder(CaesarDecrypter::findBest);
        String in = Util.readWholeStream(System.in);
        System.out.println(in);
        Decryption[] ds = Decrypter.findBest(in, 26, new ConstantAnalysis(0.5));
        for (Decryption d : ds) {
            System.out.println(d);
        }
    }
}
