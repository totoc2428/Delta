package util.message.warning;

import util.message.SystemMessage;
import util.tool.terminal.TerminalStyle;

public class WarningSystemMessage extends SystemMessage {

    public WarningSystemMessage(String messageCode) {
        super(messageCode);
    }

    public void show() {
        TerminalStyle.showWarning(super.getMessage());
    }

}
