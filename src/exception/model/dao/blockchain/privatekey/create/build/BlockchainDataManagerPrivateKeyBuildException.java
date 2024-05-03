package exception.model.dao.blockchain.privatekey.create.build;

import exception.model.dao.blockchain.privatekey.create.BlockchainDataManagerCreatePrivateKeyException;

public class BlockchainDataManagerPrivateKeyBuildException extends BlockchainDataManagerCreatePrivateKeyException {

    public BlockchainDataManagerPrivateKeyBuildException() {
        super("The pass phrase can't be null to build the private key",
                "BlockchainDataManagerPrivateKeyBuildException");
    }

}
