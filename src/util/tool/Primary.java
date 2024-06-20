package util.tool;

import java.util.ArrayList;
import java.util.Properties;

import exception.system.util.data.PropertiesReadingSystemException;
import exception.system.util.message.LangueageMessageNotFoundSystemException;
import exception.system.util.tool.PrimaryLoadException;
import util.data.DataManager;
import util.message.SystemMessage;
import util.message.done.DoneSystemMessage;

public abstract class Primary {
    private static final Properties INIT_PROPERTIES = initProperties();

    private static String systemLanguageValue = INIT_PROPERTIES.getProperty("SYSTEM_LANGUAGE");

    public static final String DATA_MANAGER_INIT_PATH = INIT_PROPERTIES.getProperty("DATA_MANAGER_INIT_PATH");

    public static final String DONE_MESSAGE_FOLDER_PATH = INIT_PROPERTIES
            .getProperty("DONE_MESSAGE_FOLDER_PATH");
    public static final String ERROR_MESSAGE_FOLDER_PATH = INIT_PROPERTIES
            .getProperty("ERROR_MESSAGE_FOLDER_PATH");
    public static final String INFORMATION_MESSAGE_FOLDER_PATH = INIT_PROPERTIES
            .getProperty("INFORMATION_MESSAGE_FOLDER_PATH");
    public static final String NEUTRAL_MESSAGE_FOLDER_PATH = INIT_PROPERTIES
            .getProperty("NEUTRAL_MESSAGE_FOLDER_PATH");
    public static final String WARNING_MESSAGE_FOLDER_PATH = INIT_PROPERTIES
            .getProperty("WARNING_MESSAGE_FOLDER_PATH");

    public static void setSystemlanguageValue(String systemlanguageValue)
            throws LangueageMessageNotFoundSystemException {
        if (isLanguageAviable(systemlanguageValue)) {
            systemLanguageValue = systemlanguageValue.toUpperCase();

            new DoneSystemMessage("PrimarySetSystemlanguageValue").show();
        } else {
            throw new LangueageMessageNotFoundSystemException();
        }

    }

    private static boolean isLanguageAviable(String systemLanguageCode) {
        return checkAFolderLanguage(DataManager.folderNameToAStringArrayList(ERROR_MESSAGE_FOLDER_PATH),
                systemLanguageCode)
                && checkAFolderLanguage(DataManager.folderNameToAStringArrayList(INFORMATION_MESSAGE_FOLDER_PATH),
                        systemLanguageCode)
                && checkAFolderLanguage(DataManager.folderNameToAStringArrayList(WARNING_MESSAGE_FOLDER_PATH),
                        systemLanguageCode)
                && checkAFolderLanguage(DataManager.folderNameToAStringArrayList(NEUTRAL_MESSAGE_FOLDER_PATH),
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

    public static String getSystemlanguageValue() {
        return systemLanguageValue.toLowerCase();
    }

    private static Properties initProperties() {
        try {
            return DataManager.readAFile("./ressources/init.conf");
        } catch (PropertiesReadingSystemException e) {
            e.show();
            return null;
        }
    }

    public static void load() throws PrimaryLoadException {
        if (isLanguageAviable(INIT_PROPERTIES.getProperty("SYSTEM_LANGUAGE"))) {
            SystemMessage.reset();
            try {
                SystemMessage.load();
            } catch (LangueageMessageNotFoundSystemException e) {
                e.show();
            }
            new DoneSystemMessage("PrimaryLoad").show();
        } else {
            throw new PrimaryLoadException();
        }
    }
}
