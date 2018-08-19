package WordConscious.Threads;

import WordConscious.Data.WordSearcherThreadResults;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class WordSearcherThread implements Runnable {

    //number of letters generated for each guessing set
    private int numberOfLettersPerSet;

    private int minimumWordLength;
    private int maximumWordLength;

    //point in the List that the thread will begin to search for valid words
    private int startingPoint;

    //point when the thread stops searching
    private int endingPoint;

    //a list of all the words to be searched (by all threads)
    private List<String> allWords;

    //the list of characters that the words we are looking for must be
    //made up of
    private List<Character> allowedCharacters;

    //the results instance that all threads have, and append found words to
    private WordSearcherThreadResults results;

    public WordSearcherThread(int startingPoint, int endingPoint, List<String> allWords,
                              List<Character> allowedCharacters, Properties properties, int letters_per_set,
                              WordSearcherThreadResults results) {

        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.allWords = allWords;
        this.allowedCharacters = allowedCharacters;
        this.numberOfLettersPerSet = letters_per_set;
        this.minimumWordLength = Integer.valueOf(properties.getProperty("minimum_word_length", "3"));
        this.maximumWordLength = Integer.valueOf(properties.getProperty("maximum_word_length", "7"));
        this.results = results;
    }

    @Override
    public void run() {
        //characters removed from this list as they are found in the word
        List<Character> currentWordRegex = new ArrayList<>();

        //look only at words between the points of the list assigned for this thread
        for (int i = startingPoint; i <= endingPoint; i++) {
            String current = allWords.get(i);

            //make sure that the word meets the length requirements set out in the config
            if (current.length() <=  numberOfLettersPerSet &&
                    current.length() >= minimumWordLength &&
                    current.length() <= maximumWordLength){

                boolean valid = true;

                currentWordRegex.addAll(allowedCharacters);

                //keep removing characters from currentWordRegex as those characters are
                //found in the current word. When it runs out of characters, or the
                //word regex doesn't contain a character the word has, the
                //word is determined to be invalid.
                for (char c : current.toCharArray()) {
                    if (currentWordRegex.contains(c)) {
                        int lastIndexOfChar = currentWordRegex.lastIndexOf(c);
                        currentWordRegex.remove(lastIndexOfChar);
                    } else {
                        valid = false;
                    }
                }

                //add valid words to the shared results instance.
                if (valid) {
                    if (!(results.addResult(current))) {
                        break;
                    }
                }
            }
            //make sure that the regex is empty before checking another
            //word, as there could be characters left over from the last
            //test
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
