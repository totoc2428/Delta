package exception.system;

public class SystemException extends Exception {
    private static String languageCode = "FR";

    public SystemException(String systemExceptionCode) {
        super(systemExceptionCode);
    }

    public static String getLanguageCode() {
        return languageCode;
    }

    public static void setLanguageCode(String languageCode) {
        SystemException.languageCode = languageCode;
    }
}
