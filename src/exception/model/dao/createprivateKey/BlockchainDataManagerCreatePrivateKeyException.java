package exception.model.dao.createprivateKey;

import exception.model.dao.BlockchainDataManagerException;

public abstract class BlockchainDataManagerCreatePrivateKeyException extends BlockchainDataManagerException {
    public BlockchainDataManagerCreatePrivateKeyException(String message, String code) {
        super(message, code);
    }
}