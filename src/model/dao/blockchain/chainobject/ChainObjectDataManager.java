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

public abstract class ChainObjectDataManager extends DataManager {

    public static final Properties CHAINOBJECT_PROPERTIES = DataManager
            .read(DataManager.INIT_PROPERTIES.getProperty("CHAINOBJECT_PROPERTIES"));
    public static final String SAVED_PRIVATE_KEY_TAG = CHAINOBJECT_PROPERTIES.getProperty("SAVED_PRIVATE_KEY_TAG");
    public static final String SAVED_PUBLIC_KEY_TAG = CHAINOBJECT_PROPERTIES.getProperty("SAVED_PUBLIC_KEY_TAG");
    public static final String SAVED_CHAINOBJECT_TAG = CHAINOBJECT_PROPERTIES.getProperty("SAVED_CHAINOBJECT_TAG");
    public static final String CHAINOBJECT_SRCFOLDER = CHAINOBJECT_PROPERTIES.getProperty("CHAINOBJECT_SRCFOLDER");

    // save
    public static Properties chainObjectToAProperties(ChainObject chainObject) {
        if (chainObject != null) {
            Properties properties = new Properties();
            properties.setProperty(SAVED_PUBLIC_KEY_TAG + OBJECT_TYPE_KEY, SAVED_CHAINOBJECT_TAG);
            if (chainObject.getPublicKey() != null) {
                properties.setProperty(SAVED_PRIVATE_KEY_TAG + "privateKey",
                        BlockchainDataMaganager.encryptWithPublicKey(
                                BlockchainDataMaganager.privateKeyToString(chainObject.getPrivateKey()),
                                chainObject.getPublicKey()));
            }
            properties.setProperty(SAVED_PUBLIC_KEY_TAG + "publicKey",
                    BlockchainDataMaganager.publicKeyToString(chainObject.getPublicKey()));

            return properties;
        }

        return null;
    }

    public static void saveAnObjectInAProperties(String key, Properties properties, Object o, PrivateKey privateKey) {
        if (o instanceof ChainObject) {
            ChainObject co = (ChainObject) o;
            if (co.getPrivateKey() != null) {
                saveAnStringInAProperties(key, SAVED_CHAINOBJECT_TAG, properties,
                        BlockchainDataMaganager.privateKeyToString(co.getPrivateKey()),
                        privateKey);
            } else {
                saveAnStringInAProperties(key, SAVED_CHAINOBJECT_TAG, properties,
                        BlockchainDataMaganager.publicKeyToString(co.getPublicKey()),
                        privateKey);
            }
        } else if (o instanceof ArrayList) {

            @SuppressWarnings("unchecked")
            ArrayList<Object> oArrayList = (ArrayList<Object>) o;
            saveAnStringInAProperties(key, SAVED_LIST_SPACE, properties,
                    DataManager.objectCollectionToAString(oArrayList),
                    privateKey);

        } else if (o instanceof HashMap) {
            @SuppressWarnings("unchecked")
            HashMap<Object, Object> oHashMap = (HashMap<Object, Object>) o;
            saveAnStringInAProperties(key, SAVED_LIST_SPACE, properties, DataManager.objectHashMapToAString(
                    oHashMap), privateKey);
        } else if (o instanceof PrivateKey) {
            PrivateKey po = (PrivateKey) o;
            saveAnStringInAProperties(key, SAVED_PRIVATE_KEY_TAG, properties,
                    BlockchainDataMaganager.privateKeyToString(po),
                    privateKey);

        } else if (o instanceof PublicKey) {
            PublicKey po = (PublicKey) o;
            saveAnStringInAProperties(key, SAVED_PUBLIC_KEY_TAG, properties,
                    BlockchainDataMaganager.publicKeyToString(po),
                    privateKey);
        } else {
            saveAnStringInAProperties(key, "", properties, o.toString(), privateKey);
        }
    }

    private static void saveAnStringInAProperties(String key, String tag, Properties properties, String string,
            PrivateKey privateKey) {

        if (privateKey != null) {
            key = SAVED_PRIVATE_KEY_TAG + tag + key;
            string = BlockchainDataMaganager.encryptWithPublicKey(string,
                    BlockchainDataMaganager.getPublicKeyFromPrivateKey(privateKey));
        } else {
            key = SAVED_PUBLIC_KEY_TAG + tag + key;
        }

        properties.setProperty(key, string);
    }

    // read
    private static String readAStringSavedInProperties(String key, Properties properties, PrivateKey privateKey) {
        String value = null;
        if (properties.getProperty(key).contains(SAVED_PRIVATE_KEY_TAG)) {
            value = BlockchainDataMaganager.decryptWithPrivateKey(properties.getProperty(key), privateKey);
        } else if (properties.getProperty(key).contains(SAVED_PUBLIC_KEY_TAG)) {
            value = properties.getProperty(key);
        }

        return value;
    }

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

    public static ChainObject chainObjectReadFrom(File file, PrivateKey privateKey) {
        Properties properties = DataManager.read(file);
        ChainObject chainObject = null;
        if (properties.getProperty(OBJECT_TYPE_KEY).contains(SAVED_CHAINOBJECT_TAG)) {
            PublicKey publicKey = BlockchainDataMaganager.stringToPublicKey(
                    properties.getProperty(SAVED_PUBLIC_KEY_TAG + "publicKey"));
            chainObject = (ChainObject) new PhysicalPerson(privateKey, publicKey, null, null, false, null);

        }

        return chainObject;
    }

    public static ChainObject chainObjectReadFrom(File file, PublicKey publicKey) {
        Properties properties = DataManager.read(file);
        ChainObject chainObject = null;
        if (properties.getProperty(OBJECT_TYPE_KEY).contains(SAVED_CHAINOBJECT_TAG)) {
            chainObject = (ChainObject) new PhysicalPerson(null, publicKey, null, null, false, null);
        }

        return chainObject;
    }

    // tag manager

    public static boolean isAChainObject(Properties properties) {
        return properties.getProperty(OBJECT_TYPE_KEY).contains(SAVED_CHAINOBJECT_TAG);
    }

    // data manager

    public static String objectArrayListToStringWithSpace(ArrayList<Object> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object o : list) {
            stringBuilder.append(o.toString());
            stringBuilder.append(SAVED_LIST_SPACE);
        }
        return stringBuilder.toString();
    }

}
