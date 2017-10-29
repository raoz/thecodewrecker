import java.io.File;
import java.io.FileInputStream;
import java.util.Random;
import java.util.Scanner;

public class CaesarEncrypter implements KeyedEncrypter {
    private int key;

    public CaesarEncrypter(int key) {
        super();
        this.key = key;
    }

    public CaesarEncrypter(){
        Random r = new Random();
        this.key = r.nextInt(26);
    }

    public static void main(String[] args) throws Exception{
        String message;
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the key: ");
        int shifter = scan.nextInt();
        CaesarEncrypter encrypter1 = new CaesarEncrypter(shifter);
        System.out.println("Read text from file[file] or from input[input]? ");
        String answer = scan.nextLine().toLowerCase();

        if (answer.equals("file")){
            System.out.println("Enter file name: ");
            String fl = scan.nextLine();
            File file = new File(fl);
            message = Util.readWholeStream(new FileInputStream(file));
        }
        else {
            //System.out.println(encrypter1.encrypt("Zz-Yy? Ahv!!!!!"));
            System.out.println("Please enter a message to encrypt(ctrl+D to end): ");
            message = Util.readWholeStream(System.in);
        }
        System.out.println(encrypter1.encrypt(message));
    }

    @Override
    public String encrypt(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            int c = (int) s.charAt(i);
            Character identity = Character.isUpperCase(c) ? 'A' : 'a';
            c = (c - (int)identity + key) % 26 + (int)identity;
            sb.append((char) c);

        }

        return sb.toString();
    }
}
