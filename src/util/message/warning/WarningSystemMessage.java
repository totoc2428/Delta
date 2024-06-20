package util.message.warning;

import exception.system.util.language.LangueageMessageNotFoundSystemException;
import util.message.SystemMessage;
import util.tool.Primary;
import util.tool.terminal.TerminalStyle;

public class WarningSystemMessage extends SystemMessage {

    private static final String SAVED_CODE = "WARNING";

    public WarningSystemMessage(String messageCode) {
        super(SAVED_CODE, messageCode);
    }

    public void show() {
        TerminalStyle.showWarning(super.getMessage());
    }

    public static void loadWithCode() throws LangueageMessageNotFoundSystemException {
        load(SAVED_CODE, Primary.WARNING_MESSAGE_FOLDER_PATH);
    }

}
