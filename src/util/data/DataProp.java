package util.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import util.exception.util.data.DataPropWriteConfigException;

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

    public static void writeConfig(Properties properties, String filePath) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            properties.store(fileOutputStream, "Configuration File");
        } catch (IOException e) {
            new DataPropWriteConfigException();
        }
    }
}
