package exception.system.util.message;

import exception.system.util.UtilSystemException;

public class LangueageMessageNotFoundSystemException extends UtilSystemException {
    public LangueageMessageNotFoundSystemException() {
        super("LangueageMessageNotFoundSystemException");
        addWarning();
    }
}
