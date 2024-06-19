package exception.system;

import exception.message.ExceptionMessage;
import exception.system.util.language.LangueageMessageNotFoundSystemExcetion;

public class SystemException extends Exception {
    private static String languageCode = "FR";

    public SystemException(String systemExceptionCode) {
        super(systemExceptionCode);
    }

    public static String getLanguageCode() {
        return languageCode;
    }

    public static void load() throws LangueageMessageNotFoundSystemExcetion {
        ExceptionMessage.load();
    }

    public static void setLanguageCode(String languageCode) {
        SystemException.languageCode = languageCode;
        try {
            load();
        } catch (LangueageMessageNotFoundSystemExcetion e) {
            System.out.println(e.getMessage());
        }
    }
}
