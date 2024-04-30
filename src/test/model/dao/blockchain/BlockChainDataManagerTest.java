package test.model.dao.blockchain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Properties;

import org.junit.Test;

import exception.model.dao.createprivateKey.BlockchainDataManagerPrivateKeyBuildException;
import main.model.dao.DataManager;
import main.model.dao.blockchain.BlockchainDataMaganager;

public class BlockchainDataManagerTest {
    
    @Test
    public void testBlockChainPropertiesIsNotNull(){
        assertNotNull(BlockchainDataMaganager.BLOCKCHAIN_PROPERTIES);
        
        assertNotNull(BlockchainDataMaganager.KEY_ALGORITHM);
        assertNotNull(BlockchainDataMaganager.DIGEST_ALGORITHM);

        assertNotNull(BlockchainDataMaganager.ENCRYPTOR_ALGORITHM);
        assertNotNull(BlockchainDataMaganager.AES_KEY_SIZE);
        
        assertNotNull(BlockchainDataMaganager.getSrcPath());
    }

    @Test
    public void testInitPropertiesValue(){
        
        Properties blockchainProperties = DataManager.read(DataManager.INIT_PROPERTIES.getProperty("BLOCKCHAIN_PROPERTIES"));
        
        String keyAlgorithm = blockchainProperties.getProperty("KEY_ALGORITHM");
        String digestAlgorithm = blockchainProperties.getProperty("DIGEST_ALGORITHM");

        String encryptorAlgorithm = blockchainProperties.getProperty("ENCRYPTOR_ALGORITHM");
        int aesKeySize = Integer.parseInt(blockchainProperties.getProperty("AES_KEY_SIZE"));

        String srcPath = blockchainProperties.getProperty("srcPath");

        assertEquals(keyAlgorithm, BlockchainDataMaganager.KEY_ALGORITHM);
        assertEquals(digestAlgorithm, BlockchainDataMaganager.DIGEST_ALGORITHM);
        
        assertEquals(encryptorAlgorithm, BlockchainDataMaganager.ENCRYPTOR_ALGORITHM);
        assertEquals(aesKeySize, BlockchainDataMaganager.AES_KEY_SIZE);

        assertEquals(srcPath, BlockchainDataMaganager.getSrcPath());
    }

    @Test
    public void testGeneratePrivateKeyFromString(){
        String inputForPrivateKey = "test_input_for_private_key";

        assertDoesNotThrow( () ->{
            PrivateKey privateKey = BlockchainDataMaganager.generatePrivateKeyFromString(inputForPrivateKey);
            PrivateKey privateKey2 = BlockchainDataMaganager.generatePrivateKeyFromString(inputForPrivateKey);
            
            assertEquals(privateKey, privateKey2);
        });

        assertThrows( BlockchainDataManagerPrivateKeyBuildException.class,() -> {
            BlockchainDataMaganager.generatePrivateKeyFromString(null);
        });
    }

    @Test
    public void testGetPublicKeyFromPrivateKey(){
        String inputForPrivateKey = "test_input_for_private_key";

        assertDoesNotThrow(() -> {
            PrivateKey privateKey = BlockchainDataMaganager.generatePrivateKeyFromString(inputForPrivateKey);

            PublicKey publicKey = BlockchainDataMaganager.getPublicKeyFromPrivateKey(privateKey);
            PublicKey publicKey2 = BlockchainDataMaganager.getPublicKeyFromPrivateKey(privateKey);

            assertNotNull(publicKey);
            assertEquals(publicKey, publicKey2);

        });

        
    }


}
