package test.model.dao.blockchain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.security.PrivateKey;
import java.util.Properties;

import org.junit.Test;
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

        PrivateKey    privateKey = BlockchainDataMaganager.generatePrivateKeyFromString(inputForPrivateKey);
        PrivateKey    privateKey2 = BlockchainDataMaganager.generatePrivateKeyFromString(inputForPrivateKey);
        
        assertEquals(privateKey, privateKey2);

        assertThrows(java.lang.NullPointerException.class,() -> {
            BlockchainDataMaganager.generatePrivateKeyFromString(null);
        });
    }
}
