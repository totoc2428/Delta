package model.entity;

import java.security.PrivateKey;
import java.security.PublicKey;

public class ChainObject {
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public ChainObject(PrivateKey privateKey, PublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}
