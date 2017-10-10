public class CaesarEncrypter implements Encrypter{
    private int shifter;
    public CaesarEncrypter(int shifter){
        this.shifter = shifter;
    }

    @Override
    public String encrypt(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++){
            int c = (int)s.charAt(i);
            if (Character.isUpperCase(c)){
                c = (c - (int)'A' + shifter)%26 + (int)'A';
            }
            else if (Character.isLowerCase(c)){
                c = (c - (int)'a' + shifter)%26 + (int)'a';
            }
            sb.append((char)c);

        }
        String encrypted = sb.toString();

        return encrypted;
    }

    public static void main(String[] args) {
        CaesarEncrypter encrypter1 = new CaesarEncrypter(2);
        System.out.println(encrypter1.encrypt("Zz-Yy? Ahv!!!!!"));
    }
}
