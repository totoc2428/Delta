package model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import model.controleurs.node.terminal.style.TerminalStyle;
import model.dto.terminal.Command;

public abstract class CommandDataManager extends DataManager {
    private static final Properties COMMAND_PROPERTIES = DataManager
            .read(INIT_PROPERTIES.getProperty("COMMAND_PROPERTIES"));

    private static String commandSrcPath;
    private static HashMap<String, Command> commands;

    public static void initCommand() {
        commands = new HashMap<String, Command>();
    }

    public static void loadCommandFromSrcPath() {
        if (isCommandSrcPathValid(commandSrcPath)) {
            final String COMMAND_FILE_SAVED_TAG = COMMAND_PROPERTIES.getProperty("COMMAND_FILE_SAVED_TAG");

            ArrayList<String> aviableCommand = DataManager.getAllFileNames(commandSrcPath);

            for (String str : aviableCommand) {
                String strReplaced = str.replace(COMMAND_FILE_SAVED_TAG, "");
                commands.put(strReplaced, new Command(DataManager.read(commandSrcPath + str)));
            }
        }
    }

    public static HashMap<String, Command> getAllCommands() {
        return commands;
    }

    public static Command getCommandByName(String commandName) {
        return getAllCommands().get(commandName);

    }

    public static boolean isKownedCommand(String commandName) {
        return getCommandByName(commandName) != null;
    }

    public static String getCommandSrcPaths() {
        return commandSrcPath;
    }

    public static void setCommandSrcPaths(String commandSrcPathParamter) {
        if (isCommandSrcPathValid(commandSrcPathParamter)) {
            CommandDataManager.commandSrcPath = commandSrcPathParamter;
        }
    }

    private static boolean isCommandSrcPathValid(String srcPath) {
        if (srcPath != null) {
            if (DataManager.fileExist(srcPath)) {
                if (DataManager.fileIsDirectory(srcPath)) {
                    return true;
                } else {
                    TerminalStyle.showError("fileNotDirectory");
                }
            } else {
                TerminalStyle.showError("fileNotExist");
            }

        } else {
            TerminalStyle.showError("isNull");
        }

        return false;
    }
}
