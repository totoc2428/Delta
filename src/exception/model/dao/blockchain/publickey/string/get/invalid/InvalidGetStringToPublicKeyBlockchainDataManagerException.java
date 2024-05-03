package exception.model.dao.blockchain.publickey.string.get.invalid;

import exception.model.dao.blockchain.publickey.string.get.GetStringToPublicKeyBlockchainDataManagerException;

public class InvalidGetStringToPublicKeyBlockchainDataManagerException
        extends GetStringToPublicKeyBlockchainDataManagerException {
    public InvalidGetStringToPublicKeyBlockchainDataManagerException() {
        super("The text gived to build the key is invalid.",
                "InvalidGetStringToPublicKeyBlockchainDataManagerException");
    }
}
