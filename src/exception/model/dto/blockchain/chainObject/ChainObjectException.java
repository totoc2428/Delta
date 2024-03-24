package exception.model.dto.blockchain.chainObject;

public abstract class ChainObjectException extends Exception {
    private String code;

    public ChainObjectException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
