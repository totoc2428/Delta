package main.node.terminal;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import model.controleurs.node.terminal.TerminalControleur;
import model.controleurs.node.terminal.style.TerminalStyle;
import model.dao.CommandDataManager;
import model.dao.DataManager;
import model.dto.terminal.Command;

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

    private static String allCommand;
    private static String commandName;
    private static Command command;

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

        CommandDataManager.initCommand();
        CommandDataManager.setCommandSrcPaths(COMMAND_SRC_PATH);
        CommandDataManager.loadCommandFromSrcPath();

        System.out.println(CommandDataManager.getAllCommands());

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
        if (CommandDataManager.isKownedCommand(commandName)) {
            command = CommandDataManager.getCommandByName(commandName);
            if (command.isValid(languagePreferences)) {
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
        command = CommandDataManager.getCommandByName(commandName);
        TerminalStyle.showNeutral(command.getMainOutput(languagePreferences));
        exit = true;
    }

    private static void runHelpCommand() {
        command = CommandDataManager.getCommandByName(commandName);
        TerminalStyle.showNeutral(command.getMainOutput(languagePreferences));
        for (Command c : CommandDataManager.getAllCommands().values()) {
            String showed = c.getName() + " | ";
            if (CommandDataManager.isKownedCommand(c.getName())) {
                if (c.isValid(languagePreferences)) {
                    showed += c.getDescription(languagePreferences);
                } else {
                    showed += errorsMessage.getProperty("invalidCommandConfiguration");
                }

                TerminalStyle.showNeutral(showed);
            }

        }
    }
}
