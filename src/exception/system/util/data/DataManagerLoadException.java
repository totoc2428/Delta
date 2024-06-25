package exception.system.util.data;

import exception.system.util.UtilSystemException;

public class DataManagerLoadException extends UtilSystemException {

    public DataManagerLoadException() {
        super("DataManagerLoadException");
        addWarning();
    }

}
