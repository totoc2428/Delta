package model.manager;

import java.util.Properties;

import exception.system.model.manager.chainobject.ChainObjectManagerLoadSystemException;
import exception.system.model.manager.chainobject.ChainObjectManagerSetInitFilePathSystemException;
import exception.system.util.data.PropertiesReadingSystemException;
import util.data.DataManager;
import util.primary.Primary;

public abstract class ChainObjectManager {
    private static Properties initChainObjectManager;

    private static String chainObjectSavedTag;

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
        return checkAChainObjectProperty("CHAINOBJECT_SAVED_TAG");
    }

    private static boolean checkAChainObjectProperty(String primaryCode) {
        return (initChainObjectManager.getProperty(primaryCode) != null && (!initChainObjectManager
                .getProperty(
                        primaryCode)
                .isBlank() && !initChainObjectManager.getProperty(primaryCode).isEmpty()));
    }
}
