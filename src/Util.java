import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
}
