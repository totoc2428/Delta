package exception.model.controleurs.blockchain.main;

import exception.SystemException;

public abstract class BlockchainDataMaganagerException extends SystemException {
    public BlockchainDataMaganagerException(String message, String code) {
        super(message, code);
    }
}
