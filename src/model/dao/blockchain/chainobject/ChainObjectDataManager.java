package model.dao.blockchain.chainobject;

import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Properties;
import model.dao.DataManager;

import model.dao.blockchain.BlockchainDataMaganager;
import model.dto.blockchain.chainobject.ChainObject;
import model.dto.blockchain.chainobject.person.physical.PhysicalPerson;

public abstract class ChainObjectDataManager extends BlockchainDataMaganager {

    protected static final Properties CHAINOBJECT_PROPERTIES = read(
            BLOCKCHAIN_PROPERTIES.getProperty("CHAINOBJECT_PROPERTIES"));

    private static final String SAVED_PRIVATE_KEY_TAG = CHAINOBJECT_PROPERTIES.getProperty("SAVED_PRIVATE_KEY_TAG");
    private static final String SAVED_PUBLIC_KEY_TAG = CHAINOBJECT_PROPERTIES.getProperty("SAVED_PUBLIC_KEY_TAG");

    protected static final String SAVED_CHAINOBJECT_TAG = CHAINOBJECT_PROPERTIES.getProperty("SAVED_CHAINOBJECT_TAG");

    // save
    /**
     * Take a ChainObject to convert in a properties format. Is prepared to save any
     * chainObject.
     * 
     * @param chainObject the chainObject you want to convert in to a properties.
     * @return the chainObject into a Properties format.
     */
    public static Properties chainObjectToAProperties(ChainObject chainObject) {
        if (chainObject != null) {
            Properties properties = new Properties();
            properties.setProperty(SAVED_PUBLIC_KEY_TAG + OBJECT_TYPE_KEY, SAVED_CHAINOBJECT_TAG);
            if (chainObject.getPublicKey() != null) {
                properties.setProperty(SAVED_PRIVATE_KEY_TAG + "privateKey", encryptWithPublicKey(
                        privateKeyToString(chainObject.getPrivateKey()), chainObject.getPublicKey()));
            }
            properties.setProperty(SAVED_PUBLIC_KEY_TAG + "publicKey", publicKeyToString(chainObject.getPublicKey()));

            return properties;
        }

        return null;
    }

    /**
     * @param key        the key of the parameter in properties.
     * @param properties the propertie you want to edit
     * @param object     the object you want to save in a properties.
     * @param publicKey  the publicKey to encrypt the object. If you don't want to
     *                   encrypt the object set this parmeter to null.
     */
    public static void saveAnObjectInAProperties(String key, Properties properties, Object object,
            PublicKey publicKey) {
        if (object instanceof ChainObject) {
            ChainObject co = (ChainObject) object;
            if (co.getPrivateKey() != null) {
                saveAnStringInAProperties(key, SAVED_CHAINOBJECT_TAG, properties,
                        privateKeyToString(co.getPrivateKey()), publicKey);
            } else {
                saveAnStringInAProperties(key, SAVED_CHAINOBJECT_TAG, properties, publicKeyToString(co.getPublicKey()),
                        publicKey);
            }
        } else if (object instanceof ArrayList) {

            @SuppressWarnings("unchecked")
            ArrayList<Object> oArrayList = (ArrayList<Object>) object;
            saveAnStringInAProperties(key, SAVED_LIST_SPACE, properties, objectCollectionToAString(oArrayList),
                    publicKey);

        } else if (object instanceof HashMap) {
            @SuppressWarnings("unchecked")
            HashMap<Object, Object> oHashMap = (HashMap<Object, Object>) object;
            saveAnStringInAProperties(key, SAVED_LIST_SPACE, properties, objectHashMapToString(oHashMap), publicKey);
        } else if (object instanceof PrivateKey) {
            PrivateKey po = (PrivateKey) object;
            saveAnStringInAProperties(key, SAVED_PRIVATE_KEY_TAG, properties, privateKeyToString(po), publicKey);

        } else if (object instanceof PublicKey) {
            PublicKey po = (PublicKey) object;
            saveAnStringInAProperties(key, "", properties, publicKeyToString(po), publicKey);
        } else {
            saveAnStringInAProperties(key, "", properties, object.toString(), publicKey);
        }
    }

    /**
     * @param key        the key
     * @param tag        the tag if this tag contain to recogise any object type.
     * @param properties the properties you want to edit.
     * @param string     the string you want to save in the properties.
     * @param publicKey  the public key to encrypt the object. If you don't want to
     *                   encrypt the object set this parmeter to null.
     */
    private static void saveAnStringInAProperties(String key, String tag, Properties properties, String string,
            PublicKey publicKey) {

        if (publicKey != null) {
            key = SAVED_PRIVATE_KEY_TAG + tag + key;
            string = encryptWithPublicKey(string, publicKey);
        } else {
            key = SAVED_PUBLIC_KEY_TAG + tag + key;
        }

        properties.setProperty(key, string);
    }

    // read

    /**
     * @param key        the key you want to read.
     * @param properties the properties where is saved the key.
     * @param privateKey the privateKey to decrypt the value. If the value is
     *                   encrypted and the PrivateKey is null the value will be
     *                   null.
     * @return the value in a string format.
     */
    private static String readAStringSavedInProperties(String key, Properties properties, PrivateKey privateKey) {
        String value = null;
        if (properties.getProperty(key).contains(SAVED_PRIVATE_KEY_TAG)) {
            if (privateKey != null) {
                value = decryptWithPrivateKey(properties.getProperty(key), privateKey);
            }
        } else if (properties.getProperty(key).contains(SAVED_PUBLIC_KEY_TAG)) {
            value = properties.getProperty(key);
        }

        return value;
    }

    /**
     * @param key        the key you want to read.
     * @param properties the properties where is saved the key.
     * @param privateKey the privateKey to decrypt the value. If the value is
     *                   encrypted and the PrivateKey is null the value will be
     *                   null.
     * @return the object value saved in the properties.
     */
    public static Object readAObjectSavedInPropertes(String key, Properties properties, PrivateKey privateKey) {
        Object object = null;
        String strResult = readAStringSavedInProperties(key, properties, privateKey);
        if (key.contains(SAVED_DIC_TAG)) {
            object = stringToObjectHashMap(strResult);
        } else if (key.contains(SAVED_LIST_TAG)) {
            object = stringToObjectArrayList(strResult);
        }
        return object;
    }

    /**
     * @param file       the file who is saved the chainObject
     * @param privateKey the privateKey to decrypt all the privateValue.
     * @return chainObject corresponding to the file.
     */
    public static ChainObject chainObjectReadFromFile(File file, PrivateKey privateKey) {
        return chainObjectReadFromProperties(DataManager.read(file), privateKey);
    }

    public static ChainObject chainObjectReadFromProperties(Properties properties, PrivateKey privateKey) {
        ChainObject chainObject = null;
        if (isAChainObject(properties)) {
            PublicKey publicKey = stringToPublicKey(properties.getProperty(SAVED_PUBLIC_KEY_TAG + "publicKey"));
            chainObject = (ChainObject) new PhysicalPerson(privateKey, publicKey, null, null, false, null, null);

        }

        return chainObject;
    }

    // check
    /**
     * @param properties the properties
     * @return true if containing the
     *         {@link ChainObjectDataManager#SAVED_CHAINOBJECT_TAG}
     */
    public static boolean isAChainObject(Properties properties) {
        return properties.getProperty(OBJECT_TYPE_KEY).contains(SAVED_CHAINOBJECT_TAG);
    }

}
