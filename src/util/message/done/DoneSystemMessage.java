package util.message.done;

import exception.system.util.message.LangueageMessageNotFoundSystemException;
import util.message.SystemMessage;
import util.primary.Primary;
import util.tool.terminal.TerminalStyle;

public class DoneSystemMessage extends SystemMessage {
    private static final String SAVED_CODE = "DONE";

    public DoneSystemMessage(String messageCode, int priority) {
        super(SAVED_CODE, messageCode, priority);
    }

    public void show() {
        TerminalStyle.showDone(super.getMessage());
    }

    public static void loadWithCode() throws LangueageMessageNotFoundSystemException {
        load(SAVED_CODE, Primary.getDoneMessageInitPath());
    }
}
