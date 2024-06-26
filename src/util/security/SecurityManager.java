package util.security;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
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
import io.jsonwebtoken.security.InvalidKeyException;
import util.blockchain.BlockchainManager;
import util.data.DataManager;
import util.message.done.DoneSystemMessage;
import util.primary.Primary;

public abstract class SecurityManager {
    private static Properties initSecurityProperties;

    private static String encryptorAlgoritm;
    private static String savedKeyEncryptorSpace;

    private static String signatureAgorithm;

    /* -LOADER */
    public static void load() throws SecurityManagerLoadSystemException {
        try {
            initSecurityProperties = DataManager.readAFile(Primary.getSecurityManagerInitPath());

            encryptorAlgoritm = initSecurityProperties.getProperty("ENCRYPTOR_ALGORITHM");
            savedKeyEncryptorSpace = initSecurityProperties.getProperty("SAVED_KEY_ENCRYPTOR_SPACE");

            signatureAgorithm = initSecurityProperties.getProperty("SIGNATURE_ALGORITHM");

            DoneSystemMessage.show("SecurityManagerLoad", 1);
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

            DoneSystemMessage.show("SecurityManagerEncrypt", 1);
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

            DoneSystemMessage.show("SecurityManagerDecrypt", 1);
            return new String(decryptedDataBytes);
        } catch (Exception e) {
            throw new DecryptSecurityManagerSystemException();
        }
    }

    /* --SIGNATURE */
    /* ---SIGN */
    public static String sign(String message, PrivateKey privateKey) throws SignSecurityManagerSystemException {
        if (message != null && privateKey != null) {
            try {
                Signature signature = Signature.getInstance(signatureAgorithm);
                signature.initSign(privateKey);
                signature.update(message.getBytes());
                byte[] signatureBytes = signature.sign();

                DoneSystemMessage.show("SecurityManagerSign", 1);
                return Base64.getEncoder().encodeToString(signatureBytes);
            } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException
                    | java.security.InvalidKeyException e) {
                throw new SignSecurityManagerSystemException();
            }
        } else {
            throw new SignSecurityManagerSystemException();
        }
    }

    /* ---VERIFY */
    public static boolean verifySignature(String signature, String message, PublicKey publicKey)
            throws VerifySignatureSecurityManagerSystemException {
        if (signature != null && message != null && publicKey != null) {
            try {
                Signature sig = Signature.getInstance(signatureAgorithm);
                sig.initVerify(publicKey);
                sig.update(message.getBytes());
                byte[] signatureBytes = Base64.getDecoder().decode(signature);

                DoneSystemMessage.show("SecurityManagerVerifySignature", 1);
                return sig.verify(signatureBytes);
            } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException
                    | java.security.InvalidKeyException e) {
                throw new VerifySignatureSecurityManagerSystemException();
            }
        } else {
            throw new VerifySignatureSecurityManagerSystemException();
        }

    }
}
