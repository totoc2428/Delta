package serveur.util.security;

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

public class Key {
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private String publickeyString;
    private String privateKeyString;

    public Key(String publickeyString, String privateKeyString) {
        this.publickeyString = publickeyString;
        this.privateKeyString = privateKeyString;
        this.publicKey = Key.PublicKeyfromString(publickeyString);
        this.privateKey = privateKeyString == null ? Key.PrivateKeyfromString(privateKeyString) : null;

    }

    public Key() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            this.publicKey = keyPair.getPublic();
            this.privateKey = keyPair.getPrivate();
            publickeyString = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
            privateKeyString = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
    }

    public Key(String publicKey) {
        this(publicKey, null);
    }

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

    private static PublicKey PublicKeyfromString(String string) {
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

    private static PrivateKey PrivateKeyfromString(String string) {
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

}
