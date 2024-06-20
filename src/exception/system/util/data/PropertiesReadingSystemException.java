package exception.system.util.data;

import exception.system.SystemException;
import util.message.warning.WarningSystemMessage;

public class PropertiesReadingSystemException extends SystemException {
    public PropertiesReadingSystemException() {
        super("PropertieReadingSystemException");
    }

    @Override
    public void show() {
        super.show();
        new WarningSystemMessage("PropertiesReadingSystemException").show();
    }
}
