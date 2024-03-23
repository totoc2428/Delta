package main.app.node.terminal;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Properties;

import main.model.controleurs.blockchain.chainobject.person.PersonControleur;
import main.model.controleurs.terminal.CommandControleur;
import main.model.controleurs.terminal.TerminalMessageControleur;
import main.model.dao.DataManager;
import main.model.dao.blockchain.BlockchainDataMaganager;
import main.model.dto.terminal.Command;
import main.util.style.TerminalStyle;
import main.util.style.TerminalColor;

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
        terminalMessageControleur.show("commandNotFoundError");
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
            terminalMessageControleur.show("logCommandWithPrivateKeyNull");
        }
    }

    private static void runLogCommandDOption() {
        command.show(languagePreferences, "dOutput_");

        PrivateKey privateKey = askForPersonCommand();
        if (privateKey != null) {
            personControleur.setIdentity(privateKey);
            terminalMessageControleur.get("logBuiltDone").show();
            personControleur.setIdentity(privateKey);
            if (personControleur.getIdentity() != null) {
                initPrefix();
                terminalMessageControleur.get("logBuiltDone").show();
            } else {
                terminalMessageControleur.get("logCommandWithPrivateKeyNull").show();
            }
        } else {
            terminalMessageControleur.get("logBuiltPrivateKeyError").show();
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
                        String passPhrase = askForPassPrase();
                        PrivateKey privateKey = personControleur.createAPersonPrivateKeyWithAtribute(
                                allCommand[2],
                                allCommand[3], allCommand[4], passPhrase);

                        String nationality = askForNationality();
                    }
                    break;
                default:
                    PrivateKey privateKey = askForPersonCommand();
                    String nationality = askForNationality();
                    break;
            }

        } else {
            String name = askForName();
            String forName = askForForNames();
            String birthDate = askForBirthDate();
            String passPhrase = askForPassPrase();
            String nationality = askForNationality();

            if (personControleur.setIdentityAsCreatedIdentity(name, forName, birthDate, passPhrase, nationality)) {
                terminalMessageControleur.get("identityCreated").show();
                terminalMessageControleur.get("logDone").show();
            } else {
                terminalMessageControleur.get("identityCreationError");
            }
            ;
        }
    }

    private static PrivateKey askForPersonCommand() {
        String name = askForName();
        String forName = askForForNames();
        String birthDate = askForBirthDate();
        String passPhrase = askForPassPrase();

        while (DataManager.passPhraseIsInCorectFormat(passPhrase)) {
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
        return DataManager.getUserInput(terminalMessageControleur.getContent("logDefaultNameInputPrefix"));
    }

    private static String askForForNames() {
        terminalMessageControleur.get("logDefaultForNameInputOrderWarning").show();
        return DataManager.getUserInput(terminalMessageControleur.getContent("logDefaultForNameInputPrefix"));
    }

    private static String askForBirthDate() {
        terminalMessageControleur.get("logDefaultBirthDateInputWarning").show();
        return DataManager.getUserInput(terminalMessageControleur.getContent("logDefaultBirthDateInputPrefix"));
    }

    private static String askForNationality() {
        return DataManager.getUserInput(terminalMessageControleur.getContent("registerDefaultNationalityInputPrefix"));
    }

    private static String askForPassPrase() {
        return DataManager.getUserSecretInput(terminalMessageControleur.getContent("logDefaultPassPhraseInputPrefix"));
    }
}