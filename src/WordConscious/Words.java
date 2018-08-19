package WordConscious;

import WordConscious.Data.*;
import WordConscious.Threads.WordSearcherThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

class Words {
    private Properties properties;
    private int lettersPerSet;

    Words(Properties properties) {
        this.properties = properties;
        this.lettersPerSet = Integer.valueOf(properties.getProperty("number_of_letters_per_set", "8"));
    }

    Guessables getGuessables() {
        List<Character> guessableChars = getGuessableCharacters();
        List<String> guessableWords = getGuessWords(guessableChars, properties);
        return new Guessables(guessableChars, guessableWords);
    }

    private List<String> getGuessWords(List<Character> guessableCharacters, Properties properties) {
        //common results appended to by all WordSearcherThreads
        WordSearcherThreadResults results = new WordSearcherThreadResults(properties);

        List<String> allWords = TextFileHandler.getContentFromFile(FileConstants.locationOfWordsFile);
        List<Thread> threads = new ArrayList<>();

        //calculate optimum number of threads based on amount of available processors
        int numberOfThreads = getThreadNumber();
        int startValue = 0;
        int incrementValue = allWords.size() / numberOfThreads;

        //create all the WordSearcherThread objects, create threads for each of them, and start all the threads.
        for (int i = 0; i < numberOfThreads; i++) {
            WordSearcherThread currentSearcher = new WordSearcherThread(startValue,
                    startValue + incrementValue - 1, allWords, guessableCharacters, properties,
                    lettersPerSet, results);

            Thread currentThread = new Thread(currentSearcher);

            threads.add(currentThread);
            currentThread.start();

            startValue = startValue + incrementValue;
        }

        //wait for the threads to complete.
        for (Thread t : threads) {
            try {
                t.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return results.getResults();
    }

    private int getThreadNumber() {
        int[] possibleThreadValues = {1, 2, 3, 4, 5, 6, 10, 12, 15, 20, 30};
        int threadNumber = Runtime.getRuntime().availableProcessors();
        int distance = Math.abs(possibleThreadValues[0] - threadNumber);
        int idx = 0;

        for (int c = 1; c < possibleThreadValues.length; c++) {
            int cDistance = Math.abs(possibleThreadValues[c] - threadNumber);

            if (cDistance < distance) {
                idx = c;
                distance = cDistance;
            }
        }
        return possibleThreadValues[idx];
    }

    private List<Character> getGuessableCharacters() {
        List<Character> guessableCharacters = new ArrayList<>();
        char[] possibleChars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
                'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        char[] mustContainOneOf = {'a', 'e', 'i', 'o', 'u'};

        for (int i = 0; i < lettersPerSet; ) {
            int randomInt = ThreadLocalRandom.current().nextInt(0, 26);
            guessableCharacters.add(possibleChars[randomInt]);

            if (i == lettersPerSet - 1) {
                boolean oneExists = false;

                for (char c : mustContainOneOf) {
                    if (guessableCharacters.contains(c)) {
                        oneExists = true;
                        break;
                    }
                }

                if (!oneExists) {
                    int randomVowelIndex = ThreadLocalRandom.current().nextInt(0, 5);
                    guessableCharacters.add(mustContainOneOf[randomVowelIndex]);
                    break;
                }
            }
            i++;
        }
        return guessableCharacters;
    }
}
