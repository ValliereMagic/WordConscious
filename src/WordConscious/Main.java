package WordConscious;


import WordConscious.Data.Config;
import WordConscious.Data.ConfigurationFileReader;
import WordConscious.Data.Guessables;
import WordConscious.Data.HintData;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    private static Config configuration;

    public static void main(String[] args) {
        init();
        Scanner input = new Scanner(System.in);
        boolean quitting = false;

        while (!quitting) {
            Guessables guessables = Words.getGuessables(configuration);
            List<String> currentGuessableWords = guessables.getGuessableWords();
            List<Character> currentGuessableChars = guessables.getGuessableChars();

            if (currentGuessableWords.size() != 0) {
                HintData hData = new HintData();

                System.out.println(currentGuessableChars);
                System.out.println("===========================");
                System.out.println("Enter a word to guess, or type ? to list commands.");

                while (true) {
                    System.out.print("> ");
                    String userInput = input.nextLine();

                    if (userInput.equals("?")) {
                        System.out.println("Available Commands: \n" +
                                "   hint -> get a hint on a random un-guessed word.\n" +
                                "   quit -> quit the game.\n" +
                                "   shuffle -> shuffle the letters.\n" +
                                "   howmany -> list how many words are left.");

                    } else if (userInput.toLowerCase().equals("quit")) {
                        quitting = true;
                        break;

                    } else if (userInput.toLowerCase().equals("shuffle")) {
                        Collections.shuffle(currentGuessableChars);
                        System.out.println(currentGuessableChars);

                    } else if (userInput.toLowerCase().equals("hint")) {
                        hData = getHint(hData, currentGuessableWords);
                        System.out.println(hData.getResponse());

                    } else if (userInput.toLowerCase().equals("howmany")) {
                        System.out.println("There are: " + currentGuessableWords.size() + " words left.");

                    } else {
                        String cleanedInput = userInput.toLowerCase().trim();

                        if (currentGuessableWords.contains(cleanedInput)) {
                            currentGuessableWords.remove(cleanedInput);

                            if (currentGuessableWords.size() == 0) {
                                System.out.println("Last word found! Nice job.");
                                break;
                            }
                            System.out.println("Word Found! Words left: " + currentGuessableWords.size());
                        }
                    }

                }
            }
        }
    }

    private static HintData getHint(HintData hData, List<String> guessableWords) {
        int maxValue = guessableWords.size();
        int currentHintIndex = hData.getCurrentHintIndex();
        int charsRevealed = hData.getCharsRevealed();
        String currentGuessWord = hData.getCurrentGuessWord();

        if (currentHintIndex == -1 || currentHintIndex > (maxValue - 1) || !guessableWords.get(currentHintIndex).equals(currentGuessWord)) {
            hData.setCurrentHintIndex(currentHintIndex = ThreadLocalRandom.current().nextInt(0, maxValue));
            charsRevealed = 0;
        }

        String randomWord = guessableWords.get(currentHintIndex);
        hData.setCurrentGuessWord(randomWord);
        StringBuilder builder = new StringBuilder();

        int i = 0;
        for (char c : randomWord.toCharArray()) {
            if (i <= charsRevealed) {
                builder.append(c);
            } else {
                builder.append("*");
            }
            i++;
        }

        hData.setCharsRevealed(charsRevealed + 1);
        hData.setResponse("hint: " + builder.toString());

        return hData;
    }

    private static void init() {
        configuration = ConfigurationFileReader.getConfig();
    }
}
