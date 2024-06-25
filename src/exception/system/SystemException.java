package exception.system;

import util.message.error.ErrorSystemMessage;
import util.message.warning.WarningSystemMessage;
import util.primary.Primary;

public class SystemException extends Exception {

    private String systemExceptionCode;
    private ErrorSystemMessage errorSystemMessage;
    private WarningSystemMessage warningSystemMessage;

    public SystemException(String systemExceptionCode, int priority) {
        super();
        this.systemExceptionCode = systemExceptionCode;
        errorSystemMessage = new ErrorSystemMessage(systemExceptionCode, priority);
    }

    protected void addWarning() {
        warningSystemMessage = new WarningSystemMessage(systemExceptionCode, errorSystemMessage.getPriority());
    }

    public void show() {
        if (errorSystemMessage != null && errorSystemMessage.getPriority() >= Primary.getMessagePriority()) {
            errorSystemMessage.show();
            if (warningSystemMessage != null && warningSystemMessage.getPriority() >= Primary.getMessagePriority()) {
                warningSystemMessage.show();
            }
        }
    }
}
