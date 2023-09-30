package serveur.util.data.text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class DataText {
    public static ArrayList<String> TextToArrayListOfString(File file) {
        ArrayList<String> data = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while (line != null) {
                line = br.readLine();
                data.add(line);
            }
            br.close();
        } catch (Exception e) {
            new Exception(e.getMessage());
        }
        return data;
    }
}
