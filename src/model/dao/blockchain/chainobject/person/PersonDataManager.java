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
    protected static final Properties PERSON_PROPERTIES = read(CHAINOBJECT_PROPERTIES.getProperty("PERSON_PROPERTIES"));

    private static final String SAVED_PERSON_TAG = PERSON_PROPERTIES.getProperty("SAVED_PERSON_TAG");
    private static final String SAVED_PHYSICALPERSON_TAG = PERSON_PROPERTIES.getProperty("SAVED_PHYSICALPERSON_TAG");

    private static final String PERSON_FILE_SAVED_TAG = PERSON_PROPERTIES.getProperty("PERSON_FILE_SAVED_TAG");
    private static final String PHYSICALPERSON_FILE_SAVED_TAG = PERSON_PROPERTIES
            .getProperty("PHYSICALPERSON_FILE_SAVED_TAG");

    private static String personSrcPath;

    public static PhysicalPerson getPhysicalPersonWithPrivateKey(PrivateKey privateKey) {
        return null;
    }

    // save
    private static Properties personToAProperties(Person person) {
        Properties properties = ChainObjectDataManager.chainObjectToAProperties(person);
        properties.setProperty(OBJECT_TYPE_KEY, properties.getProperty(OBJECT_TYPE_KEY) + SAVED_PERSON_TAG);

        saveAnObjectInAProperties("lastName", properties, person.getLastName(), null);
        saveAnObjectInAProperties("birthDate", properties, person.getBirthDate(), null);
        saveAnObjectInAProperties("isVerified", properties, person.isVerified(), null);

        return properties;
    }

    private static Properties physicalPersonToAProperties(PhysicalPerson physicalPerson) {
        Properties properties = personToAProperties(physicalPerson);
        properties.setProperty(OBJECT_TYPE_KEY, properties.getProperty(OBJECT_TYPE_KEY) + SAVED_PHYSICALPERSON_TAG);

        saveAnObjectInAProperties("forNames", properties, properties, null);

        return properties;
    }

    private static void saveAPerson(Properties personProperties, String fileName) {
        save(personProperties, personSrcPath + fileName + PERSON_FILE_SAVED_TAG);
    }

    public void saveAPhysicalPerson(PhysicalPerson physicalPerson) {
        saveAPerson(physicalPersonToAProperties(physicalPerson),
                publicKeyToString(physicalPerson.getPublicKey()) + PHYSICALPERSON_FILE_SAVED_TAG);
    }

    // read
    // person
    private static Person personReadFromProperties(Properties properties, PrivateKey privateKey) {
        ChainObject chainObject = ChainObjectDataManager.chainObjectReadFromProperties(properties, privateKey);

        if (properties.getProperty(OBJECT_TYPE_KEY).contains(SAVED_PERSON_TAG) && chainObject != null) {

            String lastName = (String) ChainObjectDataManager.readAObjectSavedInPropertes("lastName", properties,
                    privateKey);
            LocalDate birDate = LocalDate
                    .parse((String) ChainObjectDataManager.readAObjectSavedInPropertes("birthDate", properties,
                            privateKey));
            boolean isVerified = Boolean.parseBoolean(
                    (String) ChainObjectDataManager.readAObjectSavedInPropertes("isVerified", properties,
                            privateKey));

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

            @SuppressWarnings("unchecked")
            ArrayList<String> forNames = (ArrayList<String>) readAObjectSavedInPropertes("forNames", properties,
                    privateKey);

            return new PhysicalPerson(person.getPrivateKey(), person.getPublicKey(), person.getLastName(),
                    person.getBirthDate(), person.isVerified(), forNames);
        }
        return null;
    }

    // config
    public void setSrcPath(String srcPath) {
        if (DataManager.fileExist(srcPath) && DataManager.fileIsDirectory(srcPath)) {
            personSrcPath = srcPath;
        }
    }
}
