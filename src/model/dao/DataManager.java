package model.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

import model.dao.blockchain.BlockchainDataMaganager;
import model.dto.blockchain.chainobject.ChainObject;
import util.style.TerminalStyle;

public abstract class DataManager {

    public static final Properties INIT_PROPERTIES = read("./ressources/init/init.conf");

    public static final String SAVED_LIST_SPACE = INIT_PROPERTIES.getProperty("SAVED_LIST_SPACE");
    public static final String SAVED_DIC_SPACE = INIT_PROPERTIES.getProperty("SAVED_DIC_SPACE");

    public static final String SAVED_LIST_TAG = INIT_PROPERTIES.getProperty("SAVED_LIST_TAG");
    public static final String SAVED_DIC_TAG = INIT_PROPERTIES.getProperty("SAVED_DIC_TAG");

    public static final String OBJECT_TYPE_KEY = INIT_PROPERTIES.getProperty("OBJECT_TYPE_KEY");

    // read
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

    public static ArrayList<String> textFileToStringArrayList(File file) {
        ArrayList<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    public static ArrayList<String> textFileToStringArrayList(String file) {
        return textFileToStringArrayList(Paths.get(file).toFile());
    }

    // check
    public static boolean fileExist(String path) {
        return Paths.get(path).toFile().exists();
    }

    public static boolean fileIsDirectory(String path) {
        return Paths.get(path).toFile().isDirectory();
    }

    // convert

    public static String objectHashMapToStringWithSpace(HashMap<Object, Object> dic) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Object o : dic.values()) {
            stringBuilder.append(o.toString());
            stringBuilder.append(SAVED_LIST_SPACE);
        }

        stringBuilder.append(SAVED_DIC_SPACE);

        for (Object o : dic.keySet()) {
            stringBuilder.append(o.toString());
            stringBuilder.append(SAVED_LIST_SPACE);
        }

        return stringBuilder.toString();
    }

    public static HashMap<Object, Object> stringToObjectHashMap(String string) {
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();

        String values = string.split(SAVED_DIC_SPACE)[0];
        String keys = string.split(SAVED_DIC_SPACE)[1];

        for (int i = 0; i < values.length(); i++) {
            hashMap.put(keys.split(SAVED_LIST_SPACE)[i], values.split(SAVED_LIST_SPACE)[i]);
        }

        return hashMap;
    }

    public static ArrayList<Object> stringToObjectArrayList(String string) {
        ArrayList<Object> objects = new ArrayList<Object>();
        for (String str : string.split(SAVED_LIST_SPACE)) {
            objects.add(str);
        }

        return objects;
    }

    public static String objectHashMapToAString(HashMap<Object, Object> objects) {
        String string = "";

        string += objectCollectionToAString(objects.values());
        string += SAVED_DIC_SPACE;
        string += objectCollectionToAString(objects.keySet());

        return string;
    }

    public static String objectCollectionToAString(Collection<Object> objects) {
        String string = "";

        for (Object object : objects) {
            if (object instanceof ChainObject) {
                ChainObject chainObject = (ChainObject) object;

                if (chainObject.getPrivateKey() != null) {
                    string += SAVED_LIST_SPACE
                            + BlockchainDataMaganager.privateKeyToString(chainObject.getPrivateKey());
                } else {
                    string += SAVED_LIST_SPACE
                            + BlockchainDataMaganager.publicKeyToString(chainObject.getPublicKey());

                }
            } else {
                string += SAVED_LIST_SPACE + object.toString();
            }
        }

        return string;
    }

    // folder
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

    // input
    public static String getUserInput(String prefix) {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        System.out.print(prefix + " ");

        String input = scanner.nextLine();

        return input;
    }

}
