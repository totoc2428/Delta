package exception.message;

import java.util.Properties;

import exception.system.SystemException;
import util.DataManager;

public abstract class ExceptionMessage {
    public static Properties messages;

    public static String getAnExceptionMessage(String systemExceptionCode) {
        return (String) messages.get(systemExceptionCode);
    }

    public static void load() {
        messages = DataManager.readAFile(SystemException.getLanguageCode().toLowerCase() + "messages.conf");
    }
}
