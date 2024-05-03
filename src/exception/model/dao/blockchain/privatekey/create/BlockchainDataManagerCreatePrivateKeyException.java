package exception.model.dao.blockchain.privatekey.create;

import exception.model.dao.blockchain.privatekey.PrivateKeyBlockchainDataManagerException;

public abstract class BlockchainDataManagerCreatePrivateKeyException extends PrivateKeyBlockchainDataManagerException {
    public BlockchainDataManagerCreatePrivateKeyException(String message, String code) {
        super(message, code);
    }
}