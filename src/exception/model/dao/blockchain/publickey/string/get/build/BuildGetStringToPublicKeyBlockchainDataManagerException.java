package exception.model.dao.blockchain.publickey.string.get.build;

import exception.model.dao.blockchain.publickey.string.get.GetStringToPublicKeyBlockchainDataManagerException;

public class BuildGetStringToPublicKeyBlockchainDataManagerException
        extends GetStringToPublicKeyBlockchainDataManagerException {
    public BuildGetStringToPublicKeyBlockchainDataManagerException() {
        super("The text gived to build the key is invalid.",
                "BuildGetStringToPublicKeyBlockchainDataManagerException");
    }
}
