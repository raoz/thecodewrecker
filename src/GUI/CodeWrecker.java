package GUI;

import Core.*;
import Core.Analysis;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

class CodeWrecker {
    public static void main(String s, String message, String number) {

        Scanner scan = new Scanner(System.in);

        //Build the analysis
        String[] filenames = s.split(";");
        CompoundAnalysis analysis = new CompoundAnalysis(new ArrayList<>());
        for (String filename : filenames) {
            try {
                String data = Core.Util.readWholeStream(new FileInputStream(filename));
                analysis.addAnalysis(Analysis.getFromString(data));
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + e.getMessage());
                System.out.println("Ignoring.");
            }
        }
        //Add the decrypters
        Decrypter.registerDecrypterFinder(CaesarDecrypter::findBest);

        //Change where you get solutions amount

        try {
            int n = Integer.parseInt(number);
            if (n < 1 || n > 26) {
            throw new IllegalArgumentException("n must be in range [1..26]");
            }
            Decryption[] ds = Decrypter.findBest(message, n, analysis);
            //Fix output
            for (Decryption d : ds) {
                //
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Incorrect number of solutions");
        }
    }
}
