package exception.system;

import util.message.error.ErrorSystemMessage;
import util.message.warning.WarningSystemMessage;

public class SystemException extends Exception {

    private String systemExceptionCode;
    private ErrorSystemMessage errorSystemMessage;
    private WarningSystemMessage warningSystemMessage;

    public SystemException(String systemExceptionCode) {
        super();
        this.systemExceptionCode = systemExceptionCode;
        errorSystemMessage = new ErrorSystemMessage(systemExceptionCode);
    }

    protected void addWarning() {
        warningSystemMessage = new WarningSystemMessage(systemExceptionCode);
    }

    public void show() {
        errorSystemMessage.show();
        if (warningSystemMessage != null) {
            warningSystemMessage.show();
        }
    }
}
