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
    public static void main(String s, String message) {

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
        //TODO: Add more decrypters

        //Change where you get solutions amount
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

        PrintStream out = new PrintStream(Core.Util.getOutputStream(scan));
        Decryption[] ds = Decrypter.findBest(message, n, analysis);
        //Fix output
        for (Decryption d : ds) {
            out.println(d);
        }
    }
}
