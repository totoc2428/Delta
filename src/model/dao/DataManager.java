package model.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import io.jsonwebtoken.lang.Arrays;
import model.controleurs.node.terminal.style.TerminalStyle;

public abstract class DataManager {

    public static final Properties INIT_PROPERTIES = read("./ressources/init/init.conf");

    public static final String SAVED_LIST_SPACE = INIT_PROPERTIES.getProperty("SAVED_LIST_SPACE");
    public static final String SAVED_DIC_SPACE = INIT_PROPERTIES.getProperty("SAVED_DIC_SPACE");

    public static final String SAVED_LIST_TAG = INIT_PROPERTIES.getProperty("SAVED_LIST_TAG");
    public static final String SAVED_DIC_TAG = INIT_PROPERTIES.getProperty("SAVED_DIC_TAG");

    public static Properties read(File fileName) {
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            InputStreamReader reader = new InputStreamReader(fileInputStream, "UTF-8");
            properties.load(reader);
        } catch (FileNotFoundException e) {
            TerminalStyle.showError(e.getMessage());
        } catch (java.io.IOException e) {
            TerminalStyle.showError(e.getMessage());
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

    public static ArrayList<String> getAllFileNames(String directoryPath) {
        ArrayList<String> fileNames = new ArrayList<>();

        if (fileExist(directoryPath)) {
            File directory = Paths.get(directoryPath).toFile();
            if (directory.isDirectory()) {
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile()) {
                            fileNames.add(file.getName());
                        }
                    }
                }
            }
        }

        return fileNames;
    }

    public static String getUserInput(String prefix) {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        System.out.print(prefix + " ");

        String input = scanner.nextLine();

        return input;
    }
}
