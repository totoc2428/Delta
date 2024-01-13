package util.security;

import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Properties;

import javax.crypto.Cipher;

import util.data.Prop;
import util.exception.util.security.KeyEncryptWithPublicKeyException;
import util.exception.util.security.KeyInvalidKeyException;
import util.exception.util.security.KeyNoSuchAlgorithmException;

public class Key {
    private final static Properties INIT_PROPERTIES = Prop.read(Paths.get("./resources/config/init.conf").toFile());
    private final static String KEY_ALGORITHM = Prop
            .read(Paths.get(INIT_PROPERTIES
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("ChainObjectKeyAlgorithm");
    private final static String DIGEST_ALGORITHM = Prop
            .read(Paths.get(INIT_PROPERTIES
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("ChainObjectDigestAlgorithm");

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
            return KeyFactory.getInstance(KEY_ALGORITHM)
                    .generatePublic(new X509EncodedKeySpec(privateKey.getEncoded()));
        } catch (InvalidKeySpecException e) {
            new KeyInvalidKeyException();
        } catch (NoSuchAlgorithmException e) {
            new KeyNoSuchAlgorithmException();
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
            new KeyInvalidKeyException();
        } catch (NoSuchAlgorithmException e) {
            new KeyNoSuchAlgorithmException();
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
            new KeyInvalidKeyException();
        } catch (NoSuchAlgorithmException e) {
            new KeyNoSuchAlgorithmException();
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
            new KeyEncryptWithPublicKeyException();
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
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
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
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
            return null;
        }
    }
}
