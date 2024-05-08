package main.model.dto.blockchain.chainobject;

import java.security.PrivateKey;
import java.security.PublicKey;

import main.model.dao.blockchain.BlockchainDataManager;
import exception.SystemException;
import exception.model.dto.blockchain.chainObject.ChainObjectException;

import exception.model.dto.blockchain.chainObject.publickey.ChainObjectPublicKeyIsNullException;

public abstract class ChainObject {
    private PrivateKey privateKey;
    private PublicKey publicKey;

    /* constructor */
    public ChainObject(PrivateKey privateKey, PublicKey publicKey)
            throws ChainObjectException {

        this.privateKey = privateKey;
        if (publicKey != null) {
            this.publicKey = publicKey;
        } else {
            throw new ChainObjectPublicKeyIsNullException();
        }
    }

    /* getter */
    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    /* override */
    @Override
    public String toString() {
        try {
            return "ChainObject [privateKey(encrypted)=" + BlockchainDataManager.encryptWithPublicKey(
                    BlockchainDataManager.privateKeyToString(privateKey), publicKey) + ", publicKey=" + publicKey + "]";
        } catch (SystemException e) {
            return null;
        }
    }

}