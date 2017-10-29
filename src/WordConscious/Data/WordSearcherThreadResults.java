package WordConscious.Data;

import java.util.List;
import java.util.Vector;

public class WordSearcherThreadResults {
    private static List<String> results = new Vector<>();

    public static void addResult(String result) {
        results.add(result);
    }

    public static List<String> getResults() {
        return results;
    }
}
