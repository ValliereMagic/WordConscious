package WordConscious;

import WordConscious.Data.*;
import WordConscious.Threads.WordSearcherThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

class Words {

    static Guessables getGuessables(Config config) {
        List<Character> guessableChars = getGuessableCharacters(config);
        List<String> guessableWords = getGuessWords(guessableChars, config);
        return new Guessables(guessableChars, guessableWords);
    }

    private static List<String> getGuessWords(List<Character> guessableCharacters, Config configuration) {
        WordSearcherThreadResults results = new WordSearcherThreadResults();

        List<String> allWords = TextFileHandler.getContentFromFile(FileConstants.locationOfWordsFile);
        List<WordSearcherThread> searcherThreads = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();

        int numberOfThreads = getThreadNumber();
        int startValue = 0;
        int incrementValue = allWords.size() / numberOfThreads;

        for (int i = 0; i < numberOfThreads; i++) {
            searcherThreads.add(new WordSearcherThread(startValue, startValue + incrementValue - 1, allWords, guessableCharacters, configuration, results));
            startValue = startValue + incrementValue;
        }

        for (WordSearcherThread t : searcherThreads) {
            threads.add(new Thread(t));
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return results.getResults();
    }

    private static int getThreadNumber() {
        int[] possibleThreadValues = {1, 5};
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

    private static List<Character> getGuessableCharacters(Config configuration) {
        List<Character> guessableCharacters = new ArrayList<>();
        char[] possibleChars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        char[] mustContainOneOf = {'a', 'e', 'i', 'o', 'u'};

        for (int i = 0; i < configuration.getLettersPerSet(); ) {
            int randomInt = ThreadLocalRandom.current().nextInt(0, 26);
            guessableCharacters.add(possibleChars[randomInt]);

            if (i == configuration.getLettersPerSet() - 1) {
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
