public interface Analysis {
    double similarity(String other);
    void fromString(String lines);
    void addData(String data);
}