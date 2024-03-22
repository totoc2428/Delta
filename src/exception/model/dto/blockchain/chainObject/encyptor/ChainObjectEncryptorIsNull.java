package exception.model.dto.blockchain.chainObject.encyptor;

import exception.model.dto.blockchain.chainObject.ChainObjectException;

public class ChainObjectEncryptorIsNull extends ChainObjectException {
    public ChainObjectEncryptorIsNull() {
        super("ChainObject Encyptor is null.");
    }
}
