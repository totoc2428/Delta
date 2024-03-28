package exception.model.dao.createprivateKey;

public class BlockchainDataMaganagerCreatePrivateKeyForNamesIsNullException
        extends BlockchainDataMaganagerCreatePrivateKeyException {
    public BlockchainDataMaganagerCreatePrivateKeyForNamesIsNullException() {
        super("The name list can't be null to build the private key",
                "BlockchainDataMaganagerCreatePrivateKeyForNamesIsNullException");
    }
}