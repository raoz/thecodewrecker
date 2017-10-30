import java.util.Scanner;

/**
 * The main class for the JAR; chooses the class to execute
 * Also contains methods for working with arguments
 */
public class Launcher {
    public static void main(String[] args) throws Exception {
        System.out.println("CodeWrecker uses natural language analysis techniques for automatic cryptoanalysis.");
        Scanner scan = new Scanner(System.in, "UTF-8");
        System.out.println("What do you want to do?");
        System.out.println("1. Crack an encrypted string");
        System.out.println("2. Encrypt a string");
        System.out.println("3. Generate an analysis table");
        int choice = scan.nextInt();
        switch (choice) {
            case 1:
                CodeWrecker.main(args);
                break;
            case 2:
                CaesarEncrypter.main(args);
                break;
            case 3:
                frequencyModule(args);
                break;
            default:
                System.out.println("Enter an integer in range [1..3]");
                main(args);
                return;
        }
    }
    public static void frequencyModule(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in, "UTF-8");
        System.out.println("Choose the type of table:");
        System.out.println("1. Frequency table");
        System.out.println("2. Markov table");
        int choice = scan.nextInt();
        switch (choice) {
            case 1:
                MarkovAnalysis.main(args);
                break;
            case 2:
                FrequencyAnalysis.main(args);
                break;
            default:
                System.out.println("Please enter an integer in range [1..2]");
                frequencyModule(args);
                break;
        }
    }
}
