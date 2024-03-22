package exception.model.dto.blockchain.chainObject.encyptor;

import exception.model.dto.blockchain.chainObject.ChainObjectException;

public class ChainObjectEncyptorIsNotAESException extends ChainObjectException {
    public ChainObjectEncyptorIsNotAESException() {
        super("Encyptor have note AES algorithme Exception");
    }
}
