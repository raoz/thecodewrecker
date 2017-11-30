package Core;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public interface Analysis {
    /**
     * @param other String to comparatively analyse
     * @return Probability of being the same type of text
     */
    double similarity(String other);

    /**
     * Given the table contents, saves to the internal analysis map
     * @param lines The table contents as a string
     */
    void fromString(String lines);

    /**
     * Analyse more data and append to the table
     */
    void addData(String data);

    /**
     * Given a string of an analysis table header and the table contents, returns the appropriate filled-out analysis
     * @param s String to read analysis table from
     * @return The read analysis table
     */
    static Analysis getFromString(String s) {
        Scanner sc = new Scanner(s);
        Analysis a;
        if(sc.nextLine().trim().equals("Core.MarkovAnalysis")) {
            if(sc.nextLine().trim().startsWith("Character")) {
                a = new MarkovAnalysis<>(NaturalLanguage::characters);
            } else{
                a = new MarkovAnalysis<>(NaturalLanguage::naiveSyllables);
            }
        } else {
            if(sc.nextLine().trim().startsWith("Character")) {
                a = new FrequencyAnalysis<>(NaturalLanguage::characters);
            } else {
                a = new FrequencyAnalysis<>(NaturalLanguage::naiveSyllables);
            }
        }
        a.fromString(s.replaceFirst(".*\n.*\n", ""));
        return a;
    }
}