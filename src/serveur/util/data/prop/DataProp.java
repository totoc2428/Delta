package serveur.util.data.prop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class DataProp {
    public static Properties read(File fileName) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(fileName));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return properties;
    }

    public static void write(Properties properties, String fileName, String comment) {
        try {
            FileOutputStream out = new FileOutputStream(fileName + ".prop");
            try {
                properties.store(out, comment != null ? comment : "//");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
