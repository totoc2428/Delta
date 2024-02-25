package model.dao.blockchain.chainobject.person;

import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Properties;

import model.dao.DataManager;
import model.dao.blockchain.chainobject.ChainObjectDataManager;
import model.dto.blockchain.chainobject.ChainObject;
import model.dto.blockchain.chainobject.person.Person;

public abstract class PersonDataManager extends ChainObjectDataManager {
    public static final Properties PERSON_PROPERTIES = DataManager
            .read(DataManager.INIT_PROPERTIES.getProperty("PERSON_PROPERTIES"));

    public static final String SAVED_PERSON_TAG = PERSON_PROPERTIES.getProperty("SAVED_PERSON_TAG");

    public static ArrayList<Person> getAllPerson() {
        return null;
    }

    public static Properties personToAProperties(Person person) {
        Properties properties = ChainObjectDataManager.chainObjectToAProperties(person);
        properties.setProperty(OBJECT_TYPE_KEY, properties.getProperty(OBJECT_TYPE_KEY) + SAVED_PERSON_TAG);

        saveAnObjectInAProperties("lastName", properties, person.getLastName(), person.getPrivateKey());
        saveAnObjectInAProperties("birthDate", properties, person.getBirthDate(), person.getPrivateKey());
        saveAnObjectInAProperties("isVerified", properties, person.isVerified(), person.getPrivateKey());

        return properties;
    }

    public static Person PersonReadFrom(File file, PrivateKey privateKey) {
        Properties properties = read(file);
        ChainObject chainObject = ChainObjectDataManager.chainObjectReadFrom(file, privateKey);
        Person person = null;
        if (properties.getProperty(OBJECT_TYPE_KEY).contains(SAVED_PERSON_TAG)) {

        }

        return person;
    }

    public static Person PersonReadFrom(File file, PublicKey publicKey) {
        return null;
    }

}
