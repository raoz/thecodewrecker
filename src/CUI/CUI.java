package CUI;

import Core.Util;

import java.util.Scanner;

/**
 * The main class for the JAR; chooses the class to execute
 * Also contains methods for working with arguments
 */
class CUI {
    public static void main(String[] args) {
        System.out.println("CodeWrecker uses natural language analysis techniques for automatic cryptoanalysis.");
        Scanner scan = new Scanner(System.in, "UTF-8");
        Util.CUIOptions(scan,"What do you want to do?",
                Util.arr("Crack an encrypted string", "Encrypt a string", "Generate an analysis table"),
                new Runnable[]{CodeWrecker::main, CaesarEncrypter::main, Analysis::main}
            ).run();
    }
}