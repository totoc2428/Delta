package blockchain.chainobject;

import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Properties;

import blockchain.util.BlockChainDataManager;

public abstract class ChainObject {
    private PrivateKey privateKey;
    private PublicKey publicKey;

    /* constructor */
    public ChainObject(PrivateKey privateKey, PublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public ChainObject(File fileName) {
        this(read(fileName));
    }

    private ChainObject(ChainObject chainObject) {
        this(chainObject.privateKey, chainObject.publicKey);
    }

    /* getter */
    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    /* reader */
    public static ChainObject read(File fileName) {
        return null;
    }

    public Properties initWrite() {
        return BlockChainDataManager.chainObjectToProperties(this);
    }

    @Override
    public String toString() {
        return BlockChainDataManager.SAVED_CHAINOBJECT_TAG + privateKey == null
                ? BlockChainDataManager.SAVED_PUBLIC_KEY_TAG + publicKey.toString()
                : BlockChainDataManager.SAVED_PRIVATE_KEY_TAG + privateKey.toString();
    }

}