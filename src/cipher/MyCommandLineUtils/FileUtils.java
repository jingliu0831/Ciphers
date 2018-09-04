package cipher.MyCommandLineUtils;

import cipher.FrequencyAnalyzer;
import cipher.Main;

import java.io.BufferedReader;
import java.io.IOException;

public class FileUtils {
    public static void addFileToFrequencyAnalyzer(String filename, FrequencyAnalyzer frequencyAnalyzer) {
        try {
            BufferedReader bufferedReader = Main.openFile(filename);

            int character = bufferedReader.read();
            while (character != -1) {
                frequencyAnalyzer.addChar((char) character);
                character = bufferedReader.read();
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
}
