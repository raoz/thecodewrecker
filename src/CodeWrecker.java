import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

class CodeWrecker {
    public static void main(String[] args) {
        main();
    }

    /**
     * Interactively try to decipher
     */
    public static void main() {
        Scanner scan = new Scanner(System.in);

        //Build the analysis
        System.out.println("Enter a comma separated list of analysis files:");
        String[] filenames = scan.nextLine().trim().split(",");
        CompoundAnalysis analysis = new CompoundAnalysis(new ArrayList<>());
        for (String filename : filenames) {
            try {
                String data = Util.readWholeStream(new FileInputStream(filename));
                analysis.addAnalysis(Analysis.getFromString(data));
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + e.getMessage());
                System.out.println("Ignoring.");
            }
        }
        //Add the decrypters
        Decrypter.registerDecrypterFinder(CaesarDecrypter::findBest);
        //TODO: Add more decrypters

        System.out.println("How many solutions to output? [1..26]");
        int n = -1;
        while(n == -1) {
            try {
                n = Integer.parseInt(scan.nextLine().trim());
                if (n < 1 || n > 26) {
                    throw new IllegalArgumentException("n must be in range [1..26]");
                }
            } catch (IllegalArgumentException e) {
                n = -1;
                System.out.println("Please enter a number between 1 and 26.");
            }
        }

        System.out.println("Text to decipher");
        InputStream in = Util.getInputStream(scan);
        PrintStream out = new PrintStream(Util.getOutputStream(scan));
        Decryption[] ds = Decrypter.findBest(Util.readWholeStream(in), n, analysis);
        for (Decryption d : ds) {
            out.println(d);
        }
    }
}
