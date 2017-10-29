package WordConscious.Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class TextFileHandler {

    //Read lines from text file and return each line as a string
    //element in an array list.
    public static ArrayList<String> getContentFromFile(String filename) {
        ArrayList<String> fileContent = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();

            while (line != null) {
                fileContent.add(line);
                line = br.readLine();
            }

            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
