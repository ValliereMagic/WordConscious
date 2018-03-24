package WordConscious.Threads;

import WordConscious.Data.Config;
import WordConscious.Data.WordSearcherThreadResults;

import java.util.ArrayList;
import java.util.List;

public class WordSearcherThread implements Runnable {

    private Config config;
    private int startingPoint;
    private int endingPoint;
    private List<String> allWords;
    private List<Character> allowedCharacters;
    private WordSearcherThreadResults results;

    public WordSearcherThread(int startingPoint, int endingPoint, List<String> allWords, List<Character> allowedCharacters, Config config, WordSearcherThreadResults results) {
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.allWords = allWords;
        this.allowedCharacters = allowedCharacters;
        this.config = config;
        this.results = results;
    }

    @Override
    public void run() {
        List<Character> currentWordRegex = new ArrayList<>();

        for (int i = startingPoint; i <= endingPoint; i++) {
            String current = allWords.get(i);

            if (current.length() <= config.getLettersPerSet() && current.length() >= 3) {
                boolean valid = true;

                currentWordRegex.addAll(allowedCharacters);
                for (char c : current.toCharArray()) {
                    if (currentWordRegex.contains(c)) {
                        int lastIndexOfChar = currentWordRegex.lastIndexOf(c);
                        currentWordRegex.remove(lastIndexOfChar);
                    } else {
                        valid = false;
                    }
                }

                if (valid) {
                    /*
                    * TODO: Move the logic that decides how many words can be returned
                    * in the results to the setter in WordSearcherThreadResults.
                    */

                    if (results.getResults().size() < config.getWordsPerSet()) {
                        results.addResult(current);
                    } else {
                        break;
                    }
                }
            }
            currentWordRegex.clear();
        }
    }

    @Override
    public String toString() {
        return "WordSearcherThread{" +
                "startingPoint=" + startingPoint +
                ", endingPoint=" + endingPoint +
                '}';
    }
}
