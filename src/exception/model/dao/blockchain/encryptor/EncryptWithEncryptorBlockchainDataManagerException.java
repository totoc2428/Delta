package exception.model.dao.blockchain.encryptor;

public class EncryptWithEncryptorBlockchainDataManagerException extends EncryptorBlockchainDataManagerException {

    public EncryptWithEncryptorBlockchainDataManagerException() {
        super("An error occuring wen we encrypt de data with the encryptor.",
                "GenerateEncryptorBlockchainDataManagerException");
    }
}
