package main.node.terminal;

import java.net.InetAddress;
import java.net.UnknownHostException;
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

    private static Properties comandsProperties;
    private static Properties commandsList;

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
        comandsProperties = DataManager.read(NODETERMINALMAIN_PROPERTIES.getProperty("commands"));
        commandsList = DataManager.read(comandsProperties.getProperty("commandsList"));
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
            runCommand();
        }
        System.exit(0);
    }

    private static void runCommand() {
        if (controleur.getIdentity() == null) {
            TerminalStyle.showWarning(warningsMessage.getProperty("notConnected"));
            TerminalStyle.showInformation(informationsMessage.getProperty("toConnect"));
            TerminalStyle.showInformation(informationsMessage.getProperty("toRegister"));
        }
        String command = askCommand();
        System.out.println(command);

    }

    private static String askCommand() {
        return DataManager.getUserInput(prefix);
    }
}
