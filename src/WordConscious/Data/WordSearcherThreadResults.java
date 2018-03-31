package WordConscious.Data;

import java.util.ArrayList;
import java.util.List;

public class WordSearcherThreadResults {
    private List<String> results = new ArrayList<>();
    private Config config;

    public WordSearcherThreadResults(Config config) {
        this.config = config;
    }

    public synchronized boolean addResult(String result) {
        if (results.size() < config.getWordsPerSet()) {
            results.add(result);
            return true;
        }
        return false;
    }

    public List<String> getResults() {
        return results;
    }
}
