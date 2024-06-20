package util.message.information;

import exception.system.util.language.LangueageMessageNotFoundSystemException;
import util.message.SystemMessage;
import util.tool.Primary;
import util.tool.terminal.TerminalStyle;

public class InformationSystemMessage extends SystemMessage {

    public InformationSystemMessage(String messageCode) {
        super(messageCode);
    }

    public void show() {
        TerminalStyle.showInformation(getMessage());
    }

    public static void load() throws LangueageMessageNotFoundSystemException {
        load(Primary.INFORMATION_MESSAGE_FOLDER_PATH);
    }
}
