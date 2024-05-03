package exception.model.dao.blockchain.privatekey.create.fornames;

import exception.model.dao.blockchain.privatekey.create.BlockchainDataManagerCreatePrivateKeyException;

public class BlockchainDataManagerCreatePrivateKeyForNamesIsNullException
        extends BlockchainDataManagerCreatePrivateKeyException {
    public BlockchainDataManagerCreatePrivateKeyForNamesIsNullException() {
        super("The name list can't be null to build the private key",
                "BlockchainDataMaganagerCreatePrivateKeyForNamesIsNullException");
    }
}