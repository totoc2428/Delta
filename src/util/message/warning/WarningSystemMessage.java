package util.message.warning;

import exception.system.util.language.LangueageMessageNotFoundSystemException;
import util.message.SystemMessage;
import util.tool.Primary;
import util.tool.terminal.TerminalStyle;

public class WarningSystemMessage extends SystemMessage {

    public WarningSystemMessage(String messageCode) {
        super(messageCode);
    }

    public void show() {
        TerminalStyle.showWarning(super.getMessage());
    }

    public static void load() throws LangueageMessageNotFoundSystemException {
        load(Primary.WARNING_MESSAGE_FOLDER_PATH);
    }

}
