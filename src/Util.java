import java.io.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Util {
    /**
     * Reads the whole stream
     * @param s InputStream to read
     * @return Contents as a String
     */
    public static String readWholeStream(InputStream s) {
        BufferedReader br = new BufferedReader(new InputStreamReader(s)); //Create a reader for the input
        return br.lines().collect(Collectors.joining("\n")); // Read all lines and join them using ""
    }

    /**
     * A function that takes three arguments and returns something
     * @param <T> First argument type of the function
     * @param <U> Second argument type of the function
     * @param <V> Third argument type of the function
     * @param <R> Return type of the function
     */
    @FunctionalInterface
    interface TriFunction<T,U,V,R> {
        /**
         * Applies the function to given arguments
         */
        R apply(T t, U u, V v);

        /**
         * Composes the function with another of one parameter R and return value X
         * @param after function to compose with
         * @param <X> return type of after
         * @return Function composition of this and after
         */
        @SuppressWarnings("unused") //For a nice interface for future use
        default <X> TriFunction<T,U,V,X> andThen(Function<? super R, ? extends X> after) {
            Objects.requireNonNull(after);
            return (T t, U u, V v) -> after.apply(apply(t,u,v));
        }
    }

    /**
     * Asks an user a question and presents them with numbered options
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

    /**
     * Lets user choose an input or output stream
     * @param scan Scanner to use
     * @param std Default input
     * @param <T> Stream type
     * @return The stream chosen by user
     */
    @SuppressWarnings("unchecked")
    private static <T> T getStream(Scanner scan, T std) {
        while(true) { //Until we get a stream, at which point we return
            String name = scan.nextLine().trim();
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

    /**
     * Lets user choose an input stream
     * @param scan Scanner to use
     * @return chosen input stream
     */
    public static InputStream getInputStream(Scanner scan) {
        System.out.println("Read from a file[{filename}] or from standard input[]? ");
        return getStream(scan, System.in);
    }
    /**
     * Lets user choose an output stream
     * @param scan Scanner to use
     * @return chosen output stream
     */
    public static OutputStream getOutputStream(Scanner scan) {
        System.out.println("Write to a file[{filename}] or to standard output[]? ");
        return getStream(scan, System.out);
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
