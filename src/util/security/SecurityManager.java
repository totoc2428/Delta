package util.security;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import exception.system.util.data.PropertiesReadingSystemException;
import exception.system.util.security.DecryptSecurityManagerSystemException;
import exception.system.util.security.EncryptSecurityManagerSystemException;
import exception.system.util.security.SecurityManagerLoadSystemException;
import exception.system.util.security.SignSecurityManagerSystemException;
import exception.system.util.security.VerifySignatureSecurityManagerSystemException;
import util.blockchain.BlockchainManager;
import util.data.DataManager;
import util.primary.Primary;

public abstract class SecurityManager {
    private static Properties initSecurityProperties;

    private static String encryptorAlgoritm;
    private static String savedKeyEncryptorSpace;

    /* -LOADER */
    public static void load() throws SecurityManagerLoadSystemException {
        try {
            initSecurityProperties = DataManager.readAFile(Primary.getSecurityManagerInitPath());

            encryptorAlgoritm = initSecurityProperties.getProperty("ENCRYPTOR_ALGORITHM");
            savedKeyEncryptorSpace = initSecurityProperties.getProperty("SAVED_KEY_ENCRYPTOR_SPACE");

        } catch (PropertiesReadingSystemException e) {
            throw new SecurityManagerLoadSystemException();
        }
    }

    /* -MESSAGE */
    /* --ENCRYPT */
    public static String encrypt(String message, PublicKey publicKey) throws EncryptSecurityManagerSystemException {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(encryptorAlgoritm);
            keyGen.init(256);
            SecretKey secretKey = keyGen.generateKey();

            Cipher aesCipher = Cipher.getInstance(encryptorAlgoritm);
            aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedData = aesCipher.doFinal(message.getBytes());

            Cipher rsaCipher = Cipher.getInstance(BlockchainManager.getKeyAlgorithm());
            rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedAESKey = rsaCipher.doFinal(secretKey.getEncoded());

            String encryptedDataBase64 = Base64.getEncoder().encodeToString(encryptedData);
            String encryptedAESKeyBase64 = Base64.getEncoder().encodeToString(encryptedAESKey);

            return encryptedDataBase64 + savedKeyEncryptorSpace + encryptedAESKeyBase64;

        } catch (Exception e) {
            throw new EncryptSecurityManagerSystemException();
        }
    }

    /* --DECRYPT */
    public static String decrypt(String message, PrivateKey privateKey) throws DecryptSecurityManagerSystemException {
        try {
            String encryptedData = message.split(savedKeyEncryptorSpace)[0];
            String encryptedAESKey = message.split(savedKeyEncryptorSpace)[1];

            byte[] encryptedDataBytes = Base64.getDecoder().decode(encryptedData);
            byte[] encryptedAESKeyBytes = Base64.getDecoder().decode(encryptedAESKey);

            Cipher rsaCipher = Cipher.getInstance(BlockchainManager.getKeyAlgorithm());
            rsaCipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] aesKeyBytes = rsaCipher.doFinal(encryptedAESKeyBytes);
            SecretKeySpec aesKey = new SecretKeySpec(aesKeyBytes, encryptorAlgoritm);

            Cipher aesCipher = Cipher.getInstance(encryptorAlgoritm);
            aesCipher.init(Cipher.DECRYPT_MODE, aesKey);
            byte[] decryptedDataBytes = aesCipher.doFinal(encryptedDataBytes);

            return new String(decryptedDataBytes);

        } catch (Exception e) {
            throw new DecryptSecurityManagerSystemException();
        }
    }

    /* --SIGNATURE */
    /* ---SIGN */
    public static String sign(String message, PrivateKey privateKey) throws SignSecurityManagerSystemException {
        return null;
    }

    /* ---VERIFY */
    public static boolean verifySignature(String signature, String message, PublicKey publicKey)
            throws VerifySignatureSecurityManagerSystemException {
        return false;
    }
}
