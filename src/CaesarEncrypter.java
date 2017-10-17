import java.util.Scanner;

public class CaesarEncrypter extends KeyedEncrypter{
    public CaesarEncrypter(int shifter){
        super(shifter);
    }

    @Override
    public String encrypt(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++){
            int c = (int)s.charAt(i);
            if (Character.isUpperCase(c)){
                c = (c - (int)'A' + getKey())%26 + (int)'A';
            }
            else if (Character.isLowerCase(c)){
                c = (c - (int)'a' + getKey())%26 + (int)'a';
            }
            sb.append((char)c);

        }
        String encrypted = sb.toString();

        return encrypted;
    }

    @Override
    public Integer getKey() {
        return (int)super.getKey();
    }

    @Override
    public void setKey(Object key) {
        if (!(key instanceof Integer)){
            throw new IllegalArgumentException("Caesar shifter needs an integer!");
        }
        super.setKey(key);
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
}
