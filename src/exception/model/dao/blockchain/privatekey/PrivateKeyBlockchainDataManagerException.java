package exception.model.dao.blockchain.privatekey;

import exception.model.dao.blockchain.BlockchainDataManagerException;

public class PrivateKeyBlockchainDataManagerException extends BlockchainDataManagerException {
    public PrivateKeyBlockchainDataManagerException(String message, String code) {
        super(message, code);
    }
}
