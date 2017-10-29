package WordConscious;


import WordConscious.Data.*;
import WordConscious.Threads.WordSearcherThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    private static Config configuration;

    public static void main(String[] args) {
        init();
        List<Character> guessableCharacters = getGuessableCharacters();
        List<String> guessableWords = getGuessWords(guessableCharacters);
        System.out.println(guessableCharacters);
        System.out.println("===========================");
        System.out.println(guessableWords);
    }

    private static void init() {
        configuration = ConfigurationFileReader.getConfig();
    }

    private static List<String> getGuessWords(List<Character> guessableCharacters) {
        List<WordSearcherThread> searcherThreads = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        int numberOfThreads = getThreadNumber();
        List<String> allWords = TextFileHandler.getContentFromFile(FileConstants.locationOfWordsFile);
        int startValue = 0;
        int incrementValue = allWords.size() / numberOfThreads;

        for (int i = 0; i < numberOfThreads; i++) {
            searcherThreads.add(new WordSearcherThread(startValue, startValue + incrementValue -1, allWords, guessableCharacters, configuration));
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
        return WordSearcherThreadResults.getResults();
    }

    private static int getThreadNumber() {
        int[] possibleThreadValues = {2,4,5,8,10,16};
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

    private static List<Character> getGuessableCharacters() {
        List<Character> guessableCharacters = new ArrayList<>();
        char[] possibleChars = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        char[] mustContainOneOf = {'a','e','i','o','u'};

        for (int i = 0; i < configuration.getLettersPerSet();) {
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
