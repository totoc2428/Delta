package exception.system.util.tool;

import exception.system.SystemException;

public class PrimaryLoadException extends SystemException {

    public PrimaryLoadException() {
        super("PrimaryLoadException");
        addWarning();
    }

}
