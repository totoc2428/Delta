package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Properties;

import exception.system.util.data.PropertieReadingSystemException;

public abstract class DataManager {
    public static Properties readAFile(File fileName) throws PropertieReadingSystemException {
        try {
            Properties properties = new Properties();

            FileInputStream fileInputStream = new FileInputStream(fileName);
            InputStreamReader reader = new InputStreamReader(fileInputStream, "UTF-8");
            properties.load(reader);
            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key);
                if (value != null && value.length() >= 2 && value.startsWith("\"") && value.endsWith("\"")) {
                    value = value.substring(1, value.length() - 1);
                    properties.setProperty(key, value);
                }
            }

            return properties;
        } catch (FileNotFoundException e) {
            throw new PropertieReadingSystemException();
        } catch (java.io.IOException e) {
            throw new PropertieReadingSystemException();
        }
    }

    public static Properties readAFile(String file) throws PropertieReadingSystemException {
        return readAFile(Paths.get(file).toFile());
    }
}
