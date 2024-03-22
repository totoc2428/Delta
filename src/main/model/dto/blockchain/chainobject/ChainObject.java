package main.model.dto.blockchain.chainobject;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import main.model.dao.blockchain.BlockchainDataMaganager;
import exception.model.dto.blockchain.chainObject.ChainObjectException;
import exception.model.dto.blockchain.chainObject.encyptor.ChainObjectEncryptorIsNull;
import exception.model.dto.blockchain.chainObject.encyptor.ChainObjectEncyptorIsNotAESException;

import exception.model.dto.blockchain.chainObject.publickey.ChainObjectPublicKeyIsNull;

public abstract class ChainObject {
    private PrivateKey privateKey;
    private PublicKey publicKey;

    private KeyPair encryptor;

    /* constructor */
    public ChainObject(PrivateKey privateKey, PublicKey publicKey, KeyPair encryptor)
            throws ChainObjectException {

        this.privateKey = privateKey;
        if (publicKey != null) {
            this.publicKey = publicKey;
        } else {
            new ChainObjectPublicKeyIsNull();
        }
        if (encryptor != null) {
            if (BlockchainDataMaganager.isAESKey(encryptor.getPublic())
                    && BlockchainDataMaganager.isAESKey(encryptor.getPrivate())) {
                this.encryptor = encryptor;
            } else {
                new ChainObjectEncyptorIsNotAESException();
            }
        } else {
            new ChainObjectEncryptorIsNull();
        }
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