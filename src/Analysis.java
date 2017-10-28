import java.util.function.Function;

public interface Analysis {
    double similarity(String other);
    void fromString(String lines);
}