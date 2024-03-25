package exception.model.controleurs.blockchain.main.createprivateKey;

import exception.model.controleurs.blockchain.main.BlockchainDataMaganagerException;

public abstract class BlockchainDataMaganagerCreatePrivateKeyException extends BlockchainDataMaganagerException {
    public BlockchainDataMaganagerCreatePrivateKeyException(String message, String code) {
        super(message, code);
    }
}