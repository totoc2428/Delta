package test.model.dao.blockchain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.Properties;

import org.junit.Test;

import exception.model.dao.blockchain.publickey.getfromprivate.GetFromPrivatePublicKeyBlockchainDataManagerException;
import main.model.dao.DataManager;
import main.model.dao.blockchain.BlockchainDataManager;

public class BlockchainDataManagerTest {

    @Test
    public void testBlockChainPropertiesIsNotNull() {
        assertNotNull(BlockchainDataManager.BLOCKCHAIN_PROPERTIES);

        assertNotNull(BlockchainDataManager.KEY_ALGORITHM);
        assertNotNull(BlockchainDataManager.KEY_RANDOM_GENERATOR_INSTANCE);
        assertNotNull(BlockchainDataManager.KEY_EXPONANT);

        assertNotNull(BlockchainDataManager.DIGEST_ALGORITHM);

        assertNotNull(BlockchainDataManager.SAVED_KEY_ENCRYPTOR_SPACE);

        assertNotNull(BlockchainDataManager.ENCRYPTOR_ALGORITHM);
        assertNotNull(BlockchainDataManager.ENCRYPTOR_KEY_SIZE);

        assertNotNull(BlockchainDataManager.getSrcPath());
    }

    @Test
    public void testInitPropertiesValue() {

        Properties blockchainProperties = DataManager
                .read(DataManager.INIT_PROPERTIES.getProperty("BLOCKCHAIN_PROPERTIES"));

        String keyAlgorithm = blockchainProperties.getProperty("KEY_ALGORITHM");
        String keyRandomGeneratorInstance = blockchainProperties.getProperty("KEY_RANDOM_GENERATOR_INSTANCE");
        int keyExponant = Integer.parseInt(blockchainProperties.getProperty("KEY_EXPONANT"));

        String savedKeyEncryptorSpace = blockchainProperties.getProperty("SAVED_KEY_ENCRYPTOR_SPACE");

        String digestAlgorithm = blockchainProperties.getProperty("DIGEST_ALGORITHM");

        String encryptorAlgorithm = blockchainProperties.getProperty("ENCRYPTOR_ALGORITHM");
        int aesKeySize = Integer.parseInt(blockchainProperties.getProperty("ENCRYPTOR_KEY_SIZE"));

        String srcPath = blockchainProperties.getProperty("srcPath");

        assertEquals(keyAlgorithm, BlockchainDataManager.KEY_ALGORITHM);
        assertEquals(keyRandomGeneratorInstance, BlockchainDataManager.KEY_RANDOM_GENERATOR_INSTANCE);
        assertEquals(keyExponant, BlockchainDataManager.KEY_EXPONANT);

        assertEquals(savedKeyEncryptorSpace, BlockchainDataManager.SAVED_KEY_ENCRYPTOR_SPACE);

        assertEquals(digestAlgorithm, BlockchainDataManager.DIGEST_ALGORITHM);

        assertEquals(encryptorAlgorithm, BlockchainDataManager.ENCRYPTOR_ALGORITHM);
        assertEquals(aesKeySize, BlockchainDataManager.ENCRYPTOR_KEY_SIZE);

        assertEquals(srcPath, BlockchainDataManager.getSrcPath());
    }

    @Test
    public void testGeneratePrivateKeyFromString() {
        String inputForPrivateKey = "test_input_for_private_key";

        assertDoesNotThrow(() -> {
            PrivateKey privateKey1 = BlockchainDataManager.generatePrivateKeyFromString(inputForPrivateKey);
            PrivateKey privateKey2 = BlockchainDataManager.generatePrivateKeyFromString(inputForPrivateKey);
            PrivateKey privateKey3 = BlockchainDataManager
                    .generatePrivateKeyFromString(inputForPrivateKey + inputForPrivateKey);

            assertNotNull(privateKey1);
            assertNotNull(privateKey2);
            assertNotNull(privateKey3);

            // Vérifier que les deux clés sont équivalentes
            assertTrue(privateKey1 instanceof RSAPrivateKey);
            assertTrue(privateKey2 instanceof RSAPrivateKey);
            assertTrue(privateKey3 instanceof RSAPrivateKey);

            assertNotEquals(privateKey3, privateKey1);
            assertNotEquals(privateKey3, privateKey2);

            assertEquals(privateKey1, privateKey2);
        });

        assertThrows(Exception.class, () -> {
            BlockchainDataManager.generatePrivateKeyFromString(null);
        });

        assertThrows(Exception.class, () -> {
            BlockchainDataManager.generatePrivateKeyFromString(" ");
            BlockchainDataManager.generatePrivateKeyFromString("");
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
            assertNotNull(publicKey2);

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

    @Test
    public void testStringToPublicKey() {
        String inputForPrivateKey = "test_input_for_private_key";

        assertDoesNotThrow(() -> {
            PrivateKey privateKey = BlockchainDataManager.generatePrivateKeyFromString(inputForPrivateKey);
            PublicKey publicKey = BlockchainDataManager.getPublicKeyFromPrivateKey(privateKey);

            String strPublicKey = BlockchainDataManager.publicKeyToString(publicKey);

            PublicKey publicKey2 = BlockchainDataManager.stringToPublicKey(strPublicKey);
            PublicKey publicKey3 = BlockchainDataManager.stringToPublicKey(strPublicKey);

            assertNotNull(publicKey2);
            assertNotNull(publicKey3);

            assertEquals(publicKey2, publicKey3);
            assertEquals(publicKey, publicKey);
        });

        assertThrows(Exception.class, () -> {
            PrivateKey privateKey = BlockchainDataManager.generatePrivateKeyFromString(inputForPrivateKey);
            String strPrivateKey = BlockchainDataManager.privateKeyToString(privateKey);

            BlockchainDataManager.stringToPublicKey(null);
            BlockchainDataManager.stringToPublicKey(inputForPrivateKey);

            BlockchainDataManager.stringToPublicKey(strPrivateKey.replace("A", "b"));

        });

    }

    @Test
    public void testEncrypDecrypttWithPublicKey() {
        String inputForPrivateKey = "test_input_for_private_key";

        assertDoesNotThrow(() -> {

            PrivateKey privateKey = BlockchainDataManager.generatePrivateKeyFromString(inputForPrivateKey);
            PublicKey publicKey = BlockchainDataManager.getPublicKeyFromPrivateKey(privateKey);

            String eResult = BlockchainDataManager.encryptWithPublicKey(inputForPrivateKey, publicKey);

            assertNotNull(eResult);
            assertFalse(eResult.isBlank());
            assertFalse(eResult.isEmpty());

            String dResult = BlockchainDataManager.decryptWithPrivateKey(eResult, privateKey);

            assertNotNull(dResult);
            assertFalse(dResult.isBlank());
            assertFalse(dResult.isEmpty());

            assertEquals(inputForPrivateKey, dResult);

        });

        assertThrows(Exception.class, () -> {

            PrivateKey privateKey = BlockchainDataManager.generatePrivateKeyFromString(inputForPrivateKey);
            PrivateKey privateKey2 = BlockchainDataManager
                    .generatePrivateKeyFromString(inputForPrivateKey + inputForPrivateKey);

            PublicKey publicKey = BlockchainDataManager.getPublicKeyFromPrivateKey(privateKey);

            BlockchainDataManager.encryptWithPublicKey(null, null);
            BlockchainDataManager.encryptWithPublicKey(inputForPrivateKey, null);

            BlockchainDataManager.decryptWithPrivateKey(null, null);
            BlockchainDataManager.decryptWithPrivateKey(null, privateKey);
            BlockchainDataManager.decryptWithPrivateKey(inputForPrivateKey, privateKey);

            String eResult = BlockchainDataManager.encryptWithPublicKey(inputForPrivateKey, publicKey);

            BlockchainDataManager.decryptWithPrivateKey(eResult, privateKey2);

        });

    }

    @Test
    public void testSignData() {
        String inputForPrivateKey = "test_input_for_private_key";

        assertDoesNotThrow(() -> {
            PrivateKey privateKey = BlockchainDataManager.generatePrivateKeyFromString(inputForPrivateKey);
            PublicKey publicKey = BlockchainDataManager.getPublicKeyFromPrivateKey(privateKey);
            PrivateKey privateKey2 = BlockchainDataManager
                    .generatePrivateKeyFromString(inputForPrivateKey + inputForPrivateKey);
            PublicKey publicKey2 = BlockchainDataManager.getPublicKeyFromPrivateKey(privateKey2);

            String signature = BlockchainDataManager.signData(privateKey, inputForPrivateKey);
            String signature2 = BlockchainDataManager.signData(privateKey2, inputForPrivateKey);

            assertNotNull(signature);
            assertNotNull(signature2);

            assertNotEquals(signature, signature2);

            assertTrue(BlockchainDataManager.verifySignature(publicKey, inputForPrivateKey, signature));
            assertTrue(BlockchainDataManager.verifySignature(publicKey2, inputForPrivateKey, signature2));

            assertFalse(BlockchainDataManager.verifySignature(publicKey2, inputForPrivateKey, signature));
            assertFalse(BlockchainDataManager.verifySignature(publicKey, inputForPrivateKey, signature2));

        });

        assertThrows(Exception.class, () -> {
            PrivateKey privateKey = BlockchainDataManager.generatePrivateKeyFromString(inputForPrivateKey);

            BlockchainDataManager.signData(null, null);
            BlockchainDataManager.signData(null, inputForPrivateKey);
            BlockchainDataManager.signData(privateKey, null);

            BlockchainDataManager.verifySignature(null, null, null);
            BlockchainDataManager.verifySignature(null, inputForPrivateKey, null);

        });
    }
}
