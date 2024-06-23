package exception.system.util.blockchain;

import exception.system.SystemException;

public class BlockchainManagerLoadException extends SystemException {

    public BlockchainManagerLoadException() {
        super("BlockchainManagerLoadException");
        addWarning();
    }

}
