package src.util.signature;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class Signature {
    private String publicKey;
    private String privateKey;

    public Signature(String publicKey, String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public Signature() {
        try {

            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);

            KeyPair keyPair = keyGen.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            this.publicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            this.privateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

}
