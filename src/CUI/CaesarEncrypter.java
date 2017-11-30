package CUI;

import Core.Util;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

class CaesarEncrypter {
    public static void main(String[] args) {
        main();
    }

    /**
     * Interactive use of Core.CaesarEncrypter
     */
    static void main(){
        String message;
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the key(leave empty for random): ");
        Core.CaesarEncrypter encrypter;
        String keyStr = scan.nextLine().trim();
        if(keyStr.equals("")) {
            encrypter = new Core.CaesarEncrypter();
        } else {
            int key = Integer.parseInt(keyStr);
            encrypter = new Core.CaesarEncrypter(key);
        }
        InputStream in = Util.getInputStream(scan);
        PrintStream out = new PrintStream(Util.getOutputStream(scan));
        if(in == System.in) {
            System.out.println("Enter the message(Ctrl+D to end):");
        }
        message = Util.readWholeStream(in);
        out.println(encrypter.encrypt(message));
    }
}
