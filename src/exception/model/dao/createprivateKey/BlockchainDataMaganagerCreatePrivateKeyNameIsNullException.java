package exception.model.dao.createprivateKey;

public class BlockchainDataMaganagerCreatePrivateKeyNameIsNullException
        extends BlockchainDataMaganagerCreatePrivateKeyException {
    public BlockchainDataMaganagerCreatePrivateKeyNameIsNullException() {
        super("The name can't be null to build the private key",
                "BlockchainDataMaganagerCreatePrivateKeyNameIsNullException");
    }
}