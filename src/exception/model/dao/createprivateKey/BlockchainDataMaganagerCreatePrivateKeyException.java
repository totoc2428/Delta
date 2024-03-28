package exception.model.dao.createprivateKey;

import exception.model.dao.BlockchainDataMaganagerException;

public abstract class BlockchainDataMaganagerCreatePrivateKeyException extends BlockchainDataMaganagerException {
    public BlockchainDataMaganagerCreatePrivateKeyException(String message, String code) {
        super(message, code);
    }
}