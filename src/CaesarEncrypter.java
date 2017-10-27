import java.util.Scanner;

public class CaesarEncrypter implements KeyedEncrypter {
    private int key;

    public CaesarEncrypter(int key) {
        super();
        this.key = key;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the shifter: ");
        int shifter = scan.nextInt();
        scan.nextLine();
        CaesarEncrypter encrypter1 = new CaesarEncrypter(shifter);
        System.out.println(encrypter1.encrypt("Zz-Yy? Ahv!!!!!"));
        System.out.println("Please enter a message to encrypt: ");
        String message = scan.nextLine();
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
