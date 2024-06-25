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
import exception.system.util.blockchain.GeneratePublicKeyWithPrivateKeyException;
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
    public void testGetPublicKeyFromPrivateKey() {
        String inputForPrivateKey = "test_input_for_private_key";

        assertDoesNotThrow(() -> {
            PrivateKey privateKey = BlockchainManager.generatePrivateKeyFromString(inputForPrivateKey);

            PublicKey publicKey = BlockchainManager.generatePublicKeyWithPrivateKey(privateKey);
            PublicKey publicKey2 = BlockchainManager.generatePublicKeyWithPrivateKey(privateKey);

            assertNotNull(publicKey);
            assertNotNull(publicKey2);

            assertEquals(publicKey, publicKey2);

        });
    }

    @Test
    public void testGetPublicKeyFromPrivateKeyException() {

        assertThrows(GeneratePublicKeyWithPrivateKeyException.class, () -> {
            BlockchainManager.generatePublicKeyWithPrivateKey(null);
        });
    }
}