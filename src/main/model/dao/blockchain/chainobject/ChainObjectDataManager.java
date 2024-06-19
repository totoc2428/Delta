package main.model.dao.blockchain.chainobject;

import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Properties;

import exception.SystemException;
import exception.model.dto.blockchain.chainObject.ChainObjectException;
import main.model.dao.DataManager;
import main.model.dao.blockchain.BlockchainDataManager;
import main.model.dto.blockchain.chainobject.ChainObject;
import main.model.dto.blockchain.chainobject.person.physical.PhysicalPerson;
import main.util.style.TerminalStyle;

public abstract class ChainObjectDataManager extends BlockchainDataManager {

    public static final Properties CHAINOBJECT_PROPERTIES = read(
            BLOCKCHAIN_PROPERTIES.getProperty("CHAINOBJECT_PROPERTIES"));

    public static final String SAVED_PRIVATE_VALUE_TAG = CHAINOBJECT_PROPERTIES
            .getProperty("SAVED_PRIVATE_VALUE_TAG");
    public static final String SAVED_PUBLIC_VALUE_TAG = CHAINOBJECT_PROPERTIES.getProperty("SAVED_PUBLIC_VALUE_TAG");

    public static final String SAVED_CHAINOBJECT_TAG = CHAINOBJECT_PROPERTIES.getProperty("SAVED_CHAINOBJECT_TAG");

    public static final String SAVED_ENCRYPTOR_KEY = CHAINOBJECT_PROPERTIES.getProperty("SAVED_ENCRYPTOR_KEY");

    public static final String CHAINOBJECT_FILE_SAVED_TAG = CHAINOBJECT_PROPERTIES
            .getProperty("CHAINOBJECT_FILE_SAVED_TAG");

    private static String chainObjectSrcPath = CHAINOBJECT_PROPERTIES.getProperty("chainObjectSrcPath");

    // save
    /**
     * Take a ChainObject to convert in a properties format. Is prepared to save any
     * chainObject.
     * 
     * @param chainObject the chainObject you want to convert in to a properties.
     * @return the chainObject into a Properties format.
     */
    public static Properties chainObjectToAProperties(ChainObject chainObject, String savedTypeTag)
            throws SystemException {
        if (savedTypeTag == null) {
            savedTypeTag = "";
        }
        if (chainObject != null) {
            Properties properties = new Properties();

            saveAnStringInAProperties(OBJECT_TYPE_KEY, "", properties, SAVED_CHAINOBJECT_TAG + savedTypeTag, null);

            // String strEncryptor = convertKeyToString(encryptor);

            saveAnStringInAProperties(SAVED_ENCRYPTOR_KEY, "", properties,
                    "strEncryptor", chainObject.getPublicKey());

            String strPublicKey = publicKeyToString(chainObject.getPublicKey());
            saveAnStringInAProperties("publicKey", "", properties, strPublicKey, null);

            String strPrivateKey = privateKeyToString(chainObject.getPrivateKey());
            saveAnStringInAProperties("privateKey", "", properties, strPrivateKey, chainObject.getPublicKey());

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
            PublicKey encryptor) throws SystemException {
        if (object instanceof ChainObject) {
            ChainObject co = (ChainObject) object;
            if (co.getPrivateKey() != null) {
                saveAnStringInAProperties(key, SAVED_CHAINOBJECT_TAG, properties,
                        privateKeyToString(co.getPrivateKey()), encryptor);
            } else {
                saveAnStringInAProperties(key, SAVED_CHAINOBJECT_TAG, properties, publicKeyToString(co.getPublicKey()),
                        encryptor);
            }
        } else if (object instanceof ArrayList) {
            @SuppressWarnings("unchecked")
            ArrayList<Object> oArrayList = (ArrayList<Object>) object;
            saveAnStringInAProperties(key, SAVED_LIST_TAG, properties, objectCollectionToAString(oArrayList),
                    encryptor);

        } else if (object instanceof HashMap) {
            @SuppressWarnings("unchecked")
            HashMap<Object, Object> oHashMap = (HashMap<Object, Object>) object;
            saveAnStringInAProperties(key, SAVED_DIC_TAG, properties, objectHashMapToString(oHashMap), encryptor);
        } else if (object instanceof PrivateKey) {
            PrivateKey po = (PrivateKey) object;
            saveAnStringInAProperties(key, SAVED_PRIVATE_VALUE_TAG, properties, privateKeyToString(po), encryptor);

        } else if (object instanceof PublicKey) {
            PublicKey po = (PublicKey) object;
            saveAnStringInAProperties(key, "", properties, publicKeyToString(po), encryptor);
        } else {
            saveAnStringInAProperties(key, "", properties, object.toString(), encryptor);
        }
    }

    /**
     * @param key        the key
     * @param tag        the tag if this tag contain to recogise any object type.
     * @param properties the properties you want to edit.
     * @param string     the string you want to save in the properties.
     * @param publicKey  the public key to encrypt the object. If you don't want to
     *                   encrypt the object set this parmeter to null.
     * @throws SystemException
     */
    private static void saveAnStringInAProperties(String key, String tag, Properties properties, String string,
            PublicKey publicKey) throws SystemException {
        if (string == null) {
            string = "null";
        }
        if (tag == null) {
            tag = "";
        }
        if (key != null) {
            if (publicKey == null) {
                key = SAVED_PUBLIC_VALUE_TAG + tag + key;
            } else {
                key = SAVED_PRIVATE_VALUE_TAG + tag + key;
                string = encryptWithPublicKey(string, publicKey);
            }
            if (properties.getProperty(key) != null) {
                properties.setProperty(key, string);
            } else {
                properties.put(key, string);
            }
        }
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
        if (properties.getProperty(key).contains(SAVED_PRIVATE_VALUE_TAG)) {
            if (privateKey != null) {
                value = decryptWithPrivateKey(properties.getProperty(key), privateKey);
            }
        } else if (properties.getProperty(key).contains(SAVED_PUBLIC_VALUE_TAG)) {
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
            PublicKey publicKey = null;
            try {
                publicKey = stringToPublicKey(properties.getProperty(SAVED_PUBLIC_VALUE_TAG + "publicKey"));
            } catch (SystemException e) {
                e.printStackTrace();
            }
            try {
                chainObject = (ChainObject) new PhysicalPerson(privateKey, publicKey, null, null, false, null, null);
            } catch (ChainObjectException e) {
                // TODO: handle exception
                TerminalStyle.showError(e.getMessage());
            }

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

    public static void saveChainObject(Properties personProperties, String fileName) {
        save(personProperties,
                chainObjectSrcPath + fileName + CHAINOBJECT_FILE_SAVED_TAG);
    }

    public static String getChainObjectSrcPath() {
        return chainObjectSrcPath;
    }
}
