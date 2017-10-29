package WordConscious;


import WordConscious.Data.*;

import java.util.Scanner;

public class Main {

    private static Config configuration;

    public static void main(String[] args) {
        init();
        Scanner input = new Scanner(System.in);
        boolean quitting = false;
        while (!quitting) {
            Guessables guessables = Words.getGuessables(configuration);
            System.out.println(guessables.getGuessableChars());
            System.out.println("===========================");
        System.out.println(guessables.getGuessableWords());
            while (true) {
                if (guessables.getGuessableWords().size() != 0) {
                    System.out.print("Enter a word to guess, or type 'hint' to get a hint, or type 'quit' to quit: ");
                    String userInput = input.nextLine();

                    if (userInput.toLowerCase().equals("quit")) {
                        quitting = true;
                        break;
                    } else if (userInput.toLowerCase().equals("hint")) {
                        System.out.println("not implemented yet.");
                    } else {
                        String cleanedInput = userInput.toLowerCase().trim();
                        if (guessables.getGuessableWords().contains(cleanedInput)) {
                            guessables.getGuessableWords().remove(cleanedInput);
                            if (guessables.getGuessableWords().size() == 0) {
                                break;
                            }
                            System.out.println(guessables.getGuessableWords().size());
                        }
                    }
                } else {
                    break;
                }
            }
        }
    }

    private static void init() {
        configuration = ConfigurationFileReader.getConfig();
    }
}
