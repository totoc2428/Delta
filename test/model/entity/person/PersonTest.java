package model.entity.person;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import exception.system.util.primary.PrimaryLoadException;
import util.blockchain.BlockchainManager;
import util.primary.Primary;

public class PersonTest {
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

            PrivateKey privateKey = BlockchainManager.generatePrivateKeyFromString(privateKeyPhrase);
            PublicKey publicKey = BlockchainManager.generatePublicKeyWithPrivateKey(privateKey);

            Person person = new Person(privateKey, publicKey, privateKeyPhrase, birthDate, isVerified);

            assertNotNull(person);
        });
    }

    @Test
    public void testGetName() {
        assertDoesNotThrow(() -> {
            String privateKeyPhrase = "pk_phrase";
            LocalDate birthDate = LocalDate.now();
            boolean isVerified = false;

            PrivateKey privateKey = BlockchainManager.generatePrivateKeyFromString(privateKeyPhrase);
            PublicKey publicKey = BlockchainManager.generatePublicKeyWithPrivateKey(privateKey);

            Person person = new Person(privateKey, publicKey, privateKeyPhrase, birthDate, isVerified);

            assertNotNull(person);

            assertNotNull(person.getName());
            assertEquals(privateKeyPhrase, person.getName());
        });
    }

    @Test
    public void testGetPrivateKey() {
        assertDoesNotThrow(() -> {
            String privateKeyPhrase = "pk_phrase";
            LocalDate birthDate = LocalDate.now();
            boolean isVerified = false;

            PrivateKey privateKey = BlockchainManager.generatePrivateKeyFromString(privateKeyPhrase);
            PublicKey publicKey = BlockchainManager.generatePublicKeyWithPrivateKey(privateKey);

            Person person = new Person(privateKey, publicKey, privateKeyPhrase, birthDate, isVerified);

            assertNotNull(person);

            assertNotNull(person.getBrithDate());
            assertEquals(birthDate, person.getBrithDate());
        });
    }

    @Test
    public void testIsVerified() {
        assertDoesNotThrow(() -> {
            String privateKeyPhrase = "pk_phrase";
            LocalDate birthDate = LocalDate.now();
            boolean isVerified = false;

            PrivateKey privateKey = BlockchainManager.generatePrivateKeyFromString(privateKeyPhrase);
            PublicKey publicKey = BlockchainManager.generatePublicKeyWithPrivateKey(privateKey);

            Person person = new Person(privateKey, publicKey, privateKeyPhrase, birthDate, isVerified);

            assertNotNull(person);

            assertNotNull(person.isVerified());
            assertEquals(isVerified, person.isVerified());
        });
    }
}
