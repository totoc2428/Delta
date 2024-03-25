package exception.model.dto.blockchain.chainObject;

import exception.SystemException;

public abstract class ChainObjectException extends SystemException {
    public ChainObjectException(String message, String code) {
        super(message, code);
    }
}
