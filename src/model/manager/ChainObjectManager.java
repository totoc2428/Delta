package model.manager;

import java.util.Properties;

import exception.system.model.manager.chainobject.ChainObjectManagerLoadSystemException;
import exception.system.model.manager.chainobject.ChainObjectManagerSetInitFilePathSystemException;
import exception.system.model.manager.chainobject.SaveChainobjectSystemException;
import exception.system.util.blockchain.PrivateKeyToSavedFormatSystemException;
import exception.system.util.blockchain.PublicKeyToSavedFormatSystemException;
import exception.system.util.data.PropertiesReadingSystemException;
import exception.system.util.security.EncryptSecurityManagerSystemException;
import model.entity.ChainObject;
import util.blockchain.BlockchainManager;
import util.data.DataManager;
import util.primary.Primary;
import util.security.SecurityManager;

public abstract class ChainObjectManager {
    private static Properties initChainObjectManager;

    private static String chainObjectSavedTag;
    private static String savedObjectTypeKey;

    /* -LOADER */
    public static void load() throws ChainObjectManagerLoadSystemException {
        if (initChainObjectManager == null) {
            try {
                setInitFilePath(Primary.getBlockchainManagerInitPath());
            } catch (ChainObjectManagerSetInitFilePathSystemException e) {
                e.show();
                throw new ChainObjectManagerLoadSystemException();
            }
        }
        if (initChainObjectPropertiesCheck()) {
            chainObjectSavedTag = initChainObjectManager.getProperty("CHAINOBJECT_SAVED_TAG");
            savedObjectTypeKey = initChainObjectManager.getProperty("SAVED_OBJECT_TYPE_KEY");
        } else {
            throw new ChainObjectManagerLoadSystemException();
        }
    }

    /* -INIT */
    /* --SETTER */
    public static void setInitFilePath(String filePath) throws ChainObjectManagerSetInitFilePathSystemException {
        try {
            System.out.println(filePath);
            ChainObjectManager.initChainObjectManager = DataManager.readAFile(filePath);
        } catch (PropertiesReadingSystemException e) {
            e.show();
            throw new ChainObjectManagerSetInitFilePathSystemException();
        }
    }

    /* --CHEKER */
    private static boolean initChainObjectPropertiesCheck() {
        return checkAChainObjectProperty("CHAINOBJECT_SAVED_TAG") &&
                checkAChainObjectProperty("SAVED_OBJECT_TYPE_KEY");
    }

    private static boolean checkAChainObjectProperty(String primaryCode) {
        return (initChainObjectManager.getProperty(primaryCode) != null && (!initChainObjectManager
                .getProperty(
                        primaryCode)
                .isBlank() && !initChainObjectManager.getProperty(primaryCode).isEmpty()));
    }

    /* -SAVE */
    public static Properties save(ChainObject chainObject) throws SaveChainobjectSystemException {
        Properties chainObjectProperties = new Properties();

        chainObjectProperties.setProperty(savedObjectTypeKey, chainObjectSavedTag);
        try {
            chainObjectProperties.setProperty("privatekey",
                    SecurityManager.encrypt(BlockchainManager.privateKeyToSavedFormat(chainObject.getPrivateKey()),
                            chainObject.getPublicKey()));

            chainObjectProperties.setProperty("publickey",
                    BlockchainManager.publicKeyToSavedFormat(chainObject.getPublicKey()));

        } catch (EncryptSecurityManagerSystemException | PrivateKeyToSavedFormatSystemException
                | PublicKeyToSavedFormatSystemException e) {
            e.show();
            throw new SaveChainobjectSystemException();
        }

        return chainObjectProperties;
    }
}
