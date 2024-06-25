package util.blockchain;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.security.PrivateKey;

import org.junit.Before;
import org.junit.Test;

import exception.system.util.blockchain.BlockchainManagerLoadException;

public class BLockchainManagerTest {
    @Before
    public void initBlockchainManagerTest() {
        try {
            BlockchainManager.load();
        } catch (BlockchainManagerLoadException e) {
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
}