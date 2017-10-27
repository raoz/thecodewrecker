import java.util.List;
import java.util.stream.Collectors;

public class CompoundAnalysis implements Analysis{
    private List<Analysis> analyses;

    public CompoundAnalysis(List<Analysis> analyses) {
        this.analyses = analyses;
    }

    /**
     * @param c Class of type T(, which extends Analysis,) to look for
     * @return List of analyses of type T contained within the compound
     */
    @SuppressWarnings("unchecked") // The cast is checked by the filter
    public <T extends Analysis> List<T> getAnalysesByType(Class<T> c) {
        return (List<T>) analyses.stream().
                filter(analysis -> analysis.getClass().equals(c)).collect(Collectors.toList());
    }
}
