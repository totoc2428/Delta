package exception.message;

import java.util.Properties;

import exception.system.SystemException;
import exception.system.util.data.PropertieReadingSystemException;
import exception.system.util.language.LangueageMessageNotFoundSystemExcetion;
import util.DataManager;

public abstract class ExceptionMessage {
    public static Properties messages;

    public static String getAnExceptionMessage(String systemExceptionCode) {
        return (String) messages.get(systemExceptionCode);
    }

    public static void load() throws LangueageMessageNotFoundSystemExcetion {
        try {
            messages = DataManager.readAFile(
                    "ressources/exception/" + SystemException.getLanguageCode().toLowerCase() + "/messages.conf");
        } catch (PropertieReadingSystemException e) {
            throw new LangueageMessageNotFoundSystemExcetion();
        }
    }

}
