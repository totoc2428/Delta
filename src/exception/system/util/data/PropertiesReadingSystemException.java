package exception.system.util.data;

import exception.system.SystemException;

public class PropertiesReadingSystemException extends SystemException {
    public PropertiesReadingSystemException() {
        super("PropertieReadingSystemException");
        addWarning();
    }
}
