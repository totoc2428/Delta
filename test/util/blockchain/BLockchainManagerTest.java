package util.blockchain;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.security.PrivateKey;

import org.junit.Before;
import org.junit.Test;

import exception.system.SystemException;
import exception.system.util.blockchain.GeneratePrivateKeyFromStringSystemException;
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
}