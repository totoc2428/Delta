package exception.model.dao.createprivateKey;

public class BlockchainDataManagerCreatePrivateKeyNameIsNullException
        extends BlockchainDataManagerCreatePrivateKeyException {
    public BlockchainDataManagerCreatePrivateKeyNameIsNullException() {
        super("The name can't be null to build the private key",
                "BlockchainDataMaganagerCreatePrivateKeyNameIsNullException");
    }
}