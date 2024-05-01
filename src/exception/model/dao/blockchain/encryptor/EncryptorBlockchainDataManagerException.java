package exception.model.dao.blockchain.encryptor;

import exception.model.dao.blockchain.BlockchainDataManagerException;

public class EncryptorBlockchainDataManagerException extends BlockchainDataManagerException {
    public EncryptorBlockchainDataManagerException(String message, String code) {
        super(message, code);
    }
}
