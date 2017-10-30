import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MarkovAnalysis<T> implements Analysis{
    private Function<String, List<T>> tokenizer;
    private Map<T, Map<T, Long>> markovOccTable = new HashMap<>();
    private T lastToken = null;

    /**
     * Add data to the occurrence Markov table
     * @param s String to tokenize and add
     */
    @Override
    public void addData(String s) {
        List<T> tokens = tokenizer.apply(s);
        if(lastToken != null) {
            tokens.add(0, lastToken);
        }
        for (int i = 0; i < tokens.size() - 1; i++) { //Iterate over every adjacent pair of tokens
            T tok = tokens.get(i);
            T tokNext = tokens.get(i+1);
            markovOccTable.putIfAbsent(tok, new HashMap<>());
            Map<T, Long> occTable = markovOccTable.get(tok);
            occTable.putIfAbsent(tokNext, 0L);
            occTable.compute(tokNext, (__, n) -> n+1);
        }
        if(tokens.size() > 0) {
            lastToken = tokens.get(tokens.size() - 1);
        }
    }

    /**
     * @return The Markov frequency table
     */
    private Map<T, Map<T, Double>> getData() {
        Map<T, Map<T, Double>> freqTable = new HashMap<>();
        for (Map.Entry<T, Map<T, Long>> occEntry : markovOccTable.entrySet()) {
            Map<T, Double> freqSubTable = new HashMap<>();
            Double sum = occEntry.getValue().values().stream().mapToDouble(d->d).sum();
            occEntry.getValue().forEach((key, val) -> freqSubTable.put(key, val/sum));
            freqTable.put(occEntry.getKey(), freqSubTable);
        }
        return freqTable;
    }

    public MarkovAnalysis(Function<String, List<T>> tokenizer) {
        this.tokenizer = tokenizer;
    }

    /**
     * @param s String to analyse
     */
   public MarkovAnalysis(String s, Function<String, List<T>> tokenizer) {
       this.tokenizer = tokenizer;
       addData(s);
   }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (Map.Entry<T, Map<T, Long>> mSub : markovOccTable.entrySet()) {
            b.append(mSub.getKey());
            b.append('\n');
            mSub.getValue().forEach((key, value) -> {
                b.append('\t');
                b.append(key);
                b.append('\t');
                b.append(value);
                b.append('\n');
            });
        }
        return b.toString();
    }

    /**
     * Reads a string in the same format as toString outputs to this
     * @param s String to read from
     */
    @Override
    public void fromString(String s) {
        Function<String, T> read = tokenizer.andThen(a -> a.stream().findFirst().orElse(null)); //Get the first token
        Scanner scan = new Scanner(s);
        T token = null;
        Map<T, Long> sOccTable = null;
        while(scan.hasNextLine()) {
            String l = scan.nextLine();
            if(l.charAt(0) != '\t') {
                if(sOccTable != null) {
                    markovOccTable.put(token, sOccTable);
                }
                token = read.apply(l.trim());
                sOccTable = new HashMap<>();
            } else {
                String[] split = l.split(" \n");
                assert sOccTable != null;
                sOccTable.put(read.apply(split[0]),Long.parseLong(split[1]));
            }
        }
        markovOccTable.put(token, sOccTable);
    }

    public static void main(String[] args) throws IOException {
        //TODO: eliminate code duplication
        Scanner sc = new Scanner(System.in);
        System.out.println("Should the tokens be");
        System.out.println("1. Characters");
        System.out.println("2. Syllables");
        MarkovAnalysis f;
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                f = new MarkovAnalysis<Character>(NaturalLanguage::characters);
            case 2:
                f = new MarkovAnalysis<String>(NaturalLanguage::naiveSyllables);
                break;
            default:
                System.out.println("Please enter a number in range [1..2]");
                main(args);
                return;
        }
        System.out.println("Read from a file[{filename}] or from standard input[stdin]? ");
        String answer = sc.nextLine().trim();
        InputStream in;
        if (answer.toLowerCase().equals("stdin")){
            in = System.in;
        } else  {
            in = new FileInputStream(answer);
        }
        System.out.println("Output to a file[{filename}] or to standard output[stdout]?");
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
        return this.similarity(new MarkovAnalysis<>(other, tokenizer));
    }


    public double similarity(MarkovAnalysis<T> other) {
        Map<T, Map<T, Double>> f1 = this.getData();
        Map<T, Map<T, Double>> f2 = other.getData();
        Set<T> tokens = Stream.concat(f1.keySet().stream(), f2.keySet().stream()).collect(Collectors.toSet());
        double difference = 0;
        for (T token : tokens) {
            if(!(f1.containsKey(token) && f2.containsKey(token))) {
                difference += 1;
                continue;
            }
            Map<T, Double> sf1 = f1.get(token);
            Map<T, Double> sf2 = f2.get(token);
            Set<T> subTokens = Stream.concat(sf1.keySet().stream(), sf2.keySet().stream()).collect(Collectors.toSet());
            difference += subTokens.stream().
                    mapToDouble(k ->
                            Math.abs(sf1.getOrDefault(k, 0.0) - sf2.getOrDefault(k, 0.0))
                    ).sum() / subTokens.size();
        }
        difference = difference / tokens.size();
        return 1 - difference;
    }
}
