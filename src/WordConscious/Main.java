package WordConscious;


import WordConscious.Data.Config;
import WordConscious.Data.ConfigurationFileReader;
import WordConscious.Data.Guessables;

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

            if (guessables.getGuessableWords().size() != 0) {
                int currentHintIndex = -1;
                String currentGuessWord = "";
                int charsRevealed = 0;

                System.out.println(guessables.getGuessableChars());
                System.out.println("===========================");
                //System.out.println(guessables.getGuessableWords());
                System.out.println("Enter a word to guess, or type ? to list commands.");

                while (true) {
                    System.out.print("> ");
                    String userInput = input.nextLine();

                    if (userInput.equals("?")) {
                        System.out.println("Available Commands: \n" +
                                "   hint -> get a hint on a random un-guessed word.\n" +
                                "   quit -> quit the game.\n" +
                                "   shuffle -> shuffle the letters.");

                    } else if (userInput.toLowerCase().equals("quit")) {
                        quitting = true;
                        break;

                    } else if (userInput.toLowerCase().equals("shuffle")) {
                        List<Character> letters = guessables.getGuessableChars();
                        Collections.shuffle(letters);
                        System.out.println(letters);

                    } else if (userInput.toLowerCase().equals("hint")) {
                        int maxValue = guessables.getGuessableWords().size();

                        if (currentHintIndex == -1 || currentHintIndex > (maxValue - 1) || !guessables.getGuessableWords().get(currentHintIndex).equals(currentGuessWord)) {
                            currentHintIndex = ThreadLocalRandom.current().nextInt(0, maxValue);
                            charsRevealed = 0;
                        }

                        String randomWord = guessables.getGuessableWords().get(currentHintIndex);
                        currentGuessWord = randomWord;
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
                        charsRevealed++;
                        System.out.println("hint: " + builder.toString());

                    } else {
                        String cleanedInput = userInput.toLowerCase().trim();
                        if (guessables.getGuessableWords().contains(cleanedInput)) {
                            guessables.getGuessableWords().remove(cleanedInput);
                            if (guessables.getGuessableWords().size() == 0) {
                                System.out.println("Last word found! Nice job.");
                                break;
                            }
                            System.out.println("Word Found! Words left: " + guessables.getGuessableWords().size());
                        }
                    }

                }
            }
        }
    }

    private static void init() {
        configuration = ConfigurationFileReader.getConfig();
    }
}
