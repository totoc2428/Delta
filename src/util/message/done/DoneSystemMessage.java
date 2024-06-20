package util.message.done;

import util.message.SystemMessage;
import util.tool.terminal.TerminalStyle;

public class DoneSystemMessage extends SystemMessage {

    public DoneSystemMessage(String messageCode) {
        super(messageCode);
    }

    public void show() {
        TerminalStyle.showDone(super.getMessage());
    }
}
