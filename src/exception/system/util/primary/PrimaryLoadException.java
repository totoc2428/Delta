package exception.system.util.primary;

import exception.system.util.UtilSystemException;

public class PrimaryLoadException extends UtilSystemException {

    public PrimaryLoadException() {
        super("PrimaryLoadException");
        addWarning();
    }

}
