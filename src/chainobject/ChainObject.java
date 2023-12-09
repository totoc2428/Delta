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
import java.util.Properties;

import util.data.DataProp;
import util.exception.chainobject.ChainObjectGenerateException;
import util.security.Key;

public abstract class ChainObject {
    private final static Properties INIT_PROPERTIES = DataProp.read(Paths.get("./resources/config/init.conf").toFile());
    private final static String SIGNATURE_ALGORITHM = DataProp
            .read(Paths.get(INIT_PROPERTIES
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("ChainObjectSignatureAlgorithm");

    private final static String KEY_ALGORITHM = DataProp
            .read(Paths.get(INIT_PROPERTIES
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("ChainObjectKeyAlgorithm");

    private final static String DIGEST_ALGORITHM = DataProp
            .read(Paths.get(INIT_PROPERTIES
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("ChainObjectDigestAlgorithm");

    private final static int KEY_SIZE = Integer.parseInt(DataProp
            .read(Paths.get(INIT_PROPERTIES
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("ChainObjectKeySize"));

    protected final static String SRC_PATH = DataProp
            .read(Paths.get(INIT_PROPERTIES
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("ChainObjectSrcFolder");

    private PublicKey publicKey;
    private PrivateKey privateKey;

    protected boolean isOwnable;

    public ChainObject(PublicKey publicKey, PrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.isOwnable = false;
    }

    public ChainObject() {
        this(null, null);
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public boolean getisOwnable() {
        return isOwnable;
    }

    private void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    private void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    /**
     * Generate the public and the private key with the hash of the object. Any
     * chainObject have a public and private key who is linked to the value of it
     * atribute.
     * /!\ all object who {@extend ChainObject} need to apply this method in the
     * last line of the base constructor.
     * 
     * @param object a chain object type.
     */
    public static void generate(ChainObject object) {
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
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
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            new ChainObjectGenerateException();
        }

    }

    public void write() {
        write(initWrite(), SRC_PATH + Key.publicKeyToString(publicKey));
    }

    protected void write(Properties propertiesParameter, String src) {
        DataProp.writeConfig(propertiesParameter, src + ".conf");
    }

    /**
     * Init the properties with the value of each atribut of the ChainObject.
     * /!\ Important ! All object who is a child of {@link ChainObject} must to
     * {@overide} this method.
     * method.
     * 
     * @return properties who is a properties of any
     */
    protected Properties initWrite() {
        Properties properties = new Properties();
        writeInProperties(properties, "publicKey", Key.publicKeyToString(publicKey));
        writeInProperties(properties, "privateKey", Key.privateKeyToString(privateKey));

        return properties;
    }

    /**
     * With writeInPropertie methode you can write a ChainObject in file who can be
     * saved.
     * 
     * @param properties the properties of any atribut value of the ChainObject.
     *                   /!\ Important ! If the attribut is a ChainObject we save
     *                   the
     *                   privateKey in the properties.
     *                   [i] In the reading we refind the public key with the
     *                   private key who is the name of any ChainObject file and we
     *                   read the ChainObject.
     *                   [i] If the value of the atribut is null,
     *                   {@link String#isblanck()}, {@link String#isEmpty()}, it
     *                   will be set at "null".
     * @param key        the name of the atribut
     * @param value      the value of the attribut.
     */
    protected void writeInProperties(Properties properties, String key, String value) {
        if (key != null && properties != null && value != null) {
            if (value.isEmpty() || value.isBlank()) {
                value = "null";
            }
            if (!key.isEmpty() && !key.isBlank()) {
                if (privateKey != null) {
                    properties.put(key, Key.encryptWithPublicKey(value, publicKey));
                } else {
                    properties.put(key, value);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "ChainObject";
    }

}