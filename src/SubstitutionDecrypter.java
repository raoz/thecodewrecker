import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SubstitutionDecrypter implements KeyedDecrypter {
    private HashMap<Character, Character> key;
    private Map<Character, Character> reverseKey;

    public SubstitutionDecrypter(HashMap<Character, Character> key) {
        this.key = key;
        this.reverseKey = key.entrySet().stream() //From the stream of entries
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey)); //get the reverse
    }

    @SuppressWarnings("unused") //This method is called via reflection
    public static Decryption[] findBest(String s, int n, Analysis analysis) {
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

    @Override
    public Decryption decrypt(String s) {
        SubstitutionEncrypter reverseEncrypter = new SubstitutionEncrypter(reverseKey);
        return new Decryption(reverseEncrypter.encrypt(s), this);
    }
}
