package util.blockchain;

import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Properties;

import exception.system.util.blockchain.BlockchainManagerLoadException;
import exception.system.util.blockchain.GeneratePrivateKeyFromStringSystemException;
import exception.system.util.blockchain.GeneratePublicKeyWithPrivateKeyException;
import exception.system.util.data.PropertiesReadingSystemException;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import util.data.DataManager;
import util.primary.Primary;

public abstract class BlockchainManager {
    private static Properties initBlockchainProperties;

    private static String signatureAlgorithm;

    private static String digestAlgorithm;

    private static String keyAlgorithm;
    private static String KeyRandomGeneratorInstance;
    private static int keySize;
    private static int keyExponant;

    private static int encryptorKeySize;
    private static String encryptorAlgorithm;
    private static String savedKeyEncryptorSpace;

    /* -Attribut */
    /* --GETTER */
    public static String getSavedKeyEncryptorSpace() {
        return savedKeyEncryptorSpace;
    }

    /* -LOADER */
    public static void load() throws BlockchainManagerLoadException {
        try {
            initBlockchainProperties = DataManager.readAFile(Primary.getBlockchainManagerInitPath());

            signatureAlgorithm = initBlockchainProperties.getProperty("SIGNATURE_ALGORITHM");
            digestAlgorithm = initBlockchainProperties.getProperty("DIGEST_ALGORITHM");

            keyAlgorithm = initBlockchainProperties.getProperty("KEY_ALGORITHM");
            KeyRandomGeneratorInstance = initBlockchainProperties.getProperty("KEY_RANDOM_GENERATOR_INSTANCE");
            keySize = Integer.parseInt(initBlockchainProperties.getProperty("KEY_SIZE"));
            keyExponant = Integer.parseInt(initBlockchainProperties.getProperty("KEY_EXPONANT"));

            encryptorKeySize = Integer.parseInt(initBlockchainProperties.getProperty("ENCRYPTOR_KEY_SIZE"));
            encryptorAlgorithm = initBlockchainProperties.getProperty("ENCRYPTOR_ALGORITHM");
            savedKeyEncryptorSpace = initBlockchainProperties.getProperty("SAVED_KEY_ENCRYPTOR_SPACE");

        } catch (PropertiesReadingSystemException e) {
            e.show();

            throw new BlockchainManagerLoadException();
        }
    }

    /* -INIT */
    /* --GETTER */
    public static Properties getInit() {
        return initBlockchainProperties;
    }

    /* -GENERATOR */
    /* --PRIVATE_KEY */
    public static PrivateKey generatePrivateKeyFromString(String input)
            throws GeneratePrivateKeyFromStringSystemException {
        if (input != null && (!input.isEmpty() && !input.isBlank())) {
            try {
                SecureRandom random = SecureRandom.getInstance(KeyRandomGeneratorInstance);
                random.setSeed(input.getBytes());

                KeyPairGenerator keyGen = KeyPairGenerator.getInstance(keyAlgorithm);
                keyGen.initialize(keySize, random);

                KeyPair keyPair = keyGen.generateKeyPair();

                return keyPair.getPrivate();

            } catch (Exception e) {
                throw new GeneratePrivateKeyFromStringSystemException();
            }
        } else {
            throw new GeneratePrivateKeyFromStringSystemException();

        }

    }

    /* --PUBLIC_KEY */
    public static PublicKey generatePublicKeyWithPrivateKey(PrivateKey privateKey)
            throws GeneratePublicKeyWithPrivateKeyException {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
            RSAPrivateKeySpec privateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);

            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(privateKeySpec.getModulus(),
                    BigInteger.valueOf(keyExponant));

            return keyFactory.generatePublic(publicKeySpec);
        } catch (Exception e) {
            throw new GeneratePublicKeyWithPrivateKeyException();
        }
    }

    /* -SAVING_FORMAT */
    /* --PRIVATE_KEY */
    public static String privateKeyToSavedFormat(PrivateKey privateKey) {
        return null;
    }
}
