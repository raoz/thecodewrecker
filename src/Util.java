import java.io.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Util {
    /**
     * Reads the whole stream
     *
     * @param s InputStream to read
     * @return Contents as a String
     */
    public static String readWholeStream(InputStream s) {
        BufferedReader br = new BufferedReader(new InputStreamReader(s)); //Create a reader for the input
        return br.lines().collect(Collectors.joining("\n")); // Read all lines and join them using ""
    }
    @FunctionalInterface
    interface TriFunction<T,U,V,R> {
        R apply(T t, U u, V v);
        default <X> TriFunction<T,U,V,X> andThen(Function<? super R, ? extends X> after) {
            Objects.requireNonNull(after);
            return (T t, U u, V v) -> after.apply(apply(t,u,v));
        }
    }

    /**
     * @param scan Scanner to use for retrieving the response
     * @param query The question to ask
     * @param options The options to present
     * @param values The values corresponding to the options
     * @param <T> The type of values
     * @return The chosen option's value
     */
    public static <T> T CUIOptions(Scanner scan, String query, String[] options, T[] values) {
        if (options.length != values.length) {
            throw new IllegalArgumentException("There must be as many options as values!");
        }
        System.out.println(query);
        for (int i = 0; i < options.length; i++) {
            System.out.println(String.format("%d. %s", i + 1, options[i]));
        }
        T result = null;
        while (result == null) {
            try {
                int choice = Integer.parseInt(scan.nextLine().trim());
                result = values[choice - 1];
            } catch (Exception e) {
                System.out.println(String.format("Please enter an integer in range [1..%d]", options.length));
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private static <T> T getStream(String name, T std) {
        while(true) { //Until we get a stream, at which point we return
            if (name.equals("")) {
                return std;
            } else {
                try {
                    if (InputStream.class.isInstance(std)) {
                        return (T) new FileInputStream(name);
                    } else if (OutputStream.class.isInstance(std)) {
                        return (T) new FileOutputStream(name);
                    } else {
                        throw new IllegalArgumentException("std must be input or output stream!");
                    }
                } catch (IOException e) {
                    System.out.println("Could not open file: " + e.getMessage());
                }
            }
        }
    }
    public static InputStream getInputStream(Scanner scan) {
        System.out.println("Read from a file[{filename}] or from standard input[]? ");
        String answer = scan.nextLine().trim();
        return getStream(answer, System.in);
    }
    public static OutputStream getOutputStream(Scanner scan) {
        System.out.println("Write to a file[{filename}] or to standard output[]? ");
        String answer = scan.nextLine().trim();
        return getStream(answer, System.out);
    }

    /**
     * @param args  Elements to make an array out of
     * @param <T> Type of arguments
     * @return An array consisting of the arguments of type T
     */
    @SafeVarargs
    public static <T> T[] arr(T ... args) {
        return args;
    }

    /**
     * Reverses a given string
     * @param s String to reverse
     * @return The reversed string
     */
    public static String reverseString(String s) {
        return new StringBuilder(s).reverse().toString();
    }
}
