package serveur.util.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Key {
    String publickey;
    String privateKey;

    public Key(String publicKey, String privateKey) {
        this.publickey = publicKey;
        this.privateKey = privateKey;
    }

    public Key() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            publickey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
            privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getPublickey() {
        return publickey;
    }
}
