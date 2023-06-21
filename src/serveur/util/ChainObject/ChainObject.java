package serveur.util.ChainObject;

import serveur.util.security.Key;

public abstract class ChainObject {
    private Key signature;

    public ChainObject(Key signature) {
        this.signature = signature;
    }

    public Key getSignature() {
        return signature;
    }

    public String toWriteFormat() {
        return signature + ";";
    }
}
