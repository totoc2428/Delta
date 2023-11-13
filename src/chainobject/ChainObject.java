package chainobject;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

import util.data.DataProp;

public abstract class ChainObject {
    private final static String SIGNATURE_ALGORITHM = DataProp
            .read(Paths.get(DataProp.read(Paths.get("./resources/config/init.conf").toFile())
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("ChainObjectSignatureAlgorithm");

    private final static String KEY_ALGORITHM = DataProp
            .read(Paths.get(DataProp.read(Paths.get("./resources/config/init.conf").toFile())
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("ChainObjectKeyAlgorithm");

    private final static String DIGEST_ALGORITHM = DataProp
            .read(Paths.get(DataProp.read(Paths.get("./resources/config/init.conf").toFile())
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("ChainObjectDigestAlgorithm");

    private final static int KEY_SIZE = Integer.parseInt(DataProp
            .read(Paths.get(DataProp.read(Paths.get("./resources/config/init.conf").toFile())
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("ChainObjectKeySize"));

    private String hash;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public ChainObject(String hash, PublicKey publicKey, PrivateKey privateKey) {
        this.hash = hash;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public String getHash() {
        return hash;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    private void setHash(String hash) {
        this.hash = hash;
    }

    private void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    private void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public boolean verify() {
        Signature verificationSignature;
        try {
            verificationSignature = Signature.getInstance(SIGNATURE_ALGORITHM);
            verificationSignature.initVerify(publicKey);
            verificationSignature.update(hash.getBytes());
            return verificationSignature.verify(privateKey.getEncoded());
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void generate(ChainObject object) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        byte[] hash = MessageDigest.getInstance(DIGEST_ALGORITHM).digest(object.toString().getBytes());
        String hashString = new String(hash, StandardCharsets.UTF_8);
        PrivateKey privateKey = keyPair.getPrivate();
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(hashString.getBytes());
        object.setPrivateKey(privateKey);
        object.setPublicKey(publicKey);
        object.setHash(hashString);
    }

}