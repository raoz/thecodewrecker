import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public interface Analysis {
    double similarity(String other);
    void fromString(String lines);
    void addData(String data);

    /**
     * Interactive Analysis generation
     */
    @SuppressWarnings("unchecked")
    static void main() {
        Scanner scan = new Scanner(System.in);
        Function<String, List> t = Util.CUIOptions(scan, "Choose the type of token:",
                Util.arr("Character", "Syllable"), Util.arr(NaturalLanguage::characters, NaturalLanguage::naiveSyllables));
        System.out.println("Should the tokens be");
        System.out.println("1. Characters");
        System.out.println("2. Syllables");
        Analysis a = Util.CUIOptions(scan,"Choose the type of table:", Util.arr("Frequency table", "Markov table"),
                Util.arr(new FrequencyAnalysis(t), new MarkovAnalysis(t)));
        InputStream in = Util.getInputStream(scan);
        PrintStream out = new PrintStream(Util.getOutputStream(scan));
        if(in == System.in) {
            System.out.println("Please enter the source text(Ctrl+D to end):");
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        br.lines().forEach(a::addData);
        out.println(a.toString());
    }
}