package exception.system.util.data;

import exception.system.SystemException;

public class DataManagerLoadException extends SystemException {

    public DataManagerLoadException() {
        super("DataManagerLoadException");
        addWarning();
    }

}
