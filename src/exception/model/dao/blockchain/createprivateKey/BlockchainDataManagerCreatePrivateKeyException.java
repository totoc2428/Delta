package exception.model.dao.blockchain.createprivateKey;

import exception.model.dao.blockchain.BlockchainDataManagerException;

public abstract class BlockchainDataManagerCreatePrivateKeyException extends BlockchainDataManagerException {
    public BlockchainDataManagerCreatePrivateKeyException(String message, String code) {
        super(message, code);
    }
}