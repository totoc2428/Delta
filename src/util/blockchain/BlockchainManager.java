package util.blockchain;

import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.util.Properties;

import exception.system.util.blockchain.BlockchainManagerLoadException;
import exception.system.util.blockchain.GeneratePrivateKeyFromStringSystemException;
import exception.system.util.data.PropertiesReadingSystemException;
import java.security.KeyPair;
import util.data.DataManager;
import util.primary.Primary;

public abstract class BlockchainManager {
    private static Properties initBlockchainProperties;

    private static

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

    /* -GENERATOR */
    /* --PRIVATE_KEY */
    public static PrivateKey generatePrivateKeyFromString(String input)
            throws GeneratePrivateKeyFromStringSystemException {
        try {
            SecureRandom random = SecureRandom.getInstance(KEY_RANDOM_GENERATOR_INSTANCE);
            random.setSeed(input.getBytes());

            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyGen.initialize(KEY_SIZE, random);

            KeyPair keyPair = keyGen.generateKeyPair();

            return keyPair.getPrivate();

        } catch (Exception e) {
            throw new GeneratePrivateKeyFromStringSystemException();
        }

    }
}
