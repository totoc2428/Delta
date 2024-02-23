package main.node.terminal;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Properties;

import model.controleurs.node.terminal.TerminalControleur;
import model.controleurs.node.terminal.style.TerminalStyle;
import model.dao.DataManager;

public abstract class NodeTerminalMain {
    private static final Properties NODETERMINALMAIN_PROPERTIES = DataManager
            .read(DataManager.INIT_PROPERTIES.getProperty("NODETERMINALMAIN_PROPERTIES"));

    private static String languagePreferences;
    private static String languageSrcPath;

    private static Properties neutralMessage;
    private static Properties informationsMessage;
    private static Properties warningsMessage;
    private static Properties errorsMessage;

    private static TerminalControleur controleur;
    private static boolean exit;
    private static String prefix;

    private static Properties commandsList;

    private static String allCommand;
    private static String commandName;
    private static Properties command;

    public static void main(String[] args) {
        init();
        start();
        run();
    }

    // check
    private static boolean checkRequirement() {
        return true;// TODO
    }

    // init
    private static void init() {
        if (checkRequirement()) {
            exit = false;
            initMessage();
            initControleur();
            initCommands();
            initPrefix();
        } else {
            System.exit(1);
        }
    }

    private static void initMessage() {
        languagePreferences = DataManager.INIT_PROPERTIES.getProperty("LANGUAGE_PREFERENCES");
        languageSrcPath = NODETERMINALMAIN_PROPERTIES.getProperty("languages");

        neutralMessage = DataManager.read(
                languageSrcPath + languagePreferences + NODETERMINALMAIN_PROPERTIES.getProperty("neutralMessage"));

        informationsMessage = DataManager.read(
                languageSrcPath + languagePreferences + NODETERMINALMAIN_PROPERTIES.getProperty("informationsMessage"));
        warningsMessage = DataManager.read(
                languageSrcPath + languagePreferences + NODETERMINALMAIN_PROPERTIES.getProperty("warningsMessage"));
        errorsMessage = DataManager.read(
                languageSrcPath + languagePreferences + NODETERMINALMAIN_PROPERTIES.getProperty("errorsMessage"));
    }

    private static void initControleur() {
        controleur = new TerminalControleur();
    }

    private static void initCommands() {
        final String COMMAND_SRC_PATH = NODETERMINALMAIN_PROPERTIES.getProperty("commands");
        final String COMMAND_FILE_SAVED_TAG = NODETERMINALMAIN_PROPERTIES.getProperty("COMMAND_FILE_SAVED_TAG");

        commandsList = new Properties();

        ArrayList<String> aviableCommand = DataManager.getAllFileNames(COMMAND_SRC_PATH);
        for (String str : aviableCommand) {
            String strReplaced = str.replace(COMMAND_FILE_SAVED_TAG, "");
            commandsList.setProperty(strReplaced, COMMAND_SRC_PATH + str);
        }

        allCommand = "";
        commandName = "";

    }

    private static void initPrefix() {
        System.setProperty("file.encoding", "UTF-8");
        prefix = neutralMessage.getProperty("notConnectedPrefix");
        if (controleur.getIdentity() != null) {
            prefix = controleur.getIdentity().getName();
        }
        try {
            prefix += "@" + InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            prefix += "@" + neutralMessage.getProperty("unknownHostPrefix");
        }
        prefix += "ΔNODE_DEVICE:$ ";
    }

    // start
    private static void start() {
        TerminalStyle.showNeutral(neutralMessage.getProperty("start"));
        TerminalStyle.showNeutral(neutralMessage.getProperty("welcome"));

    }

    // run
    private static void run() {
        while (!exit) {
            runAskAndCommand();
        }
        System.exit(0);
    }

    private static void runAskAndCommand() {

        if (controleur.getIdentity() == null) {
            TerminalStyle.showWarning(warningsMessage.getProperty("notConnected"));
            TerminalStyle.showInformation(informationsMessage.getProperty("toConnect"));
            TerminalStyle.showInformation(informationsMessage.getProperty("toRegister"));
        }
        allCommand = askCommand();
        commandName = allCommand.split(" ")[0];
        if (isKownedCommand()) {
            command = loadCommand(commandName);
            if (isValidCommand(command)) {
                runCommand();
            } else {
                TerminalStyle.showError(errorsMessage.getProperty("invalidCommandConfiguration"));
            }
        } else {
            TerminalStyle.showError(errorsMessage.getProperty("invalidCommandName"));
        }

    }

    private static String askCommand() {
        return DataManager.getUserInput(prefix);
    }

    private static Properties loadCommand(String commandName) {
        return DataManager.read(commandsList.getProperty(commandName));
    }

    private static boolean isKownedCommand() {
        return isKownedCommand(commandName);
    }

    private static boolean isKownedCommand(String paraCommand) {
        final String COMMAND_PATH = commandsList.getProperty(paraCommand);
        boolean kowned = false;

        if (COMMAND_PATH != null) {
            if (!COMMAND_PATH.isBlank() && !COMMAND_PATH.isEmpty()) {
                kowned = DataManager.fileExist(commandsList.getProperty(paraCommand));
            }
        }

        return kowned;
    }

    private static boolean isValidCommand(Properties paraCommand) {
        boolean isValid = false;

        isValid = !paraCommand.getProperty("name").toString().isBlank()
                && !paraCommand.getProperty("name").toString().isEmpty();

        isValid = isValid && !paraCommand.getProperty("description_" + languagePreferences).toString().isBlank()
                && !paraCommand.getProperty("description_" + languagePreferences).toString().isEmpty();

        isValid = isValid && !paraCommand.getProperty("mainOutput_" + languagePreferences).toString().isBlank()
                && !paraCommand.getProperty("mainOutput_" + languagePreferences).toString().isEmpty();

        return isValid;
    }

    private static void runCommand() {
        switch (commandName) {
            case "exit":
                runExitCommand();
                break;
            case "help":
                runHelpCommand();
            default:
                break;
        }
    }

    private static void runExitCommand() {
        command = DataManager.read(commandsList.getProperty(commandName));
        TerminalStyle.showNeutral(command.getProperty("mainOutput_" + languagePreferences));
        exit = true;
    }

    private static void runHelpCommand() {
        command = DataManager.read(commandsList.getProperty(commandName));
        TerminalStyle.showNeutral(command.getProperty("mainOutput_" + languagePreferences));
        for (Object cmObj : commandsList.keySet()) {
            if (cmObj instanceof String) {
                String cmStr = (String) cmObj;
                String showed = cmStr + " | ";
                if (isKownedCommand(cmStr)) {
                    Properties cm = loadCommand(cmStr);
                    if (isValidCommand(cm)) {
                        showed += cm.getProperty("description_" + languagePreferences);
                    } else {
                        showed += errorsMessage.getProperty("invalidCommandConfiguration");
                    }
                }

                TerminalStyle.showNeutral("final result " + showed);

            }

        }
    }
}
