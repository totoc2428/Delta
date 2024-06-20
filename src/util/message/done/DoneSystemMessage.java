package util.message.done;

import exception.system.util.language.LangueageMessageNotFoundSystemException;
import util.message.SystemMessage;
import util.tool.Primary;
import util.tool.terminal.TerminalStyle;

public class DoneSystemMessage extends SystemMessage {
    private static final String SAVED_CODE = "DONE";

    public DoneSystemMessage(String messageCode) {
        super(SAVED_CODE, messageCode);
    }

    public void show() {
        TerminalStyle.showDone(super.getMessage());
    }

    public static void loadWithCode() throws LangueageMessageNotFoundSystemException {
        load(SAVED_CODE, Primary.DONE_MESSAGE_FOLDER_PATH);
    }
}
