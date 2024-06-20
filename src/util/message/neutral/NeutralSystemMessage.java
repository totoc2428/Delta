package util.message.neutral;

import exception.system.util.language.LangueageMessageNotFoundSystemException;
import util.message.SystemMessage;
import util.tool.Primary;
import util.tool.terminal.TerminalStyle;

public class NeutralSystemMessage extends SystemMessage {

    public NeutralSystemMessage(String messageCode) {
        super(messageCode);
    }

    public void show() {
        TerminalStyle.showNeutral(super.getMessage());
    }

    public static void load() throws LangueageMessageNotFoundSystemException {
        load(Primary.NEUTRAL_MESSAGE_FOLDER_PATH);
    }

}
