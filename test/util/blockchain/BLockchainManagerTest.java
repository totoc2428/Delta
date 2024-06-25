package util.blockchain;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.junit.Before;
import org.junit.Test;

import exception.system.SystemException;
import exception.system.util.blockchain.GeneratePrivateKeyFromStringSystemException;
import exception.system.util.blockchain.GeneratePublicKeyWithPrivateKeySystemException;
import exception.system.util.blockchain.PrivateKeyToSavedFormatSystemException;
import exception.system.util.blockchain.PublicKeyToSavedFormatSystemException;
import exception.system.util.blockchain.SavedFormatToPrivateKeySystemException;
import exception.system.util.blockchain.SavedFormatToPublicKeySystemException;
import util.primary.Primary;

public class BLockchainManagerTest {
    @Before
    public void initBlockchainManagerTest() {
        try {
            Primary.load();
        } catch (SystemException e) {
            e.show();
        }
    }

    @Test
    public void testGeneratePrivateKeyFromString() {
        assertDoesNotThrow(() -> {
            String str = "truc";

            PrivateKey privateKey = BlockchainManager.generatePrivateKeyFromString(str);
            assertNotNull(privateKey);
        });

    }

    @Test
    public void testGeneratePrivateKeyFromStringException() {
        assertThrows(GeneratePrivateKeyFromStringSystemException.class, () -> {
            BlockchainManager.generatePrivateKeyFromString(null);
        });

        assertThrows(GeneratePrivateKeyFromStringSystemException.class, () -> {
            BlockchainManager.generatePrivateKeyFromString("");
        });

        assertThrows(GeneratePrivateKeyFromStringSystemException.class, () -> {
            BlockchainManager.generatePrivateKeyFromString("   ");
        });
    }

    @Test
    public void testGeneratePublicKeyWithPrivateKey() {

        assertDoesNotThrow(() -> {

            String inputForPrivateKey = "test_input_for_private_key";
            PrivateKey privateKey = BlockchainManager.generatePrivateKeyFromString(inputForPrivateKey);

            PublicKey publicKey = BlockchainManager.generatePublicKeyWithPrivateKey(privateKey);
            PublicKey publicKey2 = BlockchainManager.generatePublicKeyWithPrivateKey(privateKey);

            assertNotNull(publicKey);
            assertNotNull(publicKey2);

            assertEquals(publicKey, publicKey2);

        });
    }

    @Test
    public void testGeneratePublicKeyWithPrivateKeyException() {

        assertThrows(GeneratePublicKeyWithPrivateKeySystemException.class, () -> {
            BlockchainManager.generatePublicKeyWithPrivateKey(null);
        });
    }

    @Test
    public void testPrivateKeyToSavedFormat() {

        assertDoesNotThrow(() -> {
            String inputForPrivateKey = "test_input_for_private_key";
            PrivateKey privateKey = BlockchainManager.generatePrivateKeyFromString(inputForPrivateKey);

            String strPrivateKey = BlockchainManager.privateKeyToSavedFormat(privateKey);
            String strPrivateKey2 = BlockchainManager.privateKeyToSavedFormat(privateKey);

            assertNotNull(strPrivateKey);
            assertNotNull(strPrivateKey2);

            assertEquals(strPrivateKey, strPrivateKey2);
        });
    }

    @Test
    public void testPrivateKeyToSavedFormatException() {
        assertThrows(PrivateKeyToSavedFormatSystemException.class, () -> {
            BlockchainManager.privateKeyToSavedFormat(null);
        });
    }

    @Test
    public void testSavedFormatToPrivateKey() {
        assertDoesNotThrow(() -> {
            String inputForPrivateKey = "test_input_for_private_key";
            PrivateKey privateKey = BlockchainManager.generatePrivateKeyFromString(inputForPrivateKey);

            String strPrivateKey = BlockchainManager.privateKeyToSavedFormat(privateKey);

            PrivateKey privateKeyResult = BlockchainManager.savedFormatToPrivateKey(strPrivateKey);

            assertNotNull(privateKeyResult);

            assertEquals(privateKey, privateKeyResult);
        });
    }

    @Test
    public void testSavedFormatToPrivateKeyException() {

        assertThrows(SavedFormatToPrivateKeySystemException.class, () -> {
            BlockchainManager.savedFormatToPrivateKey(null);
        });

        assertThrows(SavedFormatToPrivateKeySystemException.class, () -> {
            BlockchainManager.savedFormatToPrivateKey("");
        });

        assertThrows(SavedFormatToPrivateKeySystemException.class, () -> {
            BlockchainManager.savedFormatToPrivateKey("  ");
        });

        assertThrows(SavedFormatToPrivateKeySystemException.class, () -> {
            BlockchainManager.savedFormatToPrivateKey("invalid_format");
        });
    }

    @Test
    public void testPublicKeyToSavedFormat() {
        assertDoesNotThrow(() -> {
            String inputForPrivateKey = "test_input_for_private_key";

            PrivateKey privateKey = BlockchainManager.generatePrivateKeyFromString(inputForPrivateKey);

            PublicKey publicKey = BlockchainManager.generatePublicKeyWithPrivateKey(privateKey);

            String publicKeyResult1 = BlockchainManager.publicKeyToSavedFormat(publicKey);
            String publicKeyResult2 = BlockchainManager.publicKeyToSavedFormat(publicKey);

            assertNotNull(publicKeyResult1);
            assertNotNull(publicKeyResult2);

            assertEquals(publicKeyResult1, publicKeyResult2);

        });
    }

    @Test
    public void testPublicKeyToSavedFormatException() {
        assertThrows(PublicKeyToSavedFormatSystemException.class, () -> {
            BlockchainManager.publicKeyToSavedFormat(null);
        });
    }

    @Test
    public void testSavedFormatToPublicKey() {
        assertDoesNotThrow(() -> {
            String inputForPrivateKey = "test_input_for_private_key";

            PrivateKey privateKey = BlockchainManager.generatePrivateKeyFromString(inputForPrivateKey);

            PublicKey publicKey = BlockchainManager.generatePublicKeyWithPrivateKey(privateKey);

            String publicKeyInString = BlockchainManager.publicKeyToSavedFormat(publicKey);

            PublicKey publicKeyResult = BlockchainManager.savedFormatToPublicKey(publicKeyInString);

            assertNotNull(publicKeyResult);
            assertEquals(publicKey, publicKeyResult);
        });
    }

    @Test
    public void testSavedFormatToPublicKeyException() {
        assertThrows(SavedFormatToPublicKeySystemException.class, () -> {
            BlockchainManager.savedFormatToPublicKey(null);
        });

        assertThrows(SavedFormatToPublicKeySystemException.class, () -> {
            BlockchainManager.savedFormatToPublicKey("");
        });

        assertThrows(SavedFormatToPublicKeySystemException.class, () -> {
            BlockchainManager.savedFormatToPublicKey("   ");
        });

        assertThrows(SavedFormatToPublicKeySystemException.class, () -> {
            BlockchainManager.savedFormatToPublicKey("invalid_format");
        });
    }

}