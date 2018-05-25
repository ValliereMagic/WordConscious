package WordConscious;


import WordConscious.Data.Config;
import WordConscious.Data.ConfigurationFileReader;
import WordConscious.Data.Guessables;
import WordConscious.Data.HintData;

import java.util.*;
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
            List<String> currentFoundWords = new ArrayList<>();

            if (currentGuessableWords.size() != 0) {
                HintData hData = new HintData();

                System.out.println(prettyPrintCollection(currentGuessableChars));
                System.out.println("===========================");
                System.out.println("Enter a word to guess, or type ? to list commands.");

                playing:
                while (true) {
                    System.out.print("> ");
                    String userInput = input.nextLine();
                    userInput = userInput.toLowerCase().trim();

                    switch (userInput) {
                        case "?":
                            System.out.println("Available Commands: \n" +
                                    "   ht -> get a hint on a random un-guessed word.\n" +
                                    "   f -> list the words found by the player.\n"+
                                    "   s -> shuffle the letters.\n" +
                                    "   hw -> list how many words are left.\n" +
                                    "   q -> quit the game.");

                            break;

                        case "q":
                            quitting = true;
                            break playing;

                        case "s":
                            Collections.shuffle(currentGuessableChars);
                            System.out.println(prettyPrintCollection(currentGuessableChars));
                            break;

                        case "ht":
                            getHint(hData, currentGuessableWords);
                            System.out.println(hData.getResponse());
                            break;

                        case "hw":
                            System.out.println("There are: " + currentGuessableWords.size() + " words left.");
                            break;

                        case "f":

                            if (currentFoundWords.size() > 0) {
                                System.out.println("You have found: " + prettyPrintCollection(currentFoundWords));

                            } else {
                                System.out.println("You haven't found any words yet!");
                            }
                            break;

                        default:
                            if (currentGuessableWords.contains(userInput)) {

                                currentGuessableWords.remove(userInput);
                                currentFoundWords.add(userInput);

                                if (currentGuessableWords.size() == 0) {
                                    System.out.println("Last word found! Nice job.");
                                    break playing;
                                }
                                System.out.println("Word Found! Words left: " + currentGuessableWords.size());
                            }
                            break;
                    }

                }
            }
        }
    }

    private static String prettyPrintCollection(Collection collection) {
        String s = collection.toString();
        return s.replace("[", "").replace("]", "");
    }

    private static void getHint(HintData hData, List<String> guessableWords) {
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
    }

    private static void init() {
        configuration = ConfigurationFileReader.getConfig();
    }
}
