package GUI;

class CaesarEncrypter {
    static String main(String message, String keyStr){

        Core.CaesarEncrypter encrypter;
        if(keyStr.equals("")) {
            encrypter = new Core.CaesarEncrypter();
            return encrypter.encrypt(message);
        } else {
            try {
                int key = Integer.parseInt(keyStr);
                encrypter = new Core.CaesarEncrypter(key);
                return encrypter.encrypt(message);
            } catch( Exception e ) {
                System.out.println("Incorrect key error!");
                return "";
            }
        }
    }
}
