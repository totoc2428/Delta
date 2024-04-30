package exception.model.dao.blockchain.publickey.getfromprivate;

import exception.model.dao.blockchain.publickey.PublicKeyBlockchainDataManagerException;

public class GetFromPrivatePublicKeyBlockchainDataManagerException extends PublicKeyBlockchainDataManagerException {
    public GetFromPrivatePublicKeyBlockchainDataManagerException() {
        super("The name list can't be null to build the private key",
                "GetFromPrivatePublicKeyBlockchainDataManagerException");
    }
}
