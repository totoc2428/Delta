package model.dao;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.time.LocalDate;
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

    /**
     * Do a properties from file paramter.
     * 
     * @param fileName the file you want to read.
     * @return the file in a properties format.
     */
    public static Properties read(File fileName) {
        Properties properties = new Properties();
        try {
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
        } catch (FileNotFoundException e) {
            TerminalStyle.showError(e.getMessage());
        } catch (java.io.IOException e) {
            TerminalStyle.showError(e.getMessage());
        }
        return properties;
    }

    /**
     * {@link DataManager#read(File)} the same method with the file name in a string
     * format.
     * 
     * @param fileName the file Name in a string format.
     * @return the file in a Properties format.
     */
    public static Properties read(String fileName) {
        try {
            return read(Paths.get(fileName).toFile());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param file a text file.
     * @return the text file in a array list. each index of the array list
     *         corresponding to a line in the text file.
     */
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

    /**
     * @param file the file path in a string format.
     * @return the text file in a array list. each index of the array list
     *         corresponding to a line in the text file.
     */
    public static ArrayList<String> textFileToStringArrayList(String file) {
        if (fileExist(file)) {
            return textFileToStringArrayList(Paths.get(file).toFile());
        } else {
            return null;
        }
    }

    // save
    /**
     * @param properties the properties you want to save
     * @param fileName   the file where the properties will be saved.
     */
    public static void save(Properties properties, String fileName) {
        if (fileExist(fileName)) {
            try (OutputStream outputStream = new FileOutputStream(fileName)) {
                properties.store(outputStream, "");
            } catch (IOException e) {
                TerminalStyle.showError(e.getMessage());
            }
        }
    }

    // check
    /**
     * @param path the file in a string format.
     * @return a boolean : true if the file exist.
     */
    public static boolean fileExist(String path) {
        return Paths.get(path).toFile().exists();
    }

    /**
     * @param path the file in a string format.
     * @return a boolean : true if the file is a directory.
     */
    public static boolean fileIsDirectory(String path) {
        return Paths.get(path).toFile().isDirectory();
    }

    // convert

    /**
     * This medthod convert the dic into a string. For that it use the
     * {@link DataManager#objectCollectionToAString(Collection)} method to save
     * first the keyset of the hasMap separed by {@link DataManager#SAVED_DIC_SPACE}
     * and follow by the value for each key convert with the same method.
     * 
     * @param dic the dic you want to convert in to a string.
     * @return the dic in a string format.
     */
    public static String objectHashMapToString(HashMap<Object, Object> dic) {
        String string = "";

        string += objectCollectionToAString(dic.values());
        string += SAVED_DIC_SPACE;
        string += objectCollectionToAString(dic.keySet());

        return string;
    }

    /**
     * This method take a string convert to a HashMap. To work split the string with
     * the {@link DataManager#SAVED_DIC_SPACE}. The index 0 of the split is saved in
     * the KEY of the hashMap. The index 1 is saved on the VALUE of the hashMap.
     * To read each value and key this method use the
     * {@link DataManager#stringToObjectArrayList(String)}.
     * 
     * @param string the string you want to convert to a HashMap of Object.
     * @return an HashMap<Object,Object> corresponding to the string.
     */
    public static HashMap<Object, Object> stringToObjectHashMap(String string) {
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();

        String values = string.split(SAVED_DIC_SPACE)[0];
        String keys = string.split(SAVED_DIC_SPACE)[1];

        for (int i = 0; i < values.length(); i++) {
            hashMap.put(keys.split(SAVED_LIST_SPACE)[i], values.split(SAVED_LIST_SPACE)[i]);
        }

        return hashMap;
    }

    /**
     * This method take a string to convert to a ArrayList of String. To work they
     * split the string with {@link DataManager#SAVED_LIST_SPACE}. They put each
     * value of the split in to the String ArrayList.
     * 
     * @param string the string you want to convert to a string ArrayList.
     * @return the string in arrayList.
     */
    public static ArrayList<Object> stringToObjectArrayList(String string) {
        ArrayList<Object> objects = new ArrayList<Object>();
        for (String str : string.split(SAVED_LIST_SPACE)) {
            objects.add(str);
        }

        return objects;
    }

    /**
     * This method convert a collection in a string. To work they add in a string
     * each value of the collection separed by
     * {@link DataManager#SAVED_LIST_SPACE}.
     * 
     * @param objects the collection of the object you want to convert.
     * @return the collection in a string format.
     */
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

    /**
     * @param directoryPath the path of the folder.
     * @return all the name of any file containing in the folder in a string format.
     */
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
    /**
     * @param prefix the prefix showed before the input.
     * @return the user input in a string format.
     */
    public static String getUserInput(String prefix) {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        System.out.print(prefix + " ");

        String input = scanner.nextLine();

        return input;
    }

    /**
     * @param prefix get the user inpput with hiden input
     * @return the input do by the user.
     */
    public static String getUserSecretInput(String prefix) {
        Console console = System.console();
        if (console == null) {
            System.exit(1);
        }

        char[] consoleArray = console.readPassword(prefix + " ");

        return new String(consoleArray);
    }

    public static boolean wordContainUpperCase(String word) {
        boolean contain = false;

        for (int i = 0; i < word.length(); i++) {
            if (Character.isUpperCase(word.charAt(i))) {
                contain = true;
                break;
            }
        }

        return contain;
    }

    public static boolean wordContainSpecialChar(String word) {
        boolean contain = false;
        String[] specialChars = INIT_PROPERTIES.getProperty("specialChar").split(SAVED_LIST_SPACE);

        for (int i = 0; i < word.length(); i++) {
            for (String specialChar : specialChars) {
                if (specialChar.equals(word.charAt(i) + "")) {
                    contain = true;
                    break;
                }
            }
        }

        return contain;

    }

    public static boolean passPhraseIsInCorectFormat(String passPhrase) {
        boolean isCorrect = false;
        if (passPhrase != null) {
            if (passPhrase.length() >= 8) {
                if (!passPhrase.contains(" ") && DataManager.wordContainUpperCase(passPhrase)
                        && DataManager.wordContainSpecialChar(passPhrase)) {
                    isCorrect = true;
                }
            }
        }

        return isCorrect;
    }

    public static LocalDate parseDate(String localDate) {
        String[] localDateTab = (localDate.replace('/', '-')).split("-");
        localDate = localDateTab[2] + "-" + localDateTab[1] + "-" + localDateTab[0];

        return LocalDate.parse(localDate);
    }
}
