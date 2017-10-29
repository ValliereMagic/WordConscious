package WordConscious.Data;

public class Config {

    private int wordsPerSet;
    private int lettersPerSet;

    public Config(int wordsPerSet, int lettersPerSet) {
        this.wordsPerSet = wordsPerSet;
        this.lettersPerSet = lettersPerSet;
    }

    public int getWordsPerSet() {
        return wordsPerSet;
    }

    public int getLettersPerSet() {
        return lettersPerSet;
    }

    @Override
    public String toString() {
        return "Config{" +
                "wordsPerSet=" + wordsPerSet +
                ", lettersPerSet=" + lettersPerSet +
                '}';
    }
}
