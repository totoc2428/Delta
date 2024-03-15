package model.dao.terminal;

import java.io.File;
import java.util.HashMap;
import java.util.Properties;

import model.dao.DataManager;
import model.dto.terminal.TerminalMessage;
import util.style.TerminalStyle;

public abstract class TerminalMessageDataManager extends DataManager {
    public static final Properties TERMINALMESSAGE_PROPERTIES = DataManager
            .read(INIT_PROPERTIES.getProperty("TERMINALMESSAGE_PROPERTIES"));

    private static String srcPaths;
    private static String errorsNameFile;
    private static String informationsNameFile;
    private static String warningsNameFile;
    private static String neutralsNameFile;
    private static String donesNameFile;
    private static String languagePreferences;

    public static HashMap<String, TerminalMessage> getAllErrorMessages() {
        return getAll(TerminalStyle.ERROR, errorsNameFile);
    }

    public static HashMap<String, TerminalMessage> getAllInformationMessages() {
        return getAll(TerminalStyle.INFORMATION, informationsNameFile);
    }

    public static HashMap<String, TerminalMessage> getAllWarningsMessages() {
        return getAll(TerminalStyle.WARNING, warningsNameFile);
    }

    public static HashMap<String, TerminalMessage> getAllDonesMessages() {
        return getAll(TerminalStyle.DONE, donesNameFile);
    }

    public static HashMap<String, TerminalMessage> getAllNeutralsMessages() {
        return getAll(TerminalStyle.RESET, neutralsNameFile);
    }

    private static HashMap<String, TerminalMessage> getAll(TerminalStyle style, String name) {
        Properties properties = DataManager
                .read(srcPaths + File.separator + languagePreferences + File.separator + name);
        HashMap<String, TerminalMessage> dic = new HashMap<String, TerminalMessage>();

        for (Object key : properties.keySet()) {
            if (key instanceof String) {
                String strKey = (String) key;
                TerminalMessage terminalMessage = new TerminalMessage((String) properties.get(strKey), style);
                dic.put(strKey, terminalMessage);
            }
        }
        return dic;
    }

    public static void setSrcPath(String srcPaths) {
        TerminalMessageDataManager.srcPaths = srcPaths;
    }

    public static void setSrcPaths(String srcPaths) {
        TerminalMessageDataManager.srcPaths = srcPaths;
    }

    public static void setErrorsNameFile(String errorsNameFile) {
        TerminalMessageDataManager.errorsNameFile = errorsNameFile;
    }

    public static void setInformationsNameFile(String informationsNameFile) {
        TerminalMessageDataManager.informationsNameFile = informationsNameFile;
    }

    public static void setWarningsNameFile(String warningsNameFile) {
        TerminalMessageDataManager.warningsNameFile = warningsNameFile;
    }

    public static void setNeutralsNameFile(String neutralsNameFile) {
        TerminalMessageDataManager.neutralsNameFile = neutralsNameFile;
    }

    public static void setDonesNameFile(String donesNameFile) {
        TerminalMessageDataManager.donesNameFile = donesNameFile;
    }

    public static void setLanguagePreferences(String languagePreferences) {
        TerminalMessageDataManager.languagePreferences = languagePreferences;
    }

}
