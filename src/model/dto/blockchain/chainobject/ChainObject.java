package model.dto.blockchain.chainobject;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import model.dao.blockchain.BlockchainDataMaganager;

public abstract class ChainObject {
    private PrivateKey privateKey;
    private PublicKey publicKey;

    private KeyPair encryptor;

    /* constructor */
    public ChainObject(PrivateKey privateKey, PublicKey publicKey, KeyPair encryptor) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;

        this.encryptor = encryptor;
    }

    /* getter */
    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public KeyPair getEncryptor() {
        return encryptor;
    }

    /* override */
    @Override
    public String toString() {
        return "ChainObject [privateKey(encrypted)=" + BlockchainDataMaganager.encryptWithPublicKey(
                BlockchainDataMaganager.privateKeyToString(privateKey), publicKey) + ", publicKey=" + publicKey + "]";
    }

}