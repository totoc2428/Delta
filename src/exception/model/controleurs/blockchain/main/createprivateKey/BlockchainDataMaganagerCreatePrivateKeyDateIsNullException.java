package exception.model.controleurs.blockchain.main.createprivateKey;

public class BlockchainDataMaganagerCreatePrivateKeyDateIsNullException
        extends BlockchainDataMaganagerCreatePrivateKeyException {
    public BlockchainDataMaganagerCreatePrivateKeyDateIsNullException() {
        super("The date can't be null to build the private key",
                "BlockchainDataMaganagerCreatePrivateKeyDateIsNullException");
    }
}