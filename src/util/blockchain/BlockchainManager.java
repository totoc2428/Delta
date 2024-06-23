package util.blockchain;

import java.util.Properties;

import exception.system.util.blockchain.BlockchainManagerLoadException;
import exception.system.util.data.PropertiesReadingSystemException;
import util.data.DataManager;
import util.primary.Primary;

public abstract class BlockchainManager {
    private static Properties initBlockchainProperties;

    /* -LOADER */
    public static void load() throws BlockchainManagerLoadException {
        try {
            initBlockchainProperties = DataManager.readAFile(Primary.getBlockchainManagerInitPath());
        } catch (PropertiesReadingSystemException e) {
            e.show();

            throw new BlockchainManagerLoadException();
        }
    }

    /* -INIT */
    /* --GETTER */
    public static Properties getInit() {
        return initBlockchainProperties;
    }
}
