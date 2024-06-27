package util.primary;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;

import exception.system.SystemException;
import exception.system.util.data.PropertiesReadingSystemException;
import exception.system.util.message.LangueageMessageNotFoundSystemException;
import exception.system.util.primary.InvalidMessagePrioritySystemException;
import exception.system.util.primary.PrimaryLoadException;
import exception.system.util.primary.PrimarySetInitFilePathSystemException;
import util.blockchain.BlockchainManager;
import util.data.DataManager;
import util.message.SystemMessage;
import util.message.done.DoneSystemMessage;
import util.security.SecurityManager;

public abstract class Primary {
    private static Properties initPrimaryProperties;

    private static String systemLanguageValue;

    private static String dataManagerInitPath;
    private static String blockchainManagerInitPath;
    private static String securityManagerInitPath;
    private static String doneMessageInitPath;
    private static String errorMessageFolderPath;
    private static String informationMessageFolderPath;
    private static String neutralMessageFolderPath;
    private static String warningMessageFolderPath;

    private static int messagePriority;
    private static double version;

    /* -LOADER */
    public static void load() throws PrimaryLoadException {
        try {
            if (initPrimaryProperties == null) {
                setInitFilePath("./ressources/init.conf");
            }
            if (initPrimaryPropertiesCheck()) {

                systemLanguageValue = initPrimaryProperties.getProperty("SYSTEM_LANGUAGE");
                dataManagerInitPath = initPrimaryProperties.getProperty("DATA_MANAGER_INIT_PATH");
                blockchainManagerInitPath = initPrimaryProperties.getProperty("BLOCKCHAIN_MANAGER_INIT_PATH");
                securityManagerInitPath = initPrimaryProperties.getProperty("SECURITY_MANAGER_INIT_PATH");
                doneMessageInitPath = initPrimaryProperties.getProperty("DONE_MESSAGE_FOLDER_PATH");
                errorMessageFolderPath = initPrimaryProperties.getProperty("ERROR_MESSAGE_FOLDER_PATH");
                informationMessageFolderPath = initPrimaryProperties.getProperty("INFORMATION_MESSAGE_FOLDER_PATH");
                neutralMessageFolderPath = initPrimaryProperties.getProperty("NEUTRAL_MESSAGE_FOLDER_PATH");
                warningMessageFolderPath = initPrimaryProperties.getProperty("WARNING_MESSAGE_FOLDER_PATH");

                messagePriority = Integer.parseInt(initPrimaryProperties.getProperty("MESSAGE_PRIORITY"));
                version = Double.parseDouble(initPrimaryProperties.getProperty("VERSION"));

                if (isLanguageAviable(initPrimaryProperties.getProperty("SYSTEM_LANGUAGE"))) {
                    SystemMessage.reset();
                    SystemMessage.load();
                    DataManager.load();
                    BlockchainManager.load();
                    SecurityManager.load();

                    DoneSystemMessage.show("PrimaryLoad", 1);
                } else {
                    throw new PrimaryLoadException();
                }
            } else {
                throw new PrimaryLoadException();
            }
        } catch (SystemException e) {
            e.show();
            throw new PrimaryLoadException();
        }

    }

    /* -INIT */
    /* --SETTER */
    public static void setInitFilePath(String filePath) throws PrimarySetInitFilePathSystemException {
        try {
            initPrimaryProperties = DataManager.readAFile(filePath);
        } catch (PropertiesReadingSystemException e) {
            e.show();
            throw new PrimarySetInitFilePathSystemException();
        }
    }

    /* --CHEKER */
    private static boolean initPrimaryPropertiesCheck() {
        return checkAPrimaryProperty("SYSTEM_LANGUAGE") && checkAPrimaryProperty("DATA_MANAGER_INIT_PATH")
                && checkAPrimaryProperty("BLOCKCHAIN_MANAGER_INIT_PATH")
                && checkAPrimaryProperty("SECURITY_MANAGER_INIT_PATH")
                && checkAPrimaryProperty("DONE_MESSAGE_FOLDER_PATH")
                && checkAPrimaryProperty("ERROR_MESSAGE_FOLDER_PATH")
                && checkAPrimaryProperty("INFORMATION_MESSAGE_FOLDER_PATH")
                && checkAPrimaryProperty("NEUTRAL_MESSAGE_FOLDER_PATH")
                && checkAPrimaryProperty("WARNING_MESSAGE_FOLDER_PATH")
                && checkAPrimaryProperty("MESSAGE_PRIORITY")
                && checkAPrimaryProperty("VERSION");
    }

    private static boolean checkAPrimaryProperty(String primaryCode) {
        return (initPrimaryProperties.getProperty(primaryCode) != null && (!initPrimaryProperties
                .getProperty(
                        primaryCode)
                .isBlank() && !initPrimaryProperties.getProperty(primaryCode).isEmpty()));
    }

    /* -VERSION */
    /* --GETTER */
    public static double getVersion() {
        return version;
    }

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

    /* ---SECURITY */
    public static String getSecurityManagerInitPath() {
        return securityManagerInitPath;
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

                DoneSystemMessage.show("PrimarySetDoneMessageInitPath", 1);
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

                DoneSystemMessage.show("PrimarySetErrorMessageInitPath", 1);
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

                DoneSystemMessage.show("PrimarySetInformationMessageInitPath", 1);
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

                DoneSystemMessage.show("PrimarySetNeutralMessageInitPath", 1);
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

                DoneSystemMessage.show("PrimarySetWarningMessageInitPath", 1);
            } else {
                throw new LangueageMessageNotFoundSystemException();
            }
        } else {
            throw new PropertiesReadingSystemException();
        }
    }

    /* -MESSAGE */
    /* --GETTER */
    public static int getMessagePriority() {
        return messagePriority;
    }

    /* --SETTER */
    public static void setMessagePriority(int messagePriority) throws InvalidMessagePrioritySystemException {
        if (messagePriority > 0) {
            Primary.messagePriority = messagePriority;
            DoneSystemMessage.show("PrimarySetMessagePriority", 1);
        } else {
            throw new InvalidMessagePrioritySystemException();
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

            DoneSystemMessage.show("PrimarySetSystemlanguageValue", 1);
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

}
