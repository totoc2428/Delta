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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import util.data.Prop;
import util.exception.chainobject.ChainObjectGenerateException;
import util.security.Key;

public abstract class ChainObject {
    private final static Properties INIT_PROPERTIES = Prop.read(Paths.get("./resources/config/init.conf").toFile());
    private final static String SIGNATURE_ALGORITHM = Prop
            .read(Paths.get(INIT_PROPERTIES
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("ChainObjectSignatureAlgorithm");

    private final static String KEY_ALGORITHM = Prop
            .read(Paths.get(INIT_PROPERTIES
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("ChainObjectKeyAlgorithm");

    private final static String DIGEST_ALGORITHM = Prop
            .read(Paths.get(INIT_PROPERTIES
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("ChainObjectDigestAlgorithm");

    private final static int KEY_SIZE = Integer.parseInt(Prop
            .read(Paths.get(INIT_PROPERTIES
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("ChainObjectKeySize"));

    protected final static String SRC_PATH = Prop
            .read(Paths.get(INIT_PROPERTIES
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("ChainObjectSrcFolder");

    protected final static String SAVED_LIST_SPACES = Prop
            .read(Paths.get(INIT_PROPERTIES
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("ChainObjectSavedListSpaceValue");
    protected final static String SAVED_HASHMAP_SPACES = Prop
            .read(Paths.get(INIT_PROPERTIES
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("ChainObjectSavedHashMapSpaceValue");
    protected final static String SAVED_PRIVATE_KEY = Prop
            .read(Paths.get(INIT_PROPERTIES
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("ChainObjectSavedPrivateKey");
    protected final static String SAVED_PUBLIC_KEY = Prop
            .read(Paths.get(INIT_PROPERTIES
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("ChainObjectSavedPublicKey");

    /*
     * The publicKey of the ChainObject.
     */
    private PublicKey publicKey;
    /**
     * The privateKey of the ChainObject.
     */
    private PrivateKey privateKey;

    /*
     * if the object is ownable or sellable.
     */
    protected boolean isOwnable;

    /**
     * If this attribut is true, all the data of the object will be set at true.
     */
    private boolean isPublic;

    /* constructor */
    public ChainObject(PublicKey publicKey, PrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.isOwnable = false;
        this.isPublic = false;
    }

    public ChainObject() {
        this(null, null);
    }

    /* getter */

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public boolean getisOwnable() {
        return isOwnable;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    /* overrided method */
    @Override
    public String toString() {
        return "ChainObject";
    }

    /* chainobject method */

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
            object.privateKey = privateKey;
            object.publicKey = publicKey;
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            new ChainObjectGenerateException();
        }

    }

    /**
     * This method write the chainobject in file.
     * /!\ Important ! All object who is a child of {@link ChainObject} must to
     * {@overide} this method.
     */
    public void write() {
        write(initWrite(), SRC_PATH + Key.publicKeyToString(publicKey));
    }

    /**
     * Take a propertie and sources files destination to save the chainObject in a
     * file.
     * 
     * @param propertiesParameter the {@link ChainObject} in a {@link Properties}
     *                            format.
     * @param src                 the sources file where the {@link ChainObject}
     *                            will be saved.
     */
    protected void write(Properties propertiesParameter, String src) {
        Prop.writeConfig(propertiesParameter, src + ".conf");
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
        writeInProperties(properties, "publicKey", Key.publicKeyToString(publicKey), true);
        writeInProperties(properties, "privateKey", Key.privateKeyToString(privateKey), false);

        return properties;
    }

    /**
     * With writeInPropertie methode you can write a ChainObject in file who can be
     * saved.
     * 
     * @param properties        the properties of any atribut value of the
     *                          ChainObject.
     *                          /!\ Important ! If the attribut is a ChainObject we
     *                          save
     *                          the
     *                          privateKey in the properties.
     *                          [i] In the reading we refind the public key with the
     *                          private key who is the name of any ChainObject file
     *                          and we
     *                          read the ChainObject.
     *                          [i] If the value of the atribut is null,
     *                          {@link String#isblanck()}, {@link String#isEmpty()},
     *                          it will be set at "null".
     * @param key               the name of the atribut
     * @param value             the atribut you cann pass in a parameter.
     * @param isPublicParameter if this parameter is true the data contain in the
     *                          value will be never encrypted.
     *                          /!\ to be encrypted the {@link chainObject#isPublic}
     *                          attribut need to be false (default value).
     */
    protected void writeInProperties(Properties properties, String key, Object value, boolean isPublicParameter) {
        if (key != null && properties != null) {
            if (value == null) {
                value = "null";
            }
            if (!key.isEmpty() && !key.isBlank()) {
                String stringValue;
                if (value instanceof HashMap) {
                    HashMap<Object, Object> hashMapValue = (HashMap<Object, Object>) value;
                    ArrayList<Object> keyArrayList = new ArrayList<Object>();
                    ArrayList<Object> valueArrayList = new ArrayList<Object>();
                    for (Object keyObjectArrayList : hashMapValue.keySet()) {
                        keyArrayList.add(keyObjectArrayList);
                    }
                    for (Object valueObjectArrayList : hashMapValue.values()) {
                        valueArrayList.add(valueObjectArrayList);
                    }
                    stringValue = writeInitPropertiesToString(keyArrayList, isPublicParameter) + SAVED_HASHMAP_SPACES
                            + writeInitPropertiesToString(valueArrayList, isPublicParameter);
                } else {
                    stringValue = writeInitPropertiesToString(value, isPublicParameter);
                }
                if (privateKey != null && !isPublic && !isPublicParameter) {
                    key = SAVED_PRIVATE_KEY + key;
                    stringValue = Key.encryptWithPublicKey(stringValue, publicKey);
                } else {
                    key = SAVED_PUBLIC_KEY + key;
                }
                properties.put(key, stringValue);
            }
        }
    }

    /**
     * This method prepare any atribute of a ChainObject to be readble in a file.
     * 
     * @param value             the value of the atribute.
     * @param isPublicParameter if the value need to be encrypted with the publicKey
     *                          of this
     *                          Object.
     * @return the value in string format the publicKey if the object is
     *         {@link ChainObject} type.
     */
    private String writeInitPropertiesToString(Object value, boolean isPublicParameter) {
        StringBuilder stringBuilder = new StringBuilder();
        if (value instanceof ChainObject) {
            ChainObject chainObjectValue = (ChainObject) value;
            stringBuilder.append(SAVED_PUBLIC_KEY);
            stringBuilder.append(chainObjectValue.getPublicKey());
            stringBuilder.append(SAVED_PRIVATE_KEY);
            if (!isPublicParameter && chainObjectValue.privateKey != null) {
                stringBuilder.append(chainObjectValue.getPrivateKey());
            } else {
                stringBuilder.append("null");
            }
        }
        if (value instanceof ArrayList) {
            ArrayList<Object> valueArrayList = (ArrayList<Object>) value;
            for (Object object : valueArrayList) {
                stringBuilder.append(writeInitPropertiesToString(object, isPublic) + SAVED_LIST_SPACES);
            }
        }

        return stringBuilder.toString();
    }

    /**
     * This medhod make a singature of the Object to be checked.
     * /!\ All ellemant called in this medthod need to be in saved in a public
     * format. It's recommanded to make public general information who is all ready
     * saved in public (not encrypted).
     * /!\ All the child of any chainObject must overide this method.
     * 
     * @return the element of all attribut who make the signature.
     */
    protected String signHash() {
        return "chainObject";
    }

    public String sign() {
        return Key.signData(privateKey, signHash());
    }
}