import java.util.List;

/**
 * Average of multiple analyses
 */
public class CompoundAnalysis implements Analysis{
    private final List<Analysis> analyses;

    public CompoundAnalysis(List<Analysis> analyses) {
        this.analyses = analyses;
    }

    public void addAnalysis(Analysis a) {
        analyses.add(a);
    }


    /**
     * @param other the string to compare against
     * @return the average similarity of the included analyses
     */
    @Override
    public double similarity(String other) {
        return analyses.stream().mapToDouble(a->a.similarity(other)).sum() / analyses.size();
    }

    @Override
    public void fromString(String lines) {
        //TODO
    }

    @Override
    public void addData(String data) {
        analyses.forEach(a -> a.addData(data));
    }
}
