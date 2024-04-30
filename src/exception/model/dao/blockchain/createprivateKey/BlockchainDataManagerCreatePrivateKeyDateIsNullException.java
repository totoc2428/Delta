package exception.model.dao.blockchain.createprivateKey;

public class BlockchainDataManagerCreatePrivateKeyDateIsNullException
        extends BlockchainDataManagerCreatePrivateKeyException {
    public BlockchainDataManagerCreatePrivateKeyDateIsNullException() {
        super("The date can't be null to build the private key",
                "BlockchainDataMaganagerCreatePrivateKeyDateIsNullException");
    }
}