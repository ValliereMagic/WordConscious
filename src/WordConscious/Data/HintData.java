package WordConscious.Data;

public class HintData {
    private int currentHintIndex = -1;
    private String currentGuessWord = "";
    private int charsRevealed = 0;
    private String response = "";

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getCurrentHintIndex() {
        return currentHintIndex;
    }

    public void setCurrentHintIndex(int currentHintIndex) {
        this.currentHintIndex = currentHintIndex;
    }

    public String getCurrentGuessWord() {
        return currentGuessWord;
    }

    public void setCurrentGuessWord(String currentGuessWord) {
        this.currentGuessWord = currentGuessWord;
    }

    public int getCharsRevealed() {
        return charsRevealed;
    }

    public void setCharsRevealed(int charsRevealed) {
        this.charsRevealed = charsRevealed;
    }
}
