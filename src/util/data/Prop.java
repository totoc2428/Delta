package util.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import util.exception.util.data.DataPropWriteConfigException;

public class Prop {
    /**
     * This method take a file where are saved a properties type and read it to a
     * usable format.
     * 
     * @param fileName the file Name who you want to read.
     * @return A {@link Properties} instance contain the key-value pair saved in the
     *         file.
     */
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

    /**
     * This method take a {@link Properties} instance to save it in a regular file
     * (.conf).
     * 
     * @param properties the Properties instance who you want to read.
     * @param filePath   the destination sources : where are save your file.
     */
    public static void writeConfig(Properties properties, String filePath) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            properties.store(fileOutputStream, "");
        } catch (IOException e) {
            new DataPropWriteConfigException();
        }
    }
}
