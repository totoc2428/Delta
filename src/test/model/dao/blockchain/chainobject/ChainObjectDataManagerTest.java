package test.model.dao.blockchain.chainobject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Properties;

import org.junit.Test;

import main.model.dao.blockchain.BlockchainDataManager;
import main.model.dao.blockchain.chainobject.ChainObjectDataManager;

public class ChainObjectDataManagerTest {
    @Test
    public void testChainObjectDataManagerPropertiesIsNotNull() {
        assertNotNull(ChainObjectDataManager.CHAINOBJECT_PROPERTIES);

        assertNotNull(ChainObjectDataManager.SAVED_PRIVATE_VALUE_TAG);
        assertNotNull(ChainObjectDataManager.SAVED_PUBLIC_VALUE_TAG);

        assertNotNull(ChainObjectDataManager.SAVED_CHAINOBJECT_TAG);

        assertNotNull(ChainObjectDataManager.SAVED_ENCRYPTOR_KEY);

        assertNotNull(ChainObjectDataManager.CHAINOBJECT_FILE_SAVED_TAG);

        assertNotNull(ChainObjectDataManager.getChainObjectSrcPath());

    }

    @Test
    public void testChainObjectDataManagerPropertiesValue() {
        Properties chainobjectProperties = ChainObjectDataManager.read(
                BlockchainDataManager.BLOCKCHAIN_PROPERTIES.getProperty("CHAINOBJECT_PROPERTIES"));

        String savedPrivateValueTag = chainobjectProperties.getProperty("SAVED_PRIVATE_VALUE_TAG");
        String savedPublicValueTag = chainobjectProperties.getProperty("SAVED_PUBLIC_VALUE_TAG");

        String savedChainObjectTag = chainobjectProperties.getProperty("SAVED_CHAINOBJECT_TAG");

        String savedEncryptorKey = chainobjectProperties.getProperty("SAVED_ENCRYPTOR_KEY");

        String chainObjectFileSavedTag = chainobjectProperties.getProperty("CHAINOBJECT_FILE_SAVED_TAG");

        String chainObjectSrcPath = chainobjectProperties.getProperty("chainObjectSrcPath");

        assertEquals(savedPrivateValueTag, ChainObjectDataManager.SAVED_PRIVATE_VALUE_TAG);
        assertEquals(savedPublicValueTag, ChainObjectDataManager.SAVED_PUBLIC_VALUE_TAG);

        assertEquals(savedChainObjectTag, ChainObjectDataManager.SAVED_CHAINOBJECT_TAG);

        assertEquals(savedEncryptorKey, ChainObjectDataManager.SAVED_ENCRYPTOR_KEY);

        assertEquals(chainObjectFileSavedTag, ChainObjectDataManager.CHAINOBJECT_FILE_SAVED_TAG);

        assertEquals(chainObjectSrcPath, ChainObjectDataManager.getChainObjectSrcPath());

    }
}
