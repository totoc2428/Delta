package util.primary;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;

import exception.system.util.data.DataManagerLoadException;
import exception.system.util.data.PropertiesReadingSystemException;
import exception.system.util.message.LangueageMessageNotFoundSystemException;
import exception.system.util.primary.PrimaryLoadException;
import util.data.DataManager;
import util.message.SystemMessage;
import util.message.done.DoneSystemMessage;

public abstract class Primary {
    private static final Properties INIT_PROPERTIES = initProperties();

    private static String systemLanguageValue = INIT_PROPERTIES.getProperty("SYSTEM_LANGUAGE");

    private static String dataManagerInitPath = INIT_PROPERTIES.getProperty("DATA_MANAGER_INIT_PATH");

    private static String blockchainManagerInitPath = INIT_PROPERTIES.getProperty("BLOCKCHAIN_MANAGER_INIT_PATH");

    private static String doneMessageInitPath = INIT_PROPERTIES
            .getProperty("DONE_MESSAGE_FOLDER_PATH");
    private static String errorMessageFolderPath = INIT_PROPERTIES
            .getProperty("ERROR_MESSAGE_FOLDER_PATH");
    private static String informationMessageFolderPath = INIT_PROPERTIES
            .getProperty("INFORMATION_MESSAGE_FOLDER_PATH");
    private static String neutralMessageFolderPath = INIT_PROPERTIES
            .getProperty("NEUTRAL_MESSAGE_FOLDER_PATH");
    private static String warningMessageFolderPath = INIT_PROPERTIES
            .getProperty("WARNING_MESSAGE_FOLDER_PATH");

    /* -INIT_PATH */
    /* --GETTER */
    /* ---DATA */
    public static String getDataManagerInitPath() {
        return dataManagerInitPath;
    }

    /* ---BLOCKCHAIN */
    public static String getBlockchainManagerInitPath() {
        return blockchainManagerInitPath;
    }

    /* -FOLDER_PATH */
    /* --GETTER */
    public static String getDoneMessageInitPath() {
        return doneMessageInitPath;
    }

    public static String getErrorMessageFolderPath() {
        return errorMessageFolderPath;
    }

    public static String getInformationMessageFolderPath() {
        return informationMessageFolderPath;
    }

    public static String getNeutralMessageFolderPath() {
        return neutralMessageFolderPath;
    }

    public static String getWarningMessageFolderPath() {
        return warningMessageFolderPath;
    }

    /* --SETTER */
    public static void setDoneMessageInitPath(String doneMessageInitPath)
            throws PropertiesReadingSystemException, LangueageMessageNotFoundSystemException {
        File file = Paths.get(doneMessageInitPath).toFile();
        if (file.exists() && file.isDirectory()) {
            if (checkAFolderLanguage(DataManager.folderNameToAStringArrayList(file), systemLanguageValue)) {
                Primary.doneMessageInitPath = doneMessageInitPath;
            } else {
                throw new LangueageMessageNotFoundSystemException();
            }
        } else {
            throw new PropertiesReadingSystemException();
        }
    }

    public static void setErrorMessageInitPath(String errorMessageFolderPath)
            throws PropertiesReadingSystemException, LangueageMessageNotFoundSystemException {
        File file = Paths.get(errorMessageFolderPath).toFile();
        if (file.exists() && file.isDirectory()) {
            if (checkAFolderLanguage(DataManager.folderNameToAStringArrayList(file), systemLanguageValue)) {
                Primary.errorMessageFolderPath = errorMessageFolderPath;
            } else {
                throw new LangueageMessageNotFoundSystemException();
            }
        } else {
            throw new PropertiesReadingSystemException();
        }
    }

    public static void setInformationMessageInitPath(String informationMessageFolderPath)
            throws PropertiesReadingSystemException, LangueageMessageNotFoundSystemException {
        File file = Paths.get(informationMessageFolderPath).toFile();
        if (file.exists() && file.isDirectory()) {
            if (checkAFolderLanguage(DataManager.folderNameToAStringArrayList(file), systemLanguageValue)) {
                Primary.informationMessageFolderPath = informationMessageFolderPath;
            } else {
                throw new LangueageMessageNotFoundSystemException();
            }
        } else {
            throw new PropertiesReadingSystemException();
        }
    }

    public static void setNeutralMessageInitPath(String neutralMessageFolderPath)
            throws PropertiesReadingSystemException, LangueageMessageNotFoundSystemException {
        File file = Paths.get(neutralMessageFolderPath).toFile();
        if (file.exists() && file.isDirectory()) {
            if (checkAFolderLanguage(DataManager.folderNameToAStringArrayList(file), systemLanguageValue)) {
                Primary.neutralMessageFolderPath = neutralMessageFolderPath;
            } else {
                throw new LangueageMessageNotFoundSystemException();
            }
        } else {
            throw new PropertiesReadingSystemException();
        }
    }

    public static void setWarningMessageInitPath(String warningMessageFolderPath)
            throws PropertiesReadingSystemException, LangueageMessageNotFoundSystemException {
        File file = Paths.get(warningMessageFolderPath).toFile();
        if (file.exists() && file.isDirectory()) {
            if (checkAFolderLanguage(DataManager.folderNameToAStringArrayList(file), systemLanguageValue)) {
                Primary.warningMessageFolderPath = warningMessageFolderPath;
            } else {
                throw new LangueageMessageNotFoundSystemException();
            }
        } else {
            throw new PropertiesReadingSystemException();
        }
    }

    /* -LANGUAGE */
    /* --GETTER */
    public static String getSystemlanguageValue() {
        return systemLanguageValue.toLowerCase();
    }

    /* --SETTER */
    public static void setSystemlanguageValue(String systemlanguageValue)
            throws LangueageMessageNotFoundSystemException {
        if (isLanguageAviable(systemlanguageValue)) {
            systemLanguageValue = systemlanguageValue.toUpperCase();

            new DoneSystemMessage("PrimarySetSystemlanguageValue").show();
        } else {
            throw new LangueageMessageNotFoundSystemException();
        }

    }

    /* --CHECKER */
    private static boolean isLanguageAviable(String systemLanguageCode) {
        return checkAFolderLanguage(DataManager.folderNameToAStringArrayList(doneMessageInitPath),
                systemLanguageCode)
                && checkAFolderLanguage(DataManager.folderNameToAStringArrayList(informationMessageFolderPath),
                        systemLanguageCode)
                && checkAFolderLanguage(DataManager.folderNameToAStringArrayList(errorMessageFolderPath),
                        systemLanguageCode)
                && checkAFolderLanguage(DataManager.folderNameToAStringArrayList(neutralMessageFolderPath),
                        systemLanguageCode)
                && checkAFolderLanguage(DataManager.folderNameToAStringArrayList(warningMessageFolderPath),
                        systemLanguageCode);
    }

    private static boolean checkAFolderLanguage(ArrayList<String> aviable, String systemLanguageCode) {
        boolean value = false;
        int i = 0;

        while (!value && i < aviable.size()) {
            value = aviable.get(i).toLowerCase().equals(systemLanguageCode.toLowerCase());
            i += 1;
        }

        return value;
    }

    /* -INIT */
    /* --PROPERTIES */
    private static Properties initProperties() {
        try {
            return DataManager.readAFile("./ressources/init.conf");
        } catch (PropertiesReadingSystemException e) {
            e.show();
            return null;
        }
    }

    /* --LOADER */
    public static void load() throws PrimaryLoadException {
        if (isLanguageAviable(INIT_PROPERTIES.getProperty("SYSTEM_LANGUAGE"))) {
            SystemMessage.reset();
            try {
                SystemMessage.load();
            } catch (LangueageMessageNotFoundSystemException e) {
                e.show();
            }

            try {
                DataManager.load();
            } catch (DataManagerLoadException e) {
                e.show();
            }

            new DoneSystemMessage("PrimaryLoad").show();
        } else {
            throw new PrimaryLoadException();
        }
    }
}