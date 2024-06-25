package util.message.neutral;

import exception.system.util.message.LangueageMessageNotFoundSystemException;
import util.message.SystemMessage;
import util.primary.Primary;
import util.tool.terminal.TerminalStyle;

public class NeutralSystemMessage extends SystemMessage {
    private static final String SAVED_CODE = "NEUTRAL";

    public NeutralSystemMessage(String messageCode, int priority) {
        super(SAVED_CODE, messageCode, priority);
    }

    public void show() {
        TerminalStyle.showNeutral(super.getMessage());
    }

    public static void loadWithCode() throws LangueageMessageNotFoundSystemException {
        load(SAVED_CODE, Primary.getNeutralMessageFolderPath());
    }

}
