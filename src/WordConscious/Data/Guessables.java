package WordConscious.Data;

import java.util.List;

public class Guessables {

    private List<Character> guessableChars;
    private List<String> guessableWords;

    public Guessables(List<Character> guessableChars, List<String> guessableWords) {
        this.guessableChars = guessableChars;
        this.guessableWords = guessableWords;
    }

    public List<Character> getGuessableChars() {
        return guessableChars;
    }

    public List<String> getGuessableWords() {
        return guessableWords;
    }
}
