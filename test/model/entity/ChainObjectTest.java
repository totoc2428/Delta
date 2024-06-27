package model.entity;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.junit.Before;
import org.junit.Test;

import exception.system.util.primary.PrimaryLoadException;
import util.blockchain.BlockchainManager;
import util.primary.Primary;

public class ChainObjectTest {
    @Before
    public void initChainObjectTest() {
        try {
            Primary.load();
        } catch (PrimaryLoadException e) {
            e.show();
        }
    }

    @Test
    public void testChainObject() {
        assertDoesNotThrow(() -> {
            String privateKeyPhrase = "pk_phrase";
            PrivateKey privateKey = BlockchainManager.generatePrivateKeyFromString(privateKeyPhrase);
            PublicKey publicKey = BlockchainManager.generatePublicKeyWithPrivateKey(privateKey);

            ChainObject chainObject = new ChainObject(privateKey, publicKey);

            assertNotNull(chainObject);
        });
    }

    @Test
    public void testGetPrivateKey() {
        assertDoesNotThrow(() -> {
            String privateKeyPhrase = "pk_phrase";
            PrivateKey privateKey = BlockchainManager.generatePrivateKeyFromString(privateKeyPhrase);
            PublicKey publicKey = BlockchainManager.generatePublicKeyWithPrivateKey(privateKey);

            ChainObject chainObject = new ChainObject(privateKey, publicKey);
            assertNotNull(chainObject);

            assertNotNull(chainObject.getPrivateKey());
            assertEquals(privateKey, chainObject.getPrivateKey());
        });
    }

    @Test
    public void testGetPublicKey() {
        assertDoesNotThrow(() -> {
            String privateKeyPhrase = "pk_phrase";
            PrivateKey privateKey = BlockchainManager.generatePrivateKeyFromString(privateKeyPhrase);
            PublicKey publicKey = BlockchainManager.generatePublicKeyWithPrivateKey(privateKey);

            ChainObject chainObject = new ChainObject(privateKey, publicKey);
            assertNotNull(chainObject);

            assertNotNull(chainObject.getPublicKey());
            assertEquals(publicKey, chainObject.getPublicKey());
        });
    }
}
