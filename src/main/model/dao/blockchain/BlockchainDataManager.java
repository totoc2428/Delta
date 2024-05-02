package main.model.dao.blockchain;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import exception.model.dao.blockchain.createprivateKey.BlockchainDataManagerPrivateKeyBuildException;
import exception.model.dao.blockchain.encryptor.EncryptWithEncryptorBlockchainDataManagerException;
import exception.model.dao.blockchain.encryptor.GenerateEncryptorBlockchainDataManagerException;
import exception.model.dao.blockchain.publickey.getfromprivate.GetFromPrivatePublicKeyBlockchainDataManagerException;
import io.jsonwebtoken.security.InvalidKeyException;
import main.model.dao.DataManager;
import main.util.style.TerminalStyle;

public abstract class BlockchainDataManager extends DataManager {

    public final static Properties BLOCKCHAIN_PROPERTIES = DataManager
            .read(INIT_PROPERTIES.getProperty("BLOCKCHAIN_PROPERTIES"));

    public final static String KEY_ALGORITHM = BLOCKCHAIN_PROPERTIES.getProperty(
            "KEY_ALGORITHM");
    public final static String DIGEST_ALGORITHM = BLOCKCHAIN_PROPERTIES
            .getProperty("DIGEST_ALGORITHM");

    // private final static int KEY_SIZE =
    // Integer.parseInt(BLOCKCHAIN_PROPERTIES.getProperty("KEY_SIZE"));

    public final static String ENCRYPTOR_ALGORITHM = BLOCKCHAIN_PROPERTIES.getProperty("ENCRYPTOR_ALGORITHM");
    public final static int AES_KEY_SIZE = Integer.parseInt(BLOCKCHAIN_PROPERTIES.getProperty("AES_KEY_SIZE"));

    private static String srcPath = BLOCKCHAIN_PROPERTIES.getProperty("srcPath");

    /**
     * This method retrieve the publicKey of a privateKey.
     * 
     * [i] If an error occured the method will be return null.
     * 
     * @param privateKey
     * @return a {@link PublicKey} deducted with the privateKey.
     * @throws GetFromPrivatePublicKeyBlockchainDataManagerException
     */
    public static PublicKey getPublicKeyFromPrivateKey(PrivateKey privateKey)
            throws GetFromPrivatePublicKeyBlockchainDataManagerException {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateKeySpec privateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);

            // Créer une spécification de clé publique RSA à partir du module (n) et de
            // l'exposant public (e)
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(privateKeySpec.getModulus(),
                    BigInteger.valueOf(65537)); // Exposant public standard

            // Générer et retourner la clé publique
            return keyFactory.generatePublic(publicKeySpec);
        } catch (Exception e) {
            throw new GetFromPrivatePublicKeyBlockchainDataManagerException();
        }
    }

    /**
     * This method transform a text-privateKey to a PrivateKey type.
     * 
     * [i] If an error occured the method will be return null.
     * 
     * @param privateKeyString the privateKey in a text format. The text need to
     *                         be a result of
     *                         {@link Key#getPublicKeyFromPrivateKey(PrivateKey)}.
     * @return the text privateKey in a {@link PrivateKey} format.
     */
    public static PrivateKey stringToPrivateKey(String privateKeyString) {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory;
        try {
            keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            return keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            TerminalStyle.showError(e.getMessage());
        }
        return null;
    }

    /**
     * This method transform a text-publicKey to a PublicKey type.
     * 
     * @param publicKeyString the PublicKey in a text format. The text need to be a
     *                        result of {@link Key#publicKeyToString(PublicKey)}
     * @return the text publicKey in a {@link PublicKey} format.
     */
    public static PublicKey stringToPublicKey(String publicKeyString) {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyString);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory;
        try {
            keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            return keyFactory.generatePublic(keySpec);
        } catch (InvalidKeySpecException e) {
            TerminalStyle.showError(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            // TODO make execption;
            TerminalStyle.showError(e.getMessage());
        }

        return null;
    }

    /**
     * Transform a publicKey in a readable textformat who can be saved.
     * 
     * @param publicKey the publicKey who you need in a text format.
     * @return the publicKey in a {@link String} format.
     */
    public static String publicKeyToString(PublicKey publicKey) {
        byte[] publicKeyBytes = publicKey.getEncoded();

        return Base64.getEncoder().encodeToString(publicKeyBytes);
    }

    /**
     * Transform a privateKey in a readable textformat who can be saved.
     * 
     * @param privateKey the privateKey who you need in a text format.
     * @return the privateKey in a {@link String} format.
     */
    public static String privateKeyToString(PrivateKey privateKey) {
        byte[] privateKeyBytes = privateKey.getEncoded();

        return Base64.getEncoder().encodeToString(privateKeyBytes);
    }

    /**
     * This method encrypt a message whith a encryptor.
     * /!\ To decrypt the message you need to have the privateKey.
     * 
     * @param plaintext the message that you want encrypt.
     * @param encryptor the encryptor that you want to use to encrypt the message.
     * @return the encrypted message in a {@link String} format.
     * @throws EncryptWithEncryptorBlockchainDataManagerException
     */
    public static String encryptWithEncryptor(String plaintext, Key encryptor)
            throws EncryptWithEncryptorBlockchainDataManagerException {
        // Chiffrer le message
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, encryptor);
            byte[] encryptedBytes;

            encryptedBytes = cipher.doFinal(plaintext.getBytes());
            String encryptedMessage = Base64.getEncoder().encodeToString(encryptedBytes);

            return encryptedMessage;
        } catch (Exception e) {
            throw new EncryptWithEncryptorBlockchainDataManagerException();
        }
    }

    /**
     * This method encrypt a message whith a publicKey.
     * /!\ To decrypt the message you need to have the privateKey.
     * 
     * @param data      the message that you want encrypt.
     * @param publicKey the encryptor that you want to use to encrypt the message.
     * @return the encrypted message in a {@link String} format.
     */
    public static String encryptWithPublicKey(String data, PublicKey publicKey) {
        try {
            // Génération de la clé AES
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256);
            SecretKey secretKey = keyGen.generateKey();

            // Chiffrement des données avec la clé AES
            Cipher aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedData = aesCipher.doFinal(data.getBytes());

            // Chiffrement de la clé AES avec la clé publique RSA
            Cipher rsaCipher = Cipher.getInstance("RSA");
            rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedAESKey = rsaCipher.doFinal(secretKey.getEncoded());

            // Encodage en base64
            String encryptedDataBase64 = Base64.getEncoder().encodeToString(encryptedData);
            String encryptedAESKeyBase64 = Base64.getEncoder().encodeToString(encryptedAESKey);

            // Retourne les données chiffrées et la clé AES chiffrée
            return encryptedDataBase64 + ":" + encryptedAESKeyBase64;

        } catch (Exception e) {
            TerminalStyle.showError(e.getMessage());
        }

        return null;
    }

    /**
     * This medthod decrypt an encrypted message with a publickey corresponding to
     * the privateKey.
     * 
     * @param encryptedString The encrypted message.
     * @param privateKey      the private key to decrypt the message.
     * @return the message, decrypted.
     */
    public static String decryptWithPrivateKey(String encryptedData, PrivateKey privateKey, String encryptedAESKey) {
        try {
            // Décodage des données encodées en base64
            byte[] encryptedDataBytes = Base64.getDecoder().decode(encryptedData);
            byte[] encryptedAESKeyBytes = Base64.getDecoder().decode(encryptedAESKey);

            // Déchiffrement de la clé AES avec la clé privée RSA
            Cipher rsaCipher = Cipher.getInstance("RSA");
            rsaCipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] aesKeyBytes = rsaCipher.doFinal(encryptedAESKeyBytes);
            SecretKeySpec aesKey = new SecretKeySpec(aesKeyBytes, "AES");

            // Déchiffrement des données avec la clé AES
            Cipher aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.DECRYPT_MODE, aesKey);
            byte[] decryptedDataBytes = aesCipher.doFinal(encryptedDataBytes);

            // Retourne les données déchiffrées
            return new String(decryptedDataBytes);

        } catch (Exception e) {

            e.printStackTrace();
            TerminalStyle.showError(e.getMessage());

            return null;
        }
    }

    /**
     * With this method you can verify the signature of a message to check if the
     * author is the same.
     * 
     * @param publicKey the publicKey of the user you want to compare.
     * @param data      the message.
     * @param signature the signature of the message.
     * @return {@link Boolean} true if the signature of the message encoded with the
     *         publicKey is the same of the signature.
     */
    public static boolean verifySignature(PublicKey publicKey, String data, String signature) {
        try {
            Signature sig = Signature.getInstance(DIGEST_ALGORITHM);
            sig.initVerify(publicKey);
            sig.update(data.getBytes());
            byte[] signatureBytes = Base64.getDecoder().decode(signature);
            return sig.verify(signatureBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException | java.security.InvalidKeyException
                | SignatureException e) {
            TerminalStyle.showError(e.getMessage());
            return false;
        }
    }

    /**
     * This metho sign a text with a private key. Is certifacte that is only you who
     * have read this method.
     * 
     * @param privateKey the private key used to sing
     * @param data       the data who is signed with the publicKey.
     * @return the text signed in a {@link String}.
     */
    public static String signData(PrivateKey privateKey, String data) {
        try {
            Signature signature = Signature.getInstance(DIGEST_ALGORITHM);
            signature.initSign(privateKey);
            signature.update(data.getBytes());
            byte[] signatureBytes = signature.sign();
            return Base64.getEncoder().encodeToString(signatureBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException | java.security.InvalidKeyException
                | SignatureException e) {
            TerminalStyle.showError(e.getMessage());
            return null;
        }
    }

    /**
     * @param input the string you want to transform in to privateKey
     * @return the privateKey correponding to string
     * @throws BlockchainDataManagerPrivateKeyBuildException
     */
    public static PrivateKey generatePrivateKeyFromString(String input)
            throws BlockchainDataManagerPrivateKeyBuildException {
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(input.getBytes());

            // Créer un générateur de clés RSA et initialiser avec le nombre aléatoire
            // sécurisé
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048, random);

            // Générer la paire de clés
            KeyPair keyPair = keyGen.generateKeyPair();

            return keyPair.getPrivate();

        } catch (Exception e) {
            throw new BlockchainDataManagerPrivateKeyBuildException();
        }

    }

    /**
     * @return the generated keyPair generator with the algorithme defined in con
     *         file.
     * @throws GenerateEncryptorBlockchainDataManagerException
     */
    public static Key generateEncyptor() throws GenerateEncryptorBlockchainDataManagerException {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(ENCRYPTOR_ALGORITHM);
            keyGen.init(AES_KEY_SIZE);
            return keyGen.generateKey();
        } catch (Exception e) {
            throw new GenerateEncryptorBlockchainDataManagerException();
        }
    }

    //// AES

    public static boolean isAESKey(Key privateKey) {
        if (privateKey instanceof RSAPrivateKey) {
            int keyLength = ((RSAPrivateKey) privateKey).getModulus().bitLength();
            if (keyLength == AES_KEY_SIZE) {

                return true;
            }
        }

        return false;
    }

    public static boolean isAESKey(PublicKey publicKey) {
        if (publicKey instanceof RSAPrivateKey) {
            int keyLength = ((RSAPrivateKey) publicKey).getModulus().bitLength();
            if (keyLength == AES_KEY_SIZE) {

                return true;
            }
        }

        return false;
    }

    public static String convertKeyToString(Key key) {
        byte[] bytesCle = key.getEncoded();
        String basekey64 = Base64.getEncoder().encodeToString(bytesCle);
        return basekey64;
    }

    //// RSA
    public static boolean isRSAKey(PrivateKey privateKey) {
        return (privateKey instanceof RSAPrivateKey);
    }

    public static boolean isRSAKey(PublicKey publicKey) {
        return (publicKey instanceof RSAPublicKey);
    }

    // SHA
    public static String sha256Hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void save(Properties properties, String fileName) {
        DataManager.save(properties, srcPath + fileName);
    }

    public static String getSrcPath() {
        return srcPath;
    }
}
