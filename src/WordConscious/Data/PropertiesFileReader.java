package WordConscious.Data;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesFileReader {

    public static Properties getProperties() {
        String propertiesFilePath = FileConstants.locationOfConfigFile;

        Properties gameProperties = new Properties();
        try {
            gameProperties.load(new FileInputStream(propertiesFilePath));

            return gameProperties;

        } catch (Exception e) {
            System.err.println("Error. Unable to load configuration file at " +
                    propertiesFilePath);

            e.printStackTrace();
        }

        return gameProperties;
    }
}
