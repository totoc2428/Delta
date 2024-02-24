package model.dao.blockchain.chainobject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import model.dao.DataManager;
import model.dao.blockchain.BlockchainDataMaganager;
import model.dto.blockchain.chainobject.ChainObject;

public abstract class ChainObjectDataManager extends DataManager {

    public static final Properties CHAINOBJECT_PROPERTIES = DataManager
            .read(DataManager.INIT_PROPERTIES.getProperty("CHAINOBJECT_PROPERTIES"));
    public static final String SAVED_PRIVATE_KEY_TAG = CHAINOBJECT_PROPERTIES.getProperty("SAVED_PRIVATE_KEY_TAG");
    public static final String SAVED_PUBLIC_KEY_TAG = CHAINOBJECT_PROPERTIES.getProperty("SAVED_PUBLIC_KEY_TAG");
    public static final String SAVED_CHAINOBJECT_TAG = CHAINOBJECT_PROPERTIES.getProperty("SAVED_CHAINOBJECT_TAG");
    public static final String CHAINOBJECT_SRCFOLDER = CHAINOBJECT_PROPERTIES.getProperty("CHAINOBJECT_SRCFOLDER");

    // save
    public static Properties chainObjectToAProperties(ChainObject chainObject) {
        Properties properties = new Properties();
        properties.setProperty(SAVED_PUBLIC_KEY_TAG + OBJECT_TYPE_KEY, SAVED_CHAINOBJECT_TAG);
        properties.setProperty(SAVED_PRIVATE_KEY_TAG + "privateKey",
                BlockchainDataMaganager.encryptWithPublicKey(
                        BlockchainDataMaganager.privateKeyToString(chainObject.getPrivateKey()),
                        chainObject.getPublicKey()));
        properties.setProperty(SAVED_PUBLIC_KEY_TAG + "publicKey",
                BlockchainDataMaganager.publicKeyToString(chainObject.getPublicKey()));

        return properties;
    }

    public static ChainObject chainObjectRead() {
        return null;
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

    public static String objectHashMapToStringWithSpace(HashMap<Object, Object> dic) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Object o : dic.values()) {
            stringBuilder.append(o.toString());
            stringBuilder.append(SAVED_LIST_SPACE);
        }

        stringBuilder.append(SAVED_DIC_SPACE);

        for (Object o : dic.keySet()) {
            stringBuilder.append(o.toString());
            stringBuilder.append(SAVED_LIST_SPACE);
        }

        return stringBuilder.toString();
    }

    public static HashMap<Object, Object> stringToObjectHashMap(String string) {
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();

        String values = string.split(SAVED_DIC_SPACE)[0];
        String keys = string.split(SAVED_DIC_SPACE)[1];

        for (int i = 0; i < values.length(); i++) {
            hashMap.put(keys.split(SAVED_LIST_SPACE)[i], values.split(SAVED_LIST_SPACE)[i]);
        }

        return hashMap;
    }

    public static ArrayList<Object> stringToObjectArrayList(String string) {
        ArrayList<Object> objects = new ArrayList<Object>();
        for (String str : string.split(SAVED_LIST_SPACE)) {
            objects.add(str);
        }

        return objects;
    }
}
