package util.message.done;

import exception.system.util.language.LangueageMessageNotFoundSystemException;
import util.message.SystemMessage;
import util.tool.Primary;
import util.tool.terminal.TerminalStyle;

public class DoneSystemMessage extends SystemMessage {

    public DoneSystemMessage(String messageCode) {
        super(messageCode);
    }

    public void show() {
        TerminalStyle.showDone(super.getMessage());
    }

    public static void load() throws LangueageMessageNotFoundSystemException {
        load(Primary.WARNING_MESSAGE_FOLDER_PATH);
    }
}
