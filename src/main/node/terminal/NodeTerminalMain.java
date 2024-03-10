package main.node.terminal;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Properties;

import io.jsonwebtoken.lang.Arrays;
import model.controleurs.blockchain.chainobject.person.PersonControleur;
import model.controleurs.terminal.CommandControleur;
import model.controleurs.terminal.TerminalMessageControleur;
import model.dao.DataManager;
import model.dao.blockchain.BlockchainDataMaganager;
import model.dto.terminal.Command;
import util.style.TerminalColor;
import util.style.TerminalStyle;

public abstract class NodeTerminalMain {
    private static final Properties TERMINALMAIN_PROPERTIES = DataManager
            .read(DataManager.INIT_PROPERTIES.getProperty("TERMINALMAIN_PROPERTIES"));

    private static String languagePreferences;

    private static boolean exit;
    private static String prefix;

    private static String[] allCommand;
    private static String commandName;
    private static Command command;

    private static CommandControleur commandControleur;
    private static TerminalMessageControleur terminalMessageControleur;
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
            allCommand = new String[] {};
            commandName = "";

            initLanguagesPreferences();
            initControleur();
            initPrefix();
        } else {
            System.exit(1);
        }
    }

    private static void initLanguagesPreferences() {
        String languagePreferencesCheck = DataManager.INIT_PROPERTIES.getProperty("LANGUAGE_PREFERENCES");
        if (languagePreferencesCheck != null) {
            if (!languagePreferencesCheck.isEmpty() && !languagePreferencesCheck.isBlank()) {
                languagePreferences = languagePreferencesCheck;
            }
        } else {
            languagePreferences = "fr";
        }
    }

    private static void initControleur() {
        personControleur = new PersonControleur();
        commandControleur = new CommandControleur(TERMINALMAIN_PROPERTIES.getProperty("commands"));
        terminalMessageControleur = new TerminalMessageControleur(languagePreferences);

    }

    private static void initPrefix() {
        System.setProperty("file.encoding", "UTF-8");
        prefix = terminalMessageControleur.getContent("notConnectedPrefix");
        if (personControleur.getIdentity() != null) {
            prefix = personControleur.getIdentity().getLastName();
        }
        try {
            prefix += "@" + InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            prefix += "@" + terminalMessageControleur.getContent("unknownHostPrefix");
        }
        prefix += "ΔNODE_DEVICE:$ ";
    }

    // start
    private static void start() {
        showLogo();
        terminalMessageControleur.show("start");
        terminalMessageControleur.show("welcome");
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
            terminalMessageControleur.show("notConnectedWarning");
            terminalMessageControleur.show("toConnectInformation");
            terminalMessageControleur.show("toRegisterInformation");
        }
        allCommand = askCommand().split(" ");
        commandName = allCommand[0];
        command = commandControleur.getCommandWithName(commandName);
        runCommand();

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
                break;
            case "log":
                runLogCommand();
                break;
            case "register":
                runRegisterCommand();
                break;
            default:
                runCommandNotFound();
                break;
        }
    }

    // not found
    private static void runCommandNotFound() {
        terminalMessageControleur.show("CommandNotFoundError");
    }

    // exit
    private static void runExitCommand() {
        command.show(languagePreferences);
        exit = true;
    }

    // help
    private static void runHelpCommand() {
        command.show(languagePreferences);
        ArrayList<String> commands = commandControleur.getAllCommandDescription(languagePreferences,
                terminalMessageControleur.getContent("invalidCommandConfiguration"));

        for (String command : commands) {
            TerminalStyle.showNeutral(command);
        }
    }

    // log
    private static void runLogCommand() {
        command.show(languagePreferences);
        if (allCommand.length > 1) {
            String options = allCommand[1];
            switch (options) {
                case "-p":
                    runLogCommandPoption();
                    break;
                default:
                    runLogCommandDOption();
                    break;
            }
        } else {
            runLogCommandDOption();
        }

    }

    private static void runLogCommandPoption() {
        command.show(languagePreferences, "pOutput_");
        PrivateKey privateKey = BlockchainDataMaganager.stringToPrivateKey(allCommand[2]);
        if (privateKey != null) {
            personControleur.setIdentity(privateKey);
        } else {
            terminalMessageControleur.show("LogCommandWithPrivateKeyNull");
        }
    }

    private static void runLogCommandDOption() {
        command.show(languagePreferences, "dOutput_");

        PrivateKey privateKey = askForPersonCommand();
        if (privateKey != null) {
            personControleur.setIdentity(privateKey);
            terminalMessageControleur.get("LogBuiltDone").show();
            personControleur.setIdentity(privateKey);
            if (personControleur.getIdentity() != null) {
                initPrefix();
                terminalMessageControleur.get("LogBuiltDone").show();
            } else {
                terminalMessageControleur.get("LogCommandWithPrivateKeyNull").show();
            }
        } else {
            terminalMessageControleur.get("LogBuiltPrivateKeyError").show();
        }
    }

    // register
    private static void runRegisterCommand() {
        command.show(languagePreferences);
        terminalMessageControleur.show("registerTrueDataWarning");
        if (allCommand.length > 1) {
            String option = allCommand[1];
            switch (option) {
                case "-l":
                    if (allCommand.length > 3) {
                        String passPhrase = DataManager.getUserSecretInput(
                                terminalMessageControleur.getContent("LogDefaultPassPhraseInputPrefix"));
                        PrivateKey privateKey = personControleur.createAPersonPrivateKeyWithAtribute(
                                allCommand[2],
                                allCommand[3], allCommand[4], passPhrase);
                    }
                    break;
                default:
                    PrivateKey privateKey = askForPersonCommand();

                    break;
            }

        }
    }

    private static PrivateKey askForPersonCommand() {
        String name = askForName();
        String forName = askForForNames();
        String birthDate = askForBirthDate();
        String passPhrase = askForPassPrase();

        while (personControleur.passPhraseIsInCorectFormat(passPhrase)) {
            terminalMessageControleur.show("passPhraseIncorectFormatError");
            passPhrase = askForPassPrase();
        }

        return personControleur.createAPersonPrivateKeyWithAtribute(name, forName, birthDate, passPhrase);

    }

    // util
    // LOGO
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

    // ask

    private static String askForName() {
        return DataManager.getUserInput(terminalMessageControleur.getContent("LogDefaultNameInputPrefix"));
    }

    private static String askForForNames() {
        terminalMessageControleur.get("LogDefaultForNameInputOrderWarning");
        return DataManager.getUserInput(terminalMessageControleur.getContent("LogDefaultForNameInputPrefix"));
    }

    private static String askForBirthDate() {
        terminalMessageControleur.get("LogDefaultBirthDateInputWarning");
        return DataManager.getUserInput(terminalMessageControleur.getContent("LogDefaultBirthDateInputPrefix"));
    }

    private static String askForPassPrase() {
        return DataManager.getUserSecretInput(terminalMessageControleur.getContent("LogDefaultPassPhraseInputPrefix"));
    }
}
