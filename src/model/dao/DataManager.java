package model.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import io.jsonwebtoken.lang.Arrays;

public abstract class DataManager {

    public static final Properties INIT_PROPERTIES = read("./ressources/init/init.conf");

    public static final String SAVED_LIST_SPACE = INIT_PROPERTIES.getProperty("SAVED_LIST_SPACE");
    public static final String SAVED_DIC_SPACE = INIT_PROPERTIES.getProperty("SAVED_DIC_SPACE");

    public static final String SAVED_LIST_TAG = INIT_PROPERTIES.getProperty("SAVED_LIST_TAG");
    public static final String SAVED_DIC_TAG = INIT_PROPERTIES.getProperty("SAVED_DIC_TAG");

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

    public static boolean fileExist(String path) {
        return Paths.get(path).toFile().exists();
    }

    public static boolean fileIsDirectory(String path) {
        return Paths.get(path).toFile().isDirectory();
    }

    public static ArrayList<String> stringToStringArrayList(String strings) {
        strings.replace(SAVED_LIST_TAG, "");
        String[] stringsTab = strings.split(SAVED_LIST_SPACE);

        return new ArrayList<String>(Arrays.asList(stringsTab));
    }

    public static String getUserInput(String prefix) {
        Scanner scanner = new Scanner(System.in);

        System.out.print(prefix + " ");

        String input = scanner.nextLine();
        scanner.close();

        return input;
    }
}
