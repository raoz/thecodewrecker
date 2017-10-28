public class ConstantAnalysis implements Analysis {
    private double c;

    public ConstantAnalysis(double c) {
        this.c = c;
    }

    @Override
    public double similarity(String other) {
        return c;
    }
}
