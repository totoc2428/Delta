package util.message.error;

import exception.system.util.message.LangueageMessageNotFoundSystemException;
import util.message.SystemMessage;
import util.primary.Primary;
import util.tool.terminal.TerminalStyle;

public class ErrorSystemMessage extends SystemMessage {
    private static final String SAVED_CODE = "ERROR";

    public ErrorSystemMessage(String messageCode, int priority) {
        super(SAVED_CODE, messageCode, priority);
    }

    public void show() {
        TerminalStyle.showError(super.getMessage());
    }

    public static void loadWithCode() throws LangueageMessageNotFoundSystemException {
        load(SAVED_CODE, Primary.getErrorMessageFolderPath());
    }

}
