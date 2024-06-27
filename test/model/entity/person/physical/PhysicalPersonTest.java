package model.entity.person.physical;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import exception.system.util.primary.PrimaryLoadException;
import io.jsonwebtoken.lang.Arrays;
import util.blockchain.BlockchainManager;
import util.primary.Primary;

public class PhysicalPersonTest {

    @Before
    public void initChainObjectTest() {
        try {
            Primary.load();
        } catch (PrimaryLoadException e) {
            e.show();
        }
    }

    @Test
    public void testPerson() {
        assertDoesNotThrow(() -> {
            String privateKeyPhrase = "pk_phrase";
            LocalDate birthDate = LocalDate.now();
            boolean isVerified = false;
            String[] names = new String[] { "name1", "name2" };

            PrivateKey privateKey = BlockchainManager.generatePrivateKeyFromString(privateKeyPhrase);
            PublicKey publicKey = BlockchainManager.generatePublicKeyWithPrivateKey(privateKey);

            PhysicalPerson physicalPerson = new PhysicalPerson(privateKey, publicKey, privateKeyPhrase, birthDate,
                    isVerified,
                    Arrays.asList(names));

            assertNotNull(physicalPerson);
        });
    }

    @Test
    public void testGetPerson() {
        assertDoesNotThrow(() -> {
            String privateKeyPhrase = "pk_phrase";
            LocalDate birthDate = LocalDate.now();
            boolean isVerified = false;
            String[] names = new String[] { "name1", "name2" };

            PrivateKey privateKey = BlockchainManager.generatePrivateKeyFromString(privateKeyPhrase);
            PublicKey publicKey = BlockchainManager.generatePublicKeyWithPrivateKey(privateKey);

            PhysicalPerson physicalPerson = new PhysicalPerson(privateKey, publicKey, privateKeyPhrase, birthDate,
                    isVerified,
                    Arrays.asList(names));

            assertNotNull(physicalPerson);
        });
    }
}
