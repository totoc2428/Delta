package exception.model.dao.blockchain.privatekey.create.date;

import exception.model.dao.blockchain.privatekey.create.BlockchainDataManagerCreatePrivateKeyException;

public class BlockchainDataManagerCreatePrivateKeyDateIsNullException
        extends BlockchainDataManagerCreatePrivateKeyException {
    public BlockchainDataManagerCreatePrivateKeyDateIsNullException() {
        super("The date can't be null to build the private key",
                "BlockchainDataMaganagerCreatePrivateKeyDateIsNullException");
    }
}