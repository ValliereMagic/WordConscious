package WordConscious.Data;

public class ConfigurationFileReader {

    private static final String[] confSet = {"number-of-words-per-set:", "number-of-letters-to-choose-from:"};
    private static final Object[] dataSet = {0, 0};

    public static Config getConfig() {

        for (String line : TextFileHandler.getContentFromFile(FileConstants.locationOfConfigFile)) {

            if (line.startsWith("#")) {
                continue;
            }

            for (int i = 0; i < confSet.length; i++) {

                if (line.startsWith(confSet[i])) {
                    String value = line.replace(confSet[i], "").trim();
                    dataSet[i] = Integer.valueOf(value);
                }
            }
        }
        return new Config((int) dataSet[0], (int) dataSet[1]);
    }
}
