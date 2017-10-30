import javax.print.attribute.standard.MediaSize;
import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FrequencyAnalysis<T> implements Analysis{
    private Map<T, Double> freqMap = new HashMap<>();
    private Map<T, Long> occMap = new HashMap<>();
    Function<String, List<T>> tokenizer;

    FrequencyAnalysis(String s, Function<String, List<T>> tokenizer) {
        this.tokenizer = tokenizer;
    }

    public FrequencyAnalysis(Function<String, List<T>> tokenizer) {
        this.tokenizer = tokenizer;
    }

    @Override
   public void fromString(String s) {
        occMap = null;
        Function<String, T> read = tokenizer.andThen(a -> a.stream().findFirst().orElse(null)); //Get the first token
        Scanner sc = new Scanner(s);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] pieces = line.split("\t");
            freqMap.put(read.apply(pieces[0]), Double.parseDouble(pieces[1]));
        }
    }

    @Override
    public void addData(String data) {
        if(occMap == null) {
            throw new IllegalStateException("Frequency analysis has no occurrence table.");
        }
        freqMap = null; //Reset the frequency map
        data = data.toUpperCase();
        List<T> tokens = tokenizer.apply(data);
        for (int i = 0; i < data.length(); i++) {
            if (occMap.containsKey(tokens.get(i))) {
                occMap.put(tokens.get(i), occMap.get(tokens.get(i)) + 1);
            } else {
                occMap.put(tokens.get(i), 1L);
            }
        }
    }

    public static void main(String[] args) throws Exception{
        //String in = Util.readWholeStream(System.in);
        //System.out.println(frequencyMap(in, true));
        //Map<Character, Double> thisMap = frequencyMap(in, true);
        Scanner sc = new Scanner(System.in);
        System.out.println("Should the tokens be");
        System.out.println("1. Characters");
        System.out.println("2. Syllables");
        FrequencyAnalysis f;
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                f = new FrequencyAnalysis<Character>(NaturalLanguage::characters);
            case 2:
                f = new FrequencyAnalysis<String>(NaturalLanguage::naiveSyllables);
                break;
            default:
                System.out.println("Please enter a number in range [1..2]");
                main(args);
                return;
        }
        System.out.println("Read from a file[filename] or from standard input[stdin]? ");
        String answer = sc.nextLine().trim();
        FrequencyAnalysis<Character> characterFrequencyAnalysis = new FrequencyAnalysis<>(NaturalLanguage::characters);
        InputStream in;
        if (answer.toLowerCase().equals("stdin")){
            in = System.in;
        } else  {
            in = new FileInputStream(answer);
        }
        System.out.println("Output to a file[filename] or standard output[stdout]?");
        answer = sc.nextLine().trim();
        PrintStream out;
        if (answer.toLowerCase().equals("stdin")){
            out = System.out;
        } else {
            out = new PrintStream(answer);
        }
        //File fr = new File("frequency.txt");
        if(in == System.in) {
            System.out.println("Please enter the sourcetext(Ctrl+D to end):");
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        br.lines().forEach(f::addData);
        out.println(br);
    }
    @Override
    public double similarity(String other) {
        return this.similarity(new FrequencyAnalysis<T>(other, tokenizer));
    }

    public Map<T, Double> getMap() {
        if(freqMap != null) {
            return freqMap;
        }
        freqMap = new HashMap<>();
        long sum = occMap.values().stream().mapToLong(l->l).sum();
        for (Map.Entry<T, Long> entry : occMap.entrySet()) {
            freqMap.put(entry.getKey(), (double)entry.getValue() / sum);
        }
        return freqMap;
    }

    public double similarity(FrequencyAnalysis<T> other){
        Map<T, Double> f1 = this.getMap();
        Map<T, Double> f2 = other.getMap();
        double difference = 0;
        Set<T> subTokens = Stream.concat(f1.keySet().stream(), f2.keySet().stream()).collect(Collectors.toSet());
        difference += subTokens.stream().
                mapToDouble(k ->
                        Math.abs(f1.getOrDefault(k, 0.0) - f2.getOrDefault(k, 0.0))
                ).sum() / subTokens.size();

        return 1 - difference;
    }

}
