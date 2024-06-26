package util.security;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.junit.Test;

import exception.system.util.security.EncryptSecurityManagerSystemException;
import util.blockchain.BlockchainManager;

public class SecurityManagerTest {

    @Test
    public void testEncryptAndDecrypt() {
        String inputForPrivateKey = "test_input_for_private_key";

        assertDoesNotThrow(() -> {

            PrivateKey privateKey = BlockchainManager.generatePrivateKeyFromString(inputForPrivateKey);
            PublicKey publicKey = BlockchainManager.generatePublicKeyWithPrivateKey(privateKey);

            String eResult = SecurityManager.encrypt(inputForPrivateKey, publicKey);

            assertNotNull(eResult);
            assertFalse(eResult.isBlank());
            assertFalse(eResult.isEmpty());

            String dResult = SecurityManager.decrypt(eResult, privateKey);

            assertNotNull(dResult);
            assertFalse(dResult.isBlank());
            assertFalse(dResult.isEmpty());

            assertEquals(inputForPrivateKey, dResult);

        });
    }

    @Test
    public void testEncryptAndDecryptException() {
        String inputForPrivateKey = "test_input_for_private_key";

        assertDoesNotThrow(() -> {
            PrivateKey privateKey = BlockchainManager.generatePrivateKeyFromString(inputForPrivateKey);
            PrivateKey privateKey2 = BlockchainManager
                    .generatePrivateKeyFromString(inputForPrivateKey + inputForPrivateKey);

            PublicKey publicKey = BlockchainManager.generatePublicKeyWithPrivateKey(privateKey);

            assertThrows(EncryptSecurityManagerSystemException.class, () -> {
                SecurityManager.decrypt(null, privateKey);
            });

            assertThrows(EncryptSecurityManagerSystemException.class, () -> {
                SecurityManager.encrypt(inputForPrivateKey, null);
            });

            assertThrows(EncryptSecurityManagerSystemException.class, () -> {
                SecurityManager.decrypt(null, null);
            });

            assertThrows(EncryptSecurityManagerSystemException.class, () -> {
                SecurityManager.encrypt(null, null);
            });

            assertThrows(EncryptSecurityManagerSystemException.class, () -> {
                String eResult = SecurityManager.encrypt(inputForPrivateKey, publicKey);
                SecurityManager.decrypt(eResult, privateKey2);
            });

            assertThrows(EncryptSecurityManagerSystemException.class, () -> {
                SecurityManager.decrypt(inputForPrivateKey, privateKey);
            });
        });

    }
}
