package model.controleurs.terminal;

import java.util.ArrayList;
import java.util.HashMap;

import model.dao.terminal.CommandDataManager;
import model.dto.terminal.Command;

public class CommandControleur {
    private HashMap<String, Command> commands;

    public CommandControleur(String srcPaths) {
        CommandDataManager.initCommand();
        CommandDataManager.setCommandSrcPaths(srcPaths);
        CommandDataManager.loadCommandFromSrcPath();

        commands = CommandDataManager.getAllCommands();
    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }

    public boolean isKownedCommand(String commandName) {
        return CommandDataManager.getCommandByName(commandName) != null;
    }

    public ArrayList<String> getAllCommandDescription(String languagePreferences, String errorsMessage) {
        ArrayList<String> descriptions = new ArrayList<String>();
        for (Command c : commands.values()) {
            String showed = c.getName() + "\t|\t";
            if (isKownedCommand(c.getName())) {
                if (c.isValid(languagePreferences)) {
                    showed += c.getDescription(languagePreferences);
                } else {
                    showed += errorsMessage;
                }

                descriptions.add(showed);
            }
        }

        return descriptions;
    }

    public Command getCommandWithName(String commandName) {
        if (isKownedCommand(commandName)) {
            return commands.get(commandName);
        }

        return null;
    }
}
