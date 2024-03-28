package exception.model.dao.createprivateKey;

public class BlockchainDataMaganagerCreatePrivateKeyPassPhraseIsNullException
        extends BlockchainDataMaganagerCreatePrivateKeyException {
    public BlockchainDataMaganagerCreatePrivateKeyPassPhraseIsNullException() {
        super("The pass phrase can't be null to build the private key",
                "BlockchainDataMaganagerCreatePrivateKeyPassPhraseIsNullException");
    }
}