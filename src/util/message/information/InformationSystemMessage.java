package util.message.information;

import util.message.SystemMessage;
import util.tool.terminal.TerminalStyle;

public class InformationSystemMessage extends SystemMessage {

    public InformationSystemMessage(String messageCode) {
        super(messageCode);
    }

    public void show() {
        TerminalStyle.showInformation(getMessage());
    }
}
