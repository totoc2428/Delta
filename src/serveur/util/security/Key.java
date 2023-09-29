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
}
