import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class CaesarEncrypter implements KeyedEncrypter {
    /**
     * Number of places to shift by
     */
    private final int key;

    public CaesarEncrypter(int key) {
        super();
        this.key = key;
    }

    private CaesarEncrypter(){
        Random r = new Random();
        this.key = r.nextInt(26);
    }

    public static void main(String[] args) {
        main();
    }

    /**
     * Interactive use of this class
     */
    public static void main(){
        String message;
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the key(leave empty for random): ");
        CaesarEncrypter encrypter;
        String keyStr = scan.nextLine().trim();
        if(keyStr.equals("")) {
            encrypter = new CaesarEncrypter();
        } else {
            int key = Integer.parseInt(keyStr);
            encrypter = new CaesarEncrypter(key);
        }
        InputStream in = Util.getInputStream(scan);
        PrintStream out = new PrintStream(Util.getOutputStream(scan));
        if(in == System.in) {
            System.out.println("Enter the message(Ctrl+D to end):");
        }
        message = Util.readWholeStream(in);
        out.println(encrypter.encrypt(message));
    }

    /**
     * @param s String to encrypt
     * @return The encrypted string
     */
    @Override
    public String encrypt(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            int c = (int) s.charAt(i);
            if(Character.isAlphabetic(c)) { //Leave the special characters as-is
                Character identity = Character.isUpperCase(c) ? 'A' : 'a';
                c = (c - (int) identity + key + 26) % 26 + (int) identity; //Calculate the shifted key
            }
            sb.append((char) c);
        }

        return sb.toString();
    }
}
