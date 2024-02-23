package model.dao.blockchain.chainobject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import model.dao.DataManager;
import model.dto.blockchain.chainobject.ChainObject;

public abstract class ChainObjectDataManager extends DataManager {

    public static final Properties BLOCKCHAIN_PROPERTIES = DataManager
            .read(DataManager.INIT_PROPERTIES.getProperty("BLOCKCHAIN_PROPERTIES"));

    public static final String SAVED_PRIVATE_KEY_TAG = BLOCKCHAIN_PROPERTIES.getProperty("SAVED_PRIVATE_KEY_TAG");
    public static final String SAVED_PUBLIC_KEY_TAG = BLOCKCHAIN_PROPERTIES.getProperty("SAVED_PUBLIC_KEY_TAG");
    public static final String SAVED_CHAINOBJECT_TAG = BLOCKCHAIN_PROPERTIES.getProperty("SAVED_CHAINOBJECT_TAG");

    @SuppressWarnings("unchecked")
    public static Properties chainObjectToProperties(Object objet) {
        Properties properties = new Properties();
        if (objet instanceof ChainObject) {
            Class<?> classe = objet.getClass();
            Field[] attributs = classe.getDeclaredFields();
            for (Field attribut : attributs) {
                attribut.setAccessible(true);
                try {
                    Object value = attribut.get(objet);
                    String attributName = attribut.getName();
                    String attributeValue = value.toString();
                    if (value instanceof ArrayList) {
                        attributeValue = objectArrayListToStringWithSpace((ArrayList<Object>) value);
                        attributName += SAVED_LIST_TAG;
                    }
                    if (value instanceof HashMap) {
                        attributeValue = objectHashMapToStringWithSpace((HashMap<Object, Object>) value);
                        attributName += SAVED_DIC_TAG;
                    }

                    properties.setProperty(attributName, attributeValue);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Not a chain Object.");
        }

        return properties;
    }

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
