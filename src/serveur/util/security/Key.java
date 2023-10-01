package serveur.util.security;

import java.io.File;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Properties;

import serveur.util.data.prop.DataProp;

public class Key {
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private String publickeyString;
    private String privateKeyString;

    /* Construcor */
    /* Base constructor */
    public Key(String publickeyString, String privateKeyString) {
        this.publickeyString = publickeyString;
        this.privateKeyString = privateKeyString;
        this.publicKey = publickeyString != null ? Key.PublicKeyfromString(publickeyString) : null;
        this.privateKey = privateKeyString != null ? Key.PrivateKeyfromString(privateKeyString) : null;

    }

    /* Construcor */
    /* new Key */

    public Key() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            publicKey = keyPair.getPublic();
            privateKey = keyPair.getPrivate();
            publickeyString = Key.publicKeytoString(publicKey);
            privateKeyString = Key.privateKeytoString(privateKey);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
    }

    /* Construcor */
    /* new Key with privateKey null */
    public Key(PublicKey publicKey) {
        this(Key.publicKeytoString(publicKey), null);
    }

    /* Construcor */
    /* new Key read in a file */
    public Key(File fileName) {
        this(Key.readKey(fileName).getPublickeyString(), Key.readKey(fileName).getPrivateKeyString());
    }

    /* Getter and setter method */
    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public String getPrivateKeyString() {
        return privateKeyString;
    }

    public String getPublickeyString() {
        return publickeyString;
    }

    /* Override method */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((publicKey == null) ? 0 : publicKey.hashCode());
        result = prime * result + ((privateKey == null) ? 0 : privateKey.hashCode());
        result = prime * result + ((publickeyString == null) ? 0 : publickeyString.hashCode());
        result = prime * result + ((privateKeyString == null) ? 0 : privateKeyString.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Key other = (Key) obj;
        if (publicKey == null) {
            if (other.publicKey != null)
                return false;
        } else if (!publicKey.equals(other.publicKey))
            return false;
        if (privateKey == null) {
            if (other.privateKey != null)
                return false;
        } else if (!privateKey.equals(other.privateKey))
            return false;
        if (publickeyString == null) {
            if (other.publickeyString != null)
                return false;
        } else if (!publickeyString.equals(other.publickeyString))
            return false;
        if (privateKeyString == null) {
            if (other.privateKeyString != null)
                return false;
        } else if (!privateKeyString.equals(other.privateKeyString))
            return false;
        return true;
    }

    /* Static Method */

    /* Transform a String to PublicKey */
    public static PublicKey PublicKeyfromString(String string) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(string);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /* Transform a String to PrivateKey */
    public static PrivateKey PrivateKeyfromString(String string) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(string);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /* Transform a PublicKey to a String */
    private static String publicKeytoString(PublicKey publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    /* Transform a PrivateKey to a String */
    private static String privateKeytoString(PrivateKey privateKey) {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    /* Read a block saved in a file */
    private static Key readKey(File fileName) {
        Properties properties = DataProp.read(fileName);
        if (properties != null) {
            return new Key((String) properties.get("publicKey"), (String) properties.get("privateKey"));
        } else {
            return null;
        }
    }
}
