package exception.model.dao.blockchain.privatekey.create;

public class BlockchainDataManagerCreatePrivateKeyForNamesIsNullException
        extends BlockchainDataManagerCreatePrivateKeyException {
    public BlockchainDataManagerCreatePrivateKeyForNamesIsNullException() {
        super("The name list can't be null to build the private key",
                "BlockchainDataMaganagerCreatePrivateKeyForNamesIsNullException");
    }
}