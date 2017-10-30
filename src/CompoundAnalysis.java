import java.util.List;

/**
 * Average of multiple analyses
 */
public class CompoundAnalysis implements Analysis{
    /**
     * List of analyses this analysis is composed of
     */
    private final List<Analysis> analyses;

    public CompoundAnalysis(List<Analysis> analyses) {
        this.analyses = analyses;
    }

    /**
     * Add an analysis to the collection
     * @param a Analysis to add
     */
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

    /**
     * @param data Additional data to analyse
     */
    @Override
    public void addData(String data) {
        analyses.forEach(a -> a.addData(data));
    }
}
