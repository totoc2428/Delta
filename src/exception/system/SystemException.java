package exception.system;

import exception.message.ExceptionMessage;
import util.tool.terminal.TerminalStyle;

public class SystemException extends Exception {

    public SystemException(String systemExceptionCode) {
        super(ExceptionMessage.getAnExceptionMessage(systemExceptionCode));
    }

    public void show() {
        TerminalStyle.showError(this.getMessage());
    }
}
