package main.node.terminal;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Properties;

import model.controleurs.blockchain.chainobject.person.PersonControleur;
import model.controleurs.terminal.CommandControleur;
import model.dao.CommandDataManager;
import model.dao.DataManager;
import model.dto.terminal.Command;
import util.style.TerminalColor;
import util.style.TerminalStyle;

public abstract class NodeTerminalMain {
    private static final Properties TERMINALMAIN_PROPERTIES = DataManager
            .read(DataManager.INIT_PROPERTIES.getProperty("TERMINALMAIN_PROPERTIES"));

    private static String languagePreferences;
    private static String languageSrcPath;

    private static Properties neutralMessage;
    private static Properties informationsMessage;
    private static Properties warningsMessage;
    private static Properties errorsMessage;

    private static boolean exit;
    private static String prefix;

    private static String allCommand = "";
    private static String commandName = "";
    private static Command command;

    private static CommandControleur commandControleur;
    private static PersonControleur personControleur;

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
            initPrefix();
        } else {
            System.exit(1);
        }
    }

    private static void initMessage() {
        languagePreferences = DataManager.INIT_PROPERTIES.getProperty("LANGUAGE_PREFERENCES");
        languageSrcPath = TERMINALMAIN_PROPERTIES.getProperty("languages");

        neutralMessage = DataManager.read(
                languageSrcPath + languagePreferences + TERMINALMAIN_PROPERTIES.getProperty("neutralMessage"));

        informationsMessage = DataManager.read(
                languageSrcPath + languagePreferences + TERMINALMAIN_PROPERTIES.getProperty("informationsMessage"));
        warningsMessage = DataManager.read(
                languageSrcPath + languagePreferences + TERMINALMAIN_PROPERTIES.getProperty("warningsMessage"));
        errorsMessage = DataManager.read(
                languageSrcPath + languagePreferences + TERMINALMAIN_PROPERTIES.getProperty("errorsMessage"));
    }

    private static void initControleur() {
        personControleur = new PersonControleur();
        commandControleur = new CommandControleur(TERMINALMAIN_PROPERTIES.getProperty("commands"));

    }

    private static void initPrefix() {
        System.setProperty("file.encoding", "UTF-8");
        prefix = neutralMessage.getProperty("notConnectedPrefix");
        if (personControleur.getIdentity() != null) {
            prefix = personControleur.getIdentity().getLastName();
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
        showLogo();
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

        if (personControleur.getIdentity() == null) {
            TerminalStyle.showWarning(warningsMessage.getProperty("notConnected"));
            TerminalStyle.showInformation(informationsMessage.getProperty("toConnect"));
            TerminalStyle.showInformation(informationsMessage.getProperty("toRegister"));
        }
        allCommand = askCommand();
        commandName = allCommand.split(" ")[0];
        if (commandControleur.isKownedCommand(commandName)) {
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
        ArrayList<String> commands = commandControleur.getAllCommandDescription(languagePreferences,
                errorsMessage.getProperty("invalidCommandConfiguration"));

        for (String command : commands) {
            TerminalStyle.showNeutral(command);
        }
    }

    // show
    private static void showLogo() {
        ArrayList<String> logo = DataManager.textFileToStringArrayList(
                TERMINALMAIN_PROPERTIES.getProperty("textimage") + TERMINALMAIN_PROPERTIES.getProperty("logo"));

        for (String line : logo) {
            for (int i = 0; i < line.length(); i++) {
                switch (line.charAt(i)) {
                    case '&':
                        System.out.print(TerminalColor.GREEN.getTerminalCode() + line.charAt(i)
                                + TerminalColor.RESET.getTerminalCode());
                        break;
                    case '#':
                        System.out.print(TerminalColor.BLUE.getTerminalCode() + line.charAt(i)
                                + TerminalColor.RESET.getTerminalCode());
                        break;
                    case '/':
                        System.out.print(TerminalColor.PURPLE.getTerminalCode() + line.charAt(i)
                                + TerminalColor.RESET.getTerminalCode());
                        break;
                    default:
                        System.out.print(line.charAt(i));
                        break;
                }
            }
            System.out.print("\n");
        }
    }
}
