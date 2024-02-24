package model.dto.blockchain.chainobject;

import java.security.PrivateKey;
import java.security.PublicKey;

import model.dao.blockchain.BlockchainDataMaganager;

public abstract class ChainObject {
    private PrivateKey privateKey;
    private PublicKey publicKey;

    /* constructor */
    public ChainObject(PrivateKey privateKey, PublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    /* getter */
    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    @Override
    public String toString() {
        return "ChainObject [privateKey(encrypted)=" + BlockchainDataMaganager.encryptWithPublicKey(
                BlockchainDataMaganager.privateKeyToString(privateKey), publicKey) + ", publicKey=" + publicKey + "]";
    }

}