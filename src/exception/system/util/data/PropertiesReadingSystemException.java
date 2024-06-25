package exception.system.util.data;

import exception.system.util.UtilSystemException;

public class PropertiesReadingSystemException extends UtilSystemException {
    public PropertiesReadingSystemException() {
        super("PropertieReadingSystemException");
        addWarning();
    }
}
