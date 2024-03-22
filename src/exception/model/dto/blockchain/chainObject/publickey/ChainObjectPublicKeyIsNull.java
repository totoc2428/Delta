package exception.model.dto.blockchain.chainObject.publickey;

import exception.model.dto.blockchain.chainObject.ChainObjectException;

public class ChainObjectPublicKeyIsNull extends ChainObjectException {
    public ChainObjectPublicKeyIsNull() {
        super("The chainObject publicKey can't be null. ");
    }
}
