package model.dto.blockchain.chainObject.publickey;

import model.dto.blockchain.chainObject.ChainObjectException;

public class ChainObjectPublicKeyIsNull extends ChainObjectException {
    public ChainObjectPublicKeyIsNull() {
        super("The chainObject publicKey can't be null. ");
    }
}
