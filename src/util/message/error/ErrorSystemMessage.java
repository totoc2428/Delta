package util.message.error;

import exception.system.util.language.LangueageMessageNotFoundSystemException;
import util.message.SystemMessage;
import util.tool.Primary;
import util.tool.terminal.TerminalStyle;

public class ErrorSystemMessage extends SystemMessage {
    private static final String SAVED_CODE = "ERROR";

    public ErrorSystemMessage(String messageCode) {
        super(SAVED_CODE, messageCode);
    }

    public void show() {
        TerminalStyle.showNeutral(super.getMessage());
    }

    public static void loadWithCode() throws LangueageMessageNotFoundSystemException {
        load(SAVED_CODE, Primary.NEUTRAL_MESSAGE_FOLDER_PATH);
    }

}
