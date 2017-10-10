import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Util {
    public static String readWholeStream(InputStream s) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); //Create a reader for the input
        return br.lines().collect(Collectors.joining("")); // Read all lines and join them using ""
    }
}
