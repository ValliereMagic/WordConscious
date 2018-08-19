package WordConscious.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class WordSearcherThreadResults {
    private List<String> results = new ArrayList<>();
    private int amountOfWordsPerSet;

    public WordSearcherThreadResults(Properties properties) {
        this.amountOfWordsPerSet = Integer.valueOf(properties.getProperty("amount_of_words_per_set", "10"));
    }

    public synchronized boolean addResult(String result) {
        if (results.size() < amountOfWordsPerSet) {
            results.add(result);
            return true;
        }
        return false;
    }

    public List<String> getResults() {
        return results;
    }
}
