package exception.model.dao.blockchain.createprivateKey;

public class BlockchainDataManagerCreatePrivateKeyPassPhraseIsNullException
        extends BlockchainDataManagerCreatePrivateKeyException {
    public BlockchainDataManagerCreatePrivateKeyPassPhraseIsNullException() {
        super("The pass phrase can't be null to build the private key",
                "BlockchainDataMaganagerCreatePrivateKeyPassPhraseIsNullException");
    }
}