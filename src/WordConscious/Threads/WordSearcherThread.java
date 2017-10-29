package WordConscious.Threads;

import WordConscious.Data.Config;
import WordConscious.Data.WordSearcherThreadResults;

import java.util.List;

public class WordSearcherThread implements Runnable{

    private Config config;
    private int startingPoint;
    private int endingPoint;
    private List<String> allWords;
    private List<Character> allowedCharacters;

    public WordSearcherThread(int startingPoint, int endingPoint, List<String> allWords, List<Character> allowedCharacters, Config config) {
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.allWords = allWords;
        this.allowedCharacters = allowedCharacters;
        this.config = config;

    }

    @Override
    public void run() {
        for (int i = startingPoint; i <= endingPoint; i++) {
            String current = allWords.get(i);

            if (current.length() <= config.getLettersPerSet() && current.length() >= 3) {
                boolean valid = true;
                for (char c : current.toCharArray()) {
                    if (!allowedCharacters.contains(c)) {
                        valid = false;
                    }
                }

                if (valid) {
                    if (WordSearcherThreadResults.getResults().size() < config.getWordsPerSet()) {
                        WordSearcherThreadResults.addResult(current);
                    } else {
                        break;
                    }
                }
            }
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
