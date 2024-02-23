package model.dto.blockchain.chainobject;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Properties;

import model.dao.blockchain.chainobject.ChainObjectDataManager;

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

    public Properties initWrite() {
        return ChainObjectDataManager.chainObjectToProperties(this);
    }

    @Override
    public String toString() {
        return ChainObjectDataManager.SAVED_CHAINOBJECT_TAG + privateKey == null
                ? ChainObjectDataManager.SAVED_PUBLIC_KEY_TAG + publicKey.toString()
                : ChainObjectDataManager.SAVED_PRIVATE_KEY_TAG + privateKey.toString();
    }

}