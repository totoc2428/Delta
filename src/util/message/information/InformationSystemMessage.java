package util.message.information;

import exception.system.util.language.LangueageMessageNotFoundSystemException;
import util.message.SystemMessage;
import util.tool.Primary;
import util.tool.terminal.TerminalStyle;

public class InformationSystemMessage extends SystemMessage {
    private static final String SAVED_CODE = "INFORMATION";

    public InformationSystemMessage(String messageCode) {
        super(SAVED_CODE, messageCode);
    }

    public void show() {
        TerminalStyle.showInformation(getMessage());
    }

    public static void loadWithCode() throws LangueageMessageNotFoundSystemException {
        load(SAVED_CODE, Primary.INFORMATION_MESSAGE_FOLDER_PATH);
    }
}
