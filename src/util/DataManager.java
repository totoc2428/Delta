package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Properties;

public abstract class DataManager {

    public static final Properties INIT_PROPERTIES = read("./ressources/init/init.conf");

    public static Properties read(File fileName) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(fileName));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (java.io.IOException e) {
            System.out.println(e.getMessage());
        }
        return properties;
    }

    public static Properties read(String fileName) {
        try {
            return read(Paths.get(fileName).toFile());
        } catch (Exception e) {
            return null;
        }
    }
}
