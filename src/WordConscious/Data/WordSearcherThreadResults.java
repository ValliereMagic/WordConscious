package WordConscious.Data;

import java.util.List;
import java.util.Vector;

public class WordSearcherThreadResults {
    private List<String> results = new Vector<>();

    public void addResult(String result) {
        results.add(result);
    }

    public List<String> getResults() {
        return results;
    }
}
