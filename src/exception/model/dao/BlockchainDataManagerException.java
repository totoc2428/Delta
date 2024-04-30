package exception.model.dao;

import exception.SystemException;

public abstract class BlockchainDataManagerException extends SystemException {
    public BlockchainDataManagerException(String message, String code) {
        super(message, code);
    }
}
