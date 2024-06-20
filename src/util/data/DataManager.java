package util.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;

import exception.system.util.data.PropertiesReadingSystemException;

public abstract class DataManager {

    public static ArrayList<String> folderNameToAStringArrayList(File directoryPath) {
        ArrayList<String> fileNames = new ArrayList<>();
        if (directoryPath.exists() && !directoryPath.isFile() && directoryPath.isDirectory()) {
            File[] files = directoryPath.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        fileNames.add(file.getName());
                    }
                }
            }
        }

        return fileNames;
    }

    public static ArrayList<String> folderNameToAStringArrayList(String directoryPath) {
        return folderNameToAStringArrayList(Paths.get(directoryPath).toFile());
    }

    public static Properties readAFile(File fileName) throws PropertiesReadingSystemException {
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
        } catch (java.io.IOException e) {
            throw new PropertiesReadingSystemException();
        }
    }

    public static Properties readAFile(String file) throws PropertiesReadingSystemException {
        return readAFile(Paths.get(file).toFile());
    }
}
