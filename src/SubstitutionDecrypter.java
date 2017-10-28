import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Decrypter of substitution encryption, i.e. bijective function between plaintext and encrypted text
 */
public class SubstitutionDecrypter implements KeyedDecrypter {
    /**
     * The key used to decrypt (replacement → original)
     */
    private final Map<Character, Character> reverseKey;

    /**
     * @param key The original key used to encrypt
     */
    public SubstitutionDecrypter(HashMap<Character, Character> key) {
        this.reverseKey = key.entrySet().stream() //From the stream of entries
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey)); //get the reverse
    }

    /**
     * @param s String to analyse
     * @param n Limit of how many solutions to return
     * @param analysis The analysis to use
     * @return ≤ n decryptions of s
     */
    public static List<Decryption> findBest(String s, int n, Analysis analysis) {
        List<FrequencyAnalysis> frequencyAnalyses = new ArrayList<>();
        if (analysis instanceof FrequencyAnalysis) { //If the analysis is single, put it in the list
            frequencyAnalyses.add((FrequencyAnalysis) analysis);
        } else if (analysis instanceof CompoundAnalysis) {
            //Get the frequency analyses from the list
            frequencyAnalyses = ((CompoundAnalysis) analysis).getAnalysesByType(FrequencyAnalysis.class);
        }
        if (frequencyAnalyses.size() == 0) {
            throw new InsufficientDataException("Substitution decrypter needs frequency analysis to work");
        }
        //TODO: use frequency analysis
        throw new NotImplementedException();
    }

    /**
     * @param s        The string to decrypt
     * @param analysis The analysis to analyse the decryption with
     * @return The decryption of s
     */
    @Override
    public Decryption decrypt(String s, Analysis analysis) {
        SubstitutionEncrypter reverseEncrypter = new SubstitutionEncrypter(reverseKey);
        return new Decryption(reverseEncrypter.encrypt(s), this, analysis.similarity(s));
    }
}
