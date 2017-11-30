package Core;

/**
 * Core.Analysis with constant output for testing
 */
@SuppressWarnings("unused")
public class ConstantAnalysis implements Analysis {
    /**
     * Constant that is returned
     */
    private double c;

    public ConstantAnalysis(double c) {
        this.c = c;
    }

    /**
     * @param other String to comparatively analyse
     * @return constant c
     */
    @Override
    public double similarity(String other) {
        return c;
    }

    /**
     * @param lines The constant as a string
     */
    @Override
    public void fromString(String lines) {
        c = Double.parseDouble(lines.trim());
    }

    /**
     * @param data Additional data to discard
     */
    @Override
    public void addData(String data) {
    }
}
