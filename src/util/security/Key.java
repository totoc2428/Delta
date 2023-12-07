package util.security;

import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Properties;

import util.data.DataProp;
import util.exception.util.security.KeyInvalidKeyException;
import util.exception.util.security.KeyNoSuchAlgorithmException;

public class Key {
    private final static Properties INIT_PROPERTIES = DataProp.read(Paths.get("./resources/config/init.conf").toFile());
    private final static String KEY_ALGORITHM = DataProp
            .read(Paths.get(INIT_PROPERTIES
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("ChainObjectKeyAlgorithm");

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

}
