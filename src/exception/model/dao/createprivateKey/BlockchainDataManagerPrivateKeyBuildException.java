package exception.model.dao.createprivateKey;

public class BlockchainDataManagerPrivateKeyBuildException extends BlockchainDataMaganagerCreatePrivateKeyException{

    public BlockchainDataManagerPrivateKeyBuildException() {
        super("The pass phrase can't be null to build the private key",
                "BlockchainDataManagerPrivateKeyBuildException");
    }
    
}
