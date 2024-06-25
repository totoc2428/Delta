package exception.system.util.blockchain;

import exception.system.util.UtilSystemException;

public class BlockchainManagerLoadException extends UtilSystemException {

    public BlockchainManagerLoadException() {
        super("BlockchainManagerLoadException");
        addWarning();
    }

}
