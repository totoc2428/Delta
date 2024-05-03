package exception.model.dao.blockchain.publickey.string.get;

import exception.model.dao.blockchain.publickey.PublicKeyBlockchainDataManagerException;

public class GetStringToPublicKeyBlockchainDataManagerException extends PublicKeyBlockchainDataManagerException {
    public GetStringToPublicKeyBlockchainDataManagerException(String message, String code) {
        super(message, code);
    }
}
