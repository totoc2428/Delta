package test.model.dao.blockchain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Properties;

import org.junit.Test;

import exception.model.dao.blockchain.createprivateKey.BlockchainDataManagerPrivateKeyBuildException;
import exception.model.dao.blockchain.publickey.getfromprivate.GetFromPrivatePublicKeyBlockchainDataManagerException;
import main.model.dao.DataManager;
import main.model.dao.blockchain.BlockchainDataManager;

public class BlockchainDataManagerTest {

    @Test
    public void testBlockChainPropertiesIsNotNull() {
        assertNotNull(BlockchainDataManager.BLOCKCHAIN_PROPERTIES);

        assertNotNull(BlockchainDataManager.KEY_ALGORITHM);
        assertNotNull(BlockchainDataManager.DIGEST_ALGORITHM);

        assertNotNull(BlockchainDataManager.ENCRYPTOR_ALGORITHM);
        assertNotNull(BlockchainDataManager.AES_KEY_SIZE);

        assertNotNull(BlockchainDataManager.getSrcPath());
    }

    @Test
    public void testInitPropertiesValue() {

        Properties blockchainProperties = DataManager
                .read(DataManager.INIT_PROPERTIES.getProperty("BLOCKCHAIN_PROPERTIES"));

        String keyAlgorithm = blockchainProperties.getProperty("KEY_ALGORITHM");
        String digestAlgorithm = blockchainProperties.getProperty("DIGEST_ALGORITHM");

        String encryptorAlgorithm = blockchainProperties.getProperty("ENCRYPTOR_ALGORITHM");
        int aesKeySize = Integer.parseInt(blockchainProperties.getProperty("AES_KEY_SIZE"));

        String srcPath = blockchainProperties.getProperty("srcPath");

        assertEquals(keyAlgorithm, BlockchainDataManager.KEY_ALGORITHM);
        assertEquals(digestAlgorithm, BlockchainDataManager.DIGEST_ALGORITHM);

        assertEquals(encryptorAlgorithm, BlockchainDataManager.ENCRYPTOR_ALGORITHM);
        assertEquals(aesKeySize, BlockchainDataManager.AES_KEY_SIZE);

        assertEquals(srcPath, BlockchainDataManager.getSrcPath());
    }

    @Test
    public void testGeneratePrivateKeyFromString() {
        String inputForPrivateKey = "test_input_for_private_key";

        assertDoesNotThrow(() -> {
            PrivateKey privateKey = BlockchainDataManager.generatePrivateKeyFromString(inputForPrivateKey);
            PrivateKey privateKey2 = BlockchainDataManager.generatePrivateKeyFromString(inputForPrivateKey);

            assertEquals(privateKey, privateKey2);
        });

        assertThrows(BlockchainDataManagerPrivateKeyBuildException.class, () -> {
            BlockchainDataManager.generatePrivateKeyFromString(null);
        });
    }

    @Test
    public void testGetPublicKeyFromPrivateKey() {
        String inputForPrivateKey = "test_input_for_private_key";

        assertDoesNotThrow(() -> {
            PrivateKey privateKey = BlockchainDataManager.generatePrivateKeyFromString(inputForPrivateKey);

            PublicKey publicKey = BlockchainDataManager.getPublicKeyFromPrivateKey(privateKey);
            PublicKey publicKey2 = BlockchainDataManager.getPublicKeyFromPrivateKey(privateKey);

            assertNotNull(publicKey);
            assertEquals(publicKey, publicKey2);

        });

        assertThrows(GetFromPrivatePublicKeyBlockchainDataManagerException.class, () -> {
            BlockchainDataManager.getPublicKeyFromPrivateKey(null);
        });
    }

    @Test
    public void testPrivateKeyToString() {
        String inputForPrivateKey = "test_input_for_private_key";

        assertDoesNotThrow(() -> {
            PrivateKey privateKey = BlockchainDataManager.generatePrivateKeyFromString(inputForPrivateKey);

            String strPrivateKey = BlockchainDataManager.privateKeyToString(privateKey);
            String strPrivateKey2 = BlockchainDataManager.privateKeyToString(privateKey);

            assertNotNull(strPrivateKey);
            assertNotNull(strPrivateKey2);

            assertEquals(strPrivateKey, strPrivateKey2);

        });

        assertThrows(Exception.class, () -> {

            BlockchainDataManager.privateKeyToString(null);
        });

    }

    @Test
    public void testPublicKeyToString() {
        String inputForPrivateKey = "test_input_for_private_key";

        assertDoesNotThrow(() -> {
            PrivateKey privateKey = BlockchainDataManager.generatePrivateKeyFromString(inputForPrivateKey);
            PublicKey publicKey = BlockchainDataManager.getPublicKeyFromPrivateKey(privateKey);

            String strPublicKey = BlockchainDataManager.publicKeyToString(publicKey);
            String strPublicKey2 = BlockchainDataManager.publicKeyToString(publicKey);

            assertNotNull(strPublicKey);
            assertNotNull(strPublicKey2);

            assertEquals(strPublicKey, strPublicKey2);
        });

        assertThrows(Exception.class, () -> {

            BlockchainDataManager.publicKeyToString(null);
        });

    }

    @Test
    public void testStringToPrivateKey() {
        String inputForPrivateKey = "test_input_for_private_key";

        assertDoesNotThrow(() -> {
            PrivateKey privateKey = BlockchainDataManager.generatePrivateKeyFromString(inputForPrivateKey);

            String strPrivateKey = BlockchainDataManager.privateKeyToString(privateKey);

            PrivateKey privateKey2 = BlockchainDataManager.stringToPrivateKey(strPrivateKey);
            PrivateKey privateKey3 = BlockchainDataManager.stringToPrivateKey(strPrivateKey);

            assertNotNull(privateKey2);
            assertNotNull(privateKey3);

            assertEquals(privateKey2, privateKey3);
            assertEquals(privateKey, privateKey2);
        });

        assertThrows(Exception.class, () -> {
            PrivateKey privateKey = BlockchainDataManager.generatePrivateKeyFromString(inputForPrivateKey);
            String strPrivateKey = BlockchainDataManager.privateKeyToString(privateKey);

            BlockchainDataManager.stringToPrivateKey(null);
            BlockchainDataManager.stringToPrivateKey(inputForPrivateKey);

            BlockchainDataManager.stringToPrivateKey(strPrivateKey.replace("A", "b"));

        });

    }
}
