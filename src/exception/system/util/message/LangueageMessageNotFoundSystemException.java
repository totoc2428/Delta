package exception.system.util.message;

import exception.system.SystemException;

public class LangueageMessageNotFoundSystemException extends SystemException {
    public LangueageMessageNotFoundSystemException() {
        super("LangueageMessageNotFoundSystemException");
        addWarning();
    }
}
