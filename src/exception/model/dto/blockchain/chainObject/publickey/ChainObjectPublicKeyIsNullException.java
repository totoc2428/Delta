package exception.model.dto.blockchain.chainObject.publickey;

import exception.model.dto.blockchain.chainObject.ChainObjectException;

public class ChainObjectPublicKeyIsNullException extends ChainObjectException {
    public ChainObjectPublicKeyIsNullException() {
        super("The chainObject publicKey can't be null. ", "ChainObjectPublicKeyIsNullException");
    }
}
