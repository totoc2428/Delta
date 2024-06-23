package util.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import exception.system.util.data.DataManagerLoadException;
import exception.system.util.data.PropertiesReadingSystemException;
import exception.system.util.data.WriteInAFileSystemException;
import util.tool.Primary;
import java.io.OutputStream;

public abstract class DataManager {

    private static Properties initDataProperties;

    private static String savedListSpace;
    private static String savedDicSpace;

    private static String listTag;
    private static String dicTag;

    /* -LOADER */
    public static void load() throws DataManagerLoadException {
        File file = Paths.get(Primary.getDataManagerInitPath()).toFile();
        if (file.exists() && file.isFile()) {
            try {
                initDataProperties = DataManager.readAFile(file);

                savedListSpace = initDataProperties.getProperty("SAVED_LIST_SPACE");
                savedDicSpace = initDataProperties.getProperty("SAVED_DIC_SPACE");

                listTag = initDataProperties.getProperty("LIST_TAG");
                dicTag = initDataProperties.getProperty("DIC_TAG");
            } catch (PropertiesReadingSystemException e) {
                e.show();
                throw new DataManagerLoadException();
            }
        } else {
            throw new DataManagerLoadException();
        }
    }

    /* -INIT */
    /* --GETTER */
    public static Properties getInit() {
        return initDataProperties;
    }

    /* -READ_METHOD */
    /* --FOLDER_LIST */
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

    /* --PROPERTIES */
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

    /* -WRITE_METHOD */
    /* --PROPERTIES */
    public static void writeInAFile(Properties properties, File srcDestinaionPath) throws WriteInAFileSystemException {
        if (!srcDestinaionPath.exists()) {
            try {
                srcDestinaionPath.createNewFile();
            } catch (java.io.IOException e) {
                throw new WriteInAFileSystemException();
            }
        }
        try (OutputStream outputStream = new FileOutputStream(srcDestinaionPath)) {
            properties.store(outputStream, "");
        } catch (java.io.IOException e) {
            throw new WriteInAFileSystemException();
        }
    }

    public static void writeInAFile(Properties properties, String srcDestinaionPath)
            throws WriteInAFileSystemException {
        if (srcDestinaionPath != null) {
            writeInAFile(properties, Paths.get(srcDestinaionPath).toFile());
        } else {
            throw new WriteInAFileSystemException();
        }
    }

    /* -GETTER */
    /* --TAG */
    public static String getDicTag() {
        return dicTag;
    }

    public static String getListTag() {
        return listTag;
    }

    /* --SPACE */
    public static String getSavedListSpace() {
        return savedListSpace;
    }

    public static String getSavedDicSpace() {
        return savedDicSpace;
    }

    /* -SAVING_FORMAT */
    /* --LIST_TO_SAVED_FORMAT */
    public static String listToSavingFormat(Collection<String> list) {
        String listInASavedFormat = "";

        for (String string : list) {
            listInASavedFormat += getSavedListSpace() + string;
        }

        listInASavedFormat = listInASavedFormat.substring(getSavedListSpace().length(), listInASavedFormat.length());

        return listInASavedFormat;
    }

    /* --DIC_TO_SAVED_FORMAT */
    public static String dicToSavingFormat(Map<String, String> dic) {
        return listToSavingFormat(dic.keySet()) + getSavedDicSpace() + listToSavingFormat(dic.values());
    }
}
