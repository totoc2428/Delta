package exception.system.util.primary;

import exception.system.util.UtilSystemException;

public class InvalidMessagePrioritySystemException extends UtilSystemException {

    public InvalidMessagePrioritySystemException() {
        super("InvalidMessagePrioritySystemException");
        addWarning();
    }

}
