package exception.model.dao.blockchain.encrypt;

import exception.model.dao.blockchain.BlockchainDataManagerException;

public class EncryptBlockchainDataManagerException extends BlockchainDataManagerException {

    public EncryptBlockchainDataManagerException() {
        super("An error occuring wen we encrypt de data.",
                "EncryptBlockchainDataManagerException");
    }
}
