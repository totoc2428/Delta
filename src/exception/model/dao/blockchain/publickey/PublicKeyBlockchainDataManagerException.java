package exception.model.dao.blockchain.publickey;

import exception.model.dao.blockchain.BlockchainDataManagerException;

public class PublicKeyBlockchainDataManagerException extends BlockchainDataManagerException{
    public PublicKeyBlockchainDataManagerException(String message, String code) {
        super(message, code);
    }
}
