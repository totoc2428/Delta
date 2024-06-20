package exception.system.util.language;

import exception.system.SystemException;
import util.message.warning.WarningSystemMessage;

public class LangueageMessageNotFoundSystemException extends SystemException {
    public LangueageMessageNotFoundSystemException() {
        super("LangueageMessageNotFoundSystemException");

    }

    @Override
    public void show() {
        super.show();
        new WarningSystemMessage("LangueageMessageNotFoundSystemException").show();
    }
}
