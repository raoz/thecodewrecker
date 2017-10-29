import java.util.List;
import java.util.stream.Collectors;

/**
 * Average of multiple analyses
 */
public class CompoundAnalysis implements Analysis{
    private List<Analysis> analyses;

    public CompoundAnalysis(List<Analysis> analyses) {
        this.analyses = analyses;
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
}
