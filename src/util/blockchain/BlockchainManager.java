package util.blockchain;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Properties;

import exception.system.util.blockchain.BlockchainManagerLoadException;
import exception.system.util.blockchain.GeneratePrivateKeyFromStringSystemException;
import exception.system.util.blockchain.GeneratePublicKeyWithPrivateKeySystemException;
import exception.system.util.blockchain.PrivateKeyToSavedFormatSystemException;
import exception.system.util.blockchain.PublicKeyToSavedFormatSystemException;
import exception.system.util.blockchain.SavedFormatToPrivateKeySystemException;
import exception.system.util.blockchain.SavedFormatToPublicKeySystemException;
import exception.system.util.data.PropertiesReadingSystemException;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import util.data.DataManager;
import util.message.done.DoneSystemMessage;
import util.primary.Primary;

public abstract class BlockchainManager {
    private static Properties initBlockchainProperties;

    private static String keyAlgorithm;
    private static String KeyRandomGeneratorInstance;
    private static int keySize;
    private static int keyExponant;

    /* -LOADER */
    public static void load() throws BlockchainManagerLoadException {
        try {
            initBlockchainProperties = DataManager.readAFile(Primary.getBlockchainManagerInitPath());

            keyAlgorithm = initBlockchainProperties.getProperty("KEY_ALGORITHM");
            KeyRandomGeneratorInstance = initBlockchainProperties.getProperty("KEY_RANDOM_GENERATOR_INSTANCE");
            keySize = Integer.parseInt(initBlockchainProperties.getProperty("KEY_SIZE"));
            keyExponant = Integer.parseInt(initBlockchainProperties.getProperty("KEY_EXPONANT"));

            new DoneSystemMessage("BlockchainManagerLoad", 1).show();

        } catch (PropertiesReadingSystemException e) {
            e.show();

            throw new BlockchainManagerLoadException();
        }
    }

    /* -KEY */
    /* --Algorithm */
    public static String getKeyAlgorithm() {
        return keyAlgorithm;
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

                new DoneSystemMessage("BlockchainManagerGeneratePrivateKeyFromString", 1).show();
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
            throws GeneratePublicKeyWithPrivateKeySystemException {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
            RSAPrivateKeySpec privateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);

            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(privateKeySpec.getModulus(),
                    BigInteger.valueOf(keyExponant));

            new DoneSystemMessage("BlockchainManagerGeneratePublicKeyWithPrivateKey", 1).show();
            return keyFactory.generatePublic(publicKeySpec);
        } catch (Exception e) {
            throw new GeneratePublicKeyWithPrivateKeySystemException();
        }
    }

    /* -SAVING_FORMAT */
    /* --PRIVATE_KEY */
    /* ---To_SAVED_FORMAT */
    public static String privateKeyToSavedFormat(PrivateKey privateKey) throws PrivateKeyToSavedFormatSystemException {
        if (privateKey != null) {
            byte[] privateKeyBytes = privateKey.getEncoded();
            new DoneSystemMessage("BlockchainManagerPrivateKeyToSavedFormat", 1).show();

            return Base64.getEncoder().encodeToString(privateKeyBytes);
        } else {
            throw new PrivateKeyToSavedFormatSystemException();
        }
    }

    /* ---TO_PRIVATE_KEY */
    public static PrivateKey savedFormatToPrivateKey(String privateKeyInSavedFormat)
            throws SavedFormatToPrivateKeySystemException {
        if (privateKeyInSavedFormat != null
                && (!privateKeyInSavedFormat.isEmpty() && !privateKeyInSavedFormat.isBlank())) {
            try {
                byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyInSavedFormat);
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
                KeyFactory keyFactory;
                keyFactory = KeyFactory.getInstance(keyAlgorithm);

                new DoneSystemMessage("BlockchainManagerSavedFormatToPrivateKey", 1).show();

                return keyFactory.generatePrivate(keySpec);
            } catch (IllegalArgumentException | InvalidKeySpecException | NoSuchAlgorithmException e) {
                throw new SavedFormatToPrivateKeySystemException();
            }
        } else {
            throw new SavedFormatToPrivateKeySystemException();
        }
    }

    /* --PUBLIC_KEY */
    /* ---To_SAVED_FORMAT */
    public static String publicKeyToSavedFormat(PublicKey publicKey) throws PublicKeyToSavedFormatSystemException {
        if (publicKey != null) {
            byte[] publicKeyBytes = publicKey.getEncoded();

            new DoneSystemMessage("BlockchainManagerPublicKeyToSavedFormat ", 1).show();

            return Base64.getEncoder().encodeToString(publicKeyBytes);
        } else {
            throw new PublicKeyToSavedFormatSystemException();
        }
    }

    /* ---To_PUBLIC_KEY */
    public static PublicKey savedFormatToPublicKey(String publicKeyInSavedFormat)
            throws SavedFormatToPublicKeySystemException {
        if (publicKeyInSavedFormat != null
                && (!publicKeyInSavedFormat.isBlank() && !publicKeyInSavedFormat.isEmpty())) {
            try {
                byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyInSavedFormat);
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
                KeyFactory keyFactory;
                keyFactory = KeyFactory.getInstance(keyAlgorithm);

                new DoneSystemMessage("BlockchainManagerSavedFormatToPublicKey ", 1).show();

                return keyFactory.generatePublic(keySpec);
            } catch (Exception e) {
                throw new SavedFormatToPublicKeySystemException();
            }
        } else {
            throw new SavedFormatToPublicKeySystemException();

        }
    }
}
