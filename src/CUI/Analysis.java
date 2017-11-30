package CUI;

import Core.FrequencyAnalysis;
import Core.MarkovAnalysis;
import Core.NaturalLanguage;
import Core.Util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class Analysis {
    /**
     * Interactive Core.Analysis generation
     */
    @SuppressWarnings("unchecked")
    static void main() {
        Scanner scan = new Scanner(System.in);
        //Get the token type
        Function<String, List> t = Util.CUIOptions(scan, "Should the tokens be:",
                Util.arr("Characters", "Syllables"), Util.arr(NaturalLanguage::characters, NaturalLanguage::naiveSyllables));
        //Get the analysis type
        Core.Analysis a = Util.CUIOptions(scan,"Choose the type of table:", Util.arr("Frequency table", "Markov table"),
                Util.arr(new FrequencyAnalysis(t), new MarkovAnalysis(t)));
        //Get the IO
        InputStream in = Util.getInputStream(scan);
        PrintStream out = new PrintStream(Util.getOutputStream(scan));

        if(in == System.in) {
            //Prompt
            System.out.println("Please enter the source text(Ctrl+D to end):");
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        //Read the input and process
        br.lines().forEach(a::addData);
        //Output the processed data
        out.println(a.toString());
    }
}
