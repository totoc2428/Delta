package exception.model.dao.blockchain.privatekey.create;

import exception.model.dao.blockchain.BlockchainDataManagerException;

public abstract class BlockchainDataManagerCreatePrivateKeyException extends BlockchainDataManagerException {
    public BlockchainDataManagerCreatePrivateKeyException(String message, String code) {
        super(message, code);
    }
}