package test.model.dto.blockchain.chainobject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.junit.Test;

import main.model.dao.blockchain.BlockchainDataManager;
import main.model.dto.blockchain.chainobject.ChainObject;
import main.model.dto.blockchain.chainobject.person.physical.PhysicalPerson;

public class ChainObjectTest {

    @Test
    public void testChainObjectConstructor() {
        assertDoesNotThrow(() -> {
            String imputForPrivateKey = "input_for_private_key";
            PrivateKey privateKey = BlockchainDataManager.generatePrivateKeyFromString(imputForPrivateKey);
            PublicKey publicKey = BlockchainDataManager.getPublicKeyFromPrivateKey(privateKey);

            ChainObject chainObject = (ChainObject) new PhysicalPerson(privateKey, publicKey, null, null, false, null,
                    null);

            assertNotNull(chainObject);

            assertNotNull(chainObject.getPrivateKey());
            assertNotNull(chainObject.getPublicKey());

            assertEquals(privateKey, chainObject.getPrivateKey());
            assertEquals(publicKey, chainObject.getPublicKey());

        });

    }

    @Test
    public void testChainObjectToString() {
        assertDoesNotThrow(() -> {
            String imputForPrivateKey = "input_for_private_key";
            PrivateKey privateKey = BlockchainDataManager.generatePrivateKeyFromString(imputForPrivateKey);
            PublicKey publicKey = BlockchainDataManager.getPublicKeyFromPrivateKey(privateKey);

            ChainObject chainObject = (ChainObject) new PhysicalPerson(privateKey, publicKey, null, null, false, null,
                    null);

            assertNotNull(chainObject);

            String exceptedOutput = "ChainObject [privateKey(encrypted)=" + BlockchainDataManager
                    .encryptWithPublicKey(BlockchainDataManager.privateKeyToString(privateKey), publicKey)
                    + ", publicKey=" + publicKey + "]";

            assertEquals(exceptedOutput, chainObject.toString());
        });
    }
}
