package model.dao.blockchain.chainobject.person;

import java.io.File;
import java.security.PrivateKey;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;

import model.dao.DataManager;
import model.dao.blockchain.chainobject.ChainObjectDataManager;
import model.dto.blockchain.chainobject.ChainObject;
import model.dto.blockchain.chainobject.person.Person;
import model.dto.blockchain.chainobject.person.physical.PhysicalPerson;

public abstract class PersonDataManager extends ChainObjectDataManager {
    public static final Properties PERSON_PROPERTIES = DataManager
            .read(DataManager.INIT_PROPERTIES.getProperty("PERSON_PROPERTIES"));

    public static final String SAVED_PERSON_TAG = PERSON_PROPERTIES.getProperty("SAVED_PERSON_TAG");
    public static final String SAVED_PHYSICALPERSON_TAG = PERSON_PROPERTIES.getProperty("SAVED_PHYSICALPERSON_TAG");

    public static ArrayList<Person> getAllPerson() {
        return null;
    }

    // save
    public static Properties personToAProperties(Person person) {
        Properties properties = ChainObjectDataManager.chainObjectToAProperties(person);
        properties.setProperty(OBJECT_TYPE_KEY, properties.getProperty(OBJECT_TYPE_KEY) + SAVED_PERSON_TAG);

        saveAnObjectInAProperties("lastName", properties, person.getLastName(), person.getPublicKey());
        saveAnObjectInAProperties("birthDate", properties, person.getBirthDate(), person.getPublicKey());
        saveAnObjectInAProperties("isVerified", properties, person.isVerified(), person.getPublicKey());

        return properties;
    }

    // read
    // person
    private static Person personReadFromProperties(Properties properties, PrivateKey privateKey) {
        ChainObject chainObject = ChainObjectDataManager.chainObjectReadFromProperties(properties, privateKey);
        if (properties.getProperty(OBJECT_TYPE_KEY).contains(SAVED_PERSON_TAG) && chainObject != null) {

            String lastName = null;
            LocalDate birDate = null;
            boolean isVerified = false;

            if (privateKey != null) {
                lastName = (String) ChainObjectDataManager.readAObjectSavedInPropertes("lastName", properties,
                        privateKey);
                birDate = LocalDate
                        .parse((String) ChainObjectDataManager.readAObjectSavedInPropertes("birthDate", properties,
                                privateKey));
                isVerified = Boolean.parseBoolean(
                        (String) ChainObjectDataManager.readAObjectSavedInPropertes("isVerified", properties,
                                privateKey));
            }

            Person person = (Person) new PhysicalPerson(chainObject.getPrivateKey(), chainObject.getPublicKey(),
                    lastName, birDate,
                    isVerified, null);

            return person;
        }

        return null;
    }

    // physicalPerson
    public static PhysicalPerson physicalPersonReadFromFile(File file, PrivateKey privateKey) {
        return physicalPersonReadFromProperties(DataManager.read(file), privateKey);

    }

    public static PhysicalPerson physicalPersonReadFromProperties(Properties properties, PrivateKey privateKey) {
        Person person = personReadFromProperties(properties, privateKey);
        if (person != null && properties.getProperty(OBJECT_TYPE_KEY).contains(SAVED_PHYSICALPERSON_TAG)) {

        }
        return null;
    }

}
