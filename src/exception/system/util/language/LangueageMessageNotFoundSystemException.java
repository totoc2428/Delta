package exception.system.util.language;

import exception.system.SystemException;
import util.message.warning.WarningSystemMessage;

public class LangueageMessageNotFoundSystemException extends SystemException {
    public LangueageMessageNotFoundSystemException() {
        super("LangueageMessageNotFoundSystemException");

        System.out.println("before");
        new WarningSystemMessage("LangueageMessageNotFoundSystemException").show();
    }
}
