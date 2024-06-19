package util.tool;

import java.util.Properties;

import exception.system.util.data.PropertieReadingSystemException;
import exception.system.util.language.LangueageMessageNotFoundSystemExcetion;
import util.data.DataManager;

public abstract class Primary {
    private static final Properties INIT_PROPERTIES = initProperties();

    private static String SystemlanguageValue = INIT_PROPERTIES.getProperty("SYSTEM_LANGUAGE");

    public static final String DATA_MANAGER_INIT_PATH = INIT_PROPERTIES.getProperty("DATA_MANAGER_INIT_PATH");

    public static final String ERROR_MESSAGE_FOLDER_PATH = INIT_PROPERTIES
            .getProperty("ERROR_MESSAGE_FOLDER_PATH");
    public static final String INFORMATION_MESSAGE_FOLDER_PATH = INIT_PROPERTIES
            .getProperty("INFORMATION_MESSAGE_FOLDER_PATH");
    public static final String NEUTRAL_MESSAGE_FOLDER_PATH = INIT_PROPERTIES
            .getProperty("NEUTRAL_MESSAGE_FOLDER_PATH");
    public static final String WARNING_MESSAGE_FOLDER_PATH = INIT_PROPERTIES
            .getProperty("WARNING_MESSAGE_FOLDER_PATH");

    public static void setSystemlanguageValue(String systemlanguageValue)
            throws LangueageMessageNotFoundSystemExcetion {
        if (isLanguageAviable(systemlanguageValue)) {
            SystemlanguageValue = systemlanguageValue.toUpperCase();
        } else {
            throw new LangueageMessageNotFoundSystemExcetion();
        }

    }

    private static boolean isLanguageAviable(String systemLanguageCode) {
        boolean aviable = false;

        return aviable;
    }

    public static String getSystemlanguageValue() {
        return SystemlanguageValue;
    }

    private static Properties initProperties() {
        try {
            return DataManager.readAFile("./ressources/init.conf");
        } catch (PropertieReadingSystemException e) {
            e.show();
            return null;
        }
    }
}
