package exception.system.util.primary;

import exception.system.SystemException;

public class PrimaryLoadException extends SystemException {

    public PrimaryLoadException() {
        super("PrimaryLoadException");
        addWarning();
    }

}
