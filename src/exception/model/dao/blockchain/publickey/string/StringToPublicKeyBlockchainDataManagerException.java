package exception.model.dao.blockchain.publickey.string;

import exception.model.dao.blockchain.publickey.PublicKeyBlockchainDataManagerException;

public class StringToPublicKeyBlockchainDataManagerException extends PublicKeyBlockchainDataManagerException {
    public StringToPublicKeyBlockchainDataManagerException(String message, String code) {
        super(message, code);
    }
}
