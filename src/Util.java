import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
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
        return br.lines().collect(Collectors.joining("")); // Read all lines and join them using ""
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
     * Reverses a given string
     * @param s String to reverse
     * @return The reversed string
     */
    public static String reverseString(String s) {
        return new StringBuilder(s).reverse().toString();
    }
}
