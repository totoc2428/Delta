package util.security;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.junit.Before;
import org.junit.Test;

import exception.system.util.primary.PrimaryLoadException;
import exception.system.util.security.DecryptSecurityManagerSystemException;
import exception.system.util.security.EncryptSecurityManagerSystemException;
import exception.system.util.security.SignSecurityManagerSystemException;
import exception.system.util.security.VerifySignatureSecurityManagerSystemException;
import util.blockchain.BlockchainManager;
import util.primary.Primary;

public class SecurityManagerTest {
    @Before
    public void initSecurityManagerTest() {
        try {
            Primary.load();
        } catch (PrimaryLoadException e) {
            e.show();
        }
    }

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

            assertThrows(DecryptSecurityManagerSystemException.class, () -> {
                SecurityManager.decrypt(null, privateKey);
            });

            assertThrows(EncryptSecurityManagerSystemException.class, () -> {
                SecurityManager.encrypt(inputForPrivateKey, null);
            });

            assertThrows(DecryptSecurityManagerSystemException.class, () -> {
                SecurityManager.decrypt(null, null);
            });

            assertThrows(EncryptSecurityManagerSystemException.class, () -> {
                SecurityManager.encrypt(null, null);
            });

            assertThrows(DecryptSecurityManagerSystemException.class, () -> {
                String eResult = SecurityManager.encrypt(inputForPrivateKey, publicKey);
                SecurityManager.decrypt(eResult, privateKey2);
            });

            assertThrows(DecryptSecurityManagerSystemException.class, () -> {
                SecurityManager.decrypt(inputForPrivateKey, privateKey);
            });
        });

    }

    @Test
    public void testSignAndVerifySignature() {

        assertDoesNotThrow(() -> {
            String inputForPrivateKey = "test_input_for_private_key";

            PrivateKey privateKey = BlockchainManager.generatePrivateKeyFromString(inputForPrivateKey);
            PublicKey publicKey = BlockchainManager.generatePublicKeyWithPrivateKey(privateKey);
            PrivateKey privateKey2 = BlockchainManager
                    .generatePrivateKeyFromString(inputForPrivateKey + inputForPrivateKey);
            PublicKey publicKey2 = BlockchainManager.generatePublicKeyWithPrivateKey(privateKey2);

            String signature = SecurityManager.sign(inputForPrivateKey, privateKey);
            String signature2 = SecurityManager.sign(inputForPrivateKey, privateKey2);

            assertNotNull(signature);
            assertNotNull(signature2);

            assertNotEquals(signature, signature2);

            assertTrue(SecurityManager.verifySignature(signature, inputForPrivateKey, publicKey));
            assertTrue(SecurityManager.verifySignature(signature2, inputForPrivateKey, publicKey2));

            assertFalse(SecurityManager.verifySignature(signature, inputForPrivateKey, publicKey2));
            assertFalse(SecurityManager.verifySignature(signature2, inputForPrivateKey, publicKey));

        });
    }

    @Test
    public void testSignAndVerifySignatureException() {
        assertDoesNotThrow(() -> {
            String inputForPrivateKey = "test_input_for_private_key";
            PrivateKey privateKey = BlockchainManager.generatePrivateKeyFromString(inputForPrivateKey);

            assertThrows(SignSecurityManagerSystemException.class, () -> {
                SecurityManager.sign(null, null);
            });

            assertThrows(SignSecurityManagerSystemException.class, () -> {
                SecurityManager.sign(inputForPrivateKey, null);
            });

            assertThrows(SignSecurityManagerSystemException.class, () -> {
                SecurityManager.sign(null, privateKey);
            });

            assertThrows(VerifySignatureSecurityManagerSystemException.class, () -> {
                SecurityManager.verifySignature(null, null, null);
            });

            assertThrows(VerifySignatureSecurityManagerSystemException.class, () -> {
                SecurityManager.verifySignature(null, inputForPrivateKey, null);
            });
        });
    }

}
