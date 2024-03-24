package exception.model.dto.blockchain.chainObject.encyptor;

import exception.model.dto.blockchain.chainObject.ChainObjectException;

public class ChainObjectEncryptorIsNullException extends ChainObjectException {
    public ChainObjectEncryptorIsNullException() {
        super("ChainObject Encyptor is null.", "ChainObjectEncryptorIsNull");
    }
}
