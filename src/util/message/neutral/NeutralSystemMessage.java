package util.message.neutral;

import util.message.SystemMessage;
import util.tool.terminal.TerminalStyle;

public class NeutralSystemMessage extends SystemMessage {

    public NeutralSystemMessage(String messageCode) {
        super(messageCode);
    }

    public void show() {
        TerminalStyle.showNeutral(super.getMessage());
    }

}
