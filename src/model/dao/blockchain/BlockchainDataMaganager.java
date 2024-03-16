package model.dao.blockchain;

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
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.security.InvalidKeyException;
import model.dao.DataManager;
import util.style.TerminalStyle;

public abstract class BlockchainDataMaganager extends DataManager {

    protected final static Properties BLOCKCHAIN_PROPERTIES = DataManager
            .read(INIT_PROPERTIES.getProperty("BLOCKCHAIN_PROPERTIES"));

    private final static String KEY_ALGORITHM = BLOCKCHAIN_PROPERTIES.getProperty(
            "KEY_ALGORITHM");
    private final static String DIGEST_ALGORITHM = BLOCKCHAIN_PROPERTIES
            .getProperty("DIGEST_ALGORITHM");

    private final static int KEY_SIZE = Integer.parseInt(BLOCKCHAIN_PROPERTIES.getProperty("KEY_SIZE"));

    /**
     * This method retrieve the publicKey of a privateKey.
     * 
     * [i] If an error occured the method will be return null.
     * 
     * @param privateKey
     * @return a {@link PublicKey} deducted with the privateKey.
     */
    public static PublicKey getPublicKeyFromPrivateKey(PrivateKey privateKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateKeySpec privateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);

            // Créer une spécification de clé publique RSA à partir du module (n) et de
            // l'exposant public (e)
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(privateKeySpec.getModulus(),
                    BigInteger.valueOf(65537)); // Exposant public standard

            // Générer et retourner la clé publique
            return keyFactory.generatePublic(publicKeySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            TerminalStyle.showError(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            TerminalStyle.showError(e.getMessage());
        }
        return null;
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
        } catch (InvalidKeySpecException e) {
            // TODO make execption;
        } catch (NoSuchAlgorithmException e) {
            // TODO make execption;
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
            // TODO make execption;
        } catch (NoSuchAlgorithmException e) {
            // TODO make execption;
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
     * This method encrypt a message whith a publicKey.
     * /!\ To decrypt the message you need to have the privateKey.
     * 
     * @param plaintext the message that you want encrypt.
     * @param publicKey the publicKey that you want to use to encrypt the message.
     * @return the encrypted message in a {@link String} format.
     */
    public static String encryptWithPublicKey(String plaintext, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            // TODO make execption;
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
    public static String decryptWithPrivateKey(String encryptedString, PrivateKey privateKey) {
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedString);
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(privateKey.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            return new String(decryptedBytes);
        } catch (Exception e) {
            TerminalStyle.showError(e.getMessage());
        }

        return null;
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
     */
    public static PrivateKey generatePrivateKeyFromString(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");

            // Hachage du message
            byte[] hash = digest.digest(input.getBytes());

            // Utilisation des 64 premiers octets du hash pour générer la clé privée
            byte[] truncatedHash = new byte[64];
            System.arraycopy(hash, 0, truncatedHash, 0, 64);

            // Conversion du tableau d'octets en un grand entier positif
            BigInteger bigInt = new BigInteger(1, truncatedHash);

            // Création d'une spécification de clé privée RSA
            RSAPrivateKeySpec spec = new RSAPrivateKeySpec(bigInt, BigInteger.valueOf(65537)); // Exposant public
                                                                                               // standard

            // Génération et retour de la clé privée
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(spec);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            TerminalStyle.showError(e.getMessage());
        }

        return null;
    }
}
