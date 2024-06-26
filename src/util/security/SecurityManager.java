package util.security;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import exception.system.util.data.PropertiesReadingSystemException;
import exception.system.util.security.EncryptSecurityManagerSystemException;
import exception.system.util.security.SecurityManagerLoadSystemException;
import util.data.DataManager;
import util.primary.Primary;

public abstract class SecurityManager {
    private static Properties initSecurityProperties;

    /* -LOADER */
    public static void load() throws SecurityManagerLoadSystemException {
        try {
            initSecurityProperties = DataManager.readAFile(Primary.getSecurityManagerInitPath());
        } catch (PropertiesReadingSystemException e) {
            throw new SecurityManagerLoadSystemException();
        }
    }

    /* -INIT */
    /* --GETTER */
    public static Properties getInit() {
        return initSecurityProperties;
    }

    /* -MESSAGE */
    /* --ENCRYPT */
    public static String encrypt(String message, PublicKey publicKey) throws EncryptSecurityManagerSystemException {
        return null;
    }

    /* --DECRYPT */
    public static String decrypt(String message, PrivateKey privateKey) {
        return null;
    }
}
