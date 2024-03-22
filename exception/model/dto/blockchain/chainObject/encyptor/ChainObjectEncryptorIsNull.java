package model.dto.blockchain.chainObject.encyptor;

import model.dto.blockchain.chainObject.ChainObjectException;

public class ChainObjectEncryptorIsNull extends ChainObjectException {
    public ChainObjectEncryptorIsNull() {
        super("ChainObject Encyptor is null.");
    }
}
