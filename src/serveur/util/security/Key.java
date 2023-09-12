package serveur.util.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class Key {
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private String publickeyString;
    private String privateKeyString;

    public Key(String publickeyString, String privateKeyString) {
        this.publickeyString = publickeyString;
        this.privateKeyString = privateKeyString;
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

}
