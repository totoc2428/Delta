package exception.model.dao.blockchain.privatekey.string;

import exception.model.dao.blockchain.privatekey.PrivateKeyBlockchainDataManagerException;

public class StringToPrivateKeyBlockchainDataManagerException extends PrivateKeyBlockchainDataManagerException {
    public StringToPrivateKeyBlockchainDataManagerException() {
        super("Invalid key String format.", "StringPrivateKeyBlockchainDataManagerException");
    }
}
