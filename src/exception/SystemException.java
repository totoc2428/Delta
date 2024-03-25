package exception;

public abstract class SystemException extends Exception {
    private String code;

    public SystemException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
