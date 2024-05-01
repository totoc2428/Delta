package main.model.dao.blockchain.chainobject.person;

import java.io.File;
import java.nio.file.Paths;
import java.security.Key;
import java.security.PrivateKey;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;

import exception.model.dao.blockchain.encryptor.GenerateEncryptorBlockchainDataManagerException;
import exception.model.dto.blockchain.chainObject.ChainObjectException;
import main.model.dao.DataManager;
import main.model.dao.blockchain.BlockchainDataManager;
import main.model.dao.blockchain.chainobject.ChainObjectDataManager;
import main.model.dto.blockchain.chainobject.ChainObject;
import main.model.dto.blockchain.chainobject.person.Person;
import main.model.dto.blockchain.chainobject.person.physical.PhysicalPerson;

public abstract class PersonDataManager extends ChainObjectDataManager {
    protected static final Properties PERSON_PROPERTIES = read(CHAINOBJECT_PROPERTIES.getProperty("PERSON_PROPERTIES"));

    private static final String SAVED_PERSON_TAG = PERSON_PROPERTIES.getProperty("SAVED_PERSON_TAG");
    private static final String SAVED_PHYSICALPERSON_TAG = PERSON_PROPERTIES.getProperty("SAVED_PHYSICALPERSON_TAG");

    private static final String PERSON_FILE_SAVED_TAG = PERSON_PROPERTIES.getProperty("PERSON_FILE_SAVED_TAG");
    private static final String PHYSICALPERSON_FILE_SAVED_TAG = PERSON_PROPERTIES
            .getProperty("PHYSICALPERSON_FILE_SAVED_TAG");

    private static String personSrcPath = PERSON_PROPERTIES.getProperty("personSrcPath");

    /////////// GET
    public static PhysicalPerson getPhysicalPersonWithPrivateKey(PrivateKey privateKey) throws ChainObjectException {
        String fileName = personSrcPath + File.separator + privateKeyToString(privateKey)
                + PHYSICALPERSON_FILE_SAVED_TAG + PERSON_FILE_SAVED_TAG
                + SAVED_CHAINOBJECT_TAG;
        if (fileExist(fileName)) {
            return physicalPersonReadFromFile(Paths.get(fileName).toFile(), privateKey);
        } else {
            return null;
        }
    }

    public static String getPersonSrcPath() {
        return personSrcPath;
    }

    /////////// SAVE
    private static Properties personToAProperties(Person person, Key encryptor, String savedTypeTag) {
        Properties properties = ChainObjectDataManager.chainObjectToAProperties(person, encryptor,
                savedTypeTag + SAVED_PERSON_TAG);

        saveAnObjectInAProperties("lastName", properties, person.getLastName(), null);
        saveAnObjectInAProperties("birthDate", properties, person.getBirthDate(), null);
        saveAnObjectInAProperties("isVerified", properties, person.isVerified(), null);

        return properties;
    }

    private static Properties physicalPersonToAProperties(PhysicalPerson physicalPerson, Key encryptor,
            String savedTypeTage) {
        Properties properties = personToAProperties(physicalPerson, encryptor,
                SAVED_PHYSICALPERSON_TAG + savedTypeTage);

        saveAnObjectInAProperties("forNames", properties, physicalPerson.getForNames(), null);

        return properties;
    }

    private static void saveAPerson(Properties personProperties, String fileName) {
        saveChainObject(personProperties,
                personSrcPath + BlockchainDataManager.sha256Hash(fileName) + PERSON_FILE_SAVED_TAG);
    }

    public static void saveAPhysicalPerson(PhysicalPerson physicalPerson)
            throws GenerateEncryptorBlockchainDataManagerException {
        saveAPerson(physicalPersonToAProperties(physicalPerson, generateEncyptor(), ""),
                publicKeyToString(physicalPerson.getPublicKey()) + PHYSICALPERSON_FILE_SAVED_TAG);
    }

    /////////// READ
    // person
    private static Person personReadFromProperties(Properties properties, PrivateKey privateKey)
            throws ChainObjectException {
        ChainObject chainObject = ChainObjectDataManager.chainObjectReadFromProperties(properties, privateKey);

        if (properties.getProperty(SAVED_PUBLIC_VALUE_TAG + OBJECT_TYPE_KEY).contains(SAVED_PERSON_TAG)
                && chainObject != null) {

            String lastName = (String) ChainObjectDataManager.readAObjectSavedInPropertes("lastName", properties,
                    privateKey);
            LocalDate birDate = LocalDate
                    .parse((String) ChainObjectDataManager.readAObjectSavedInPropertes("birthDate", properties,
                            privateKey));
            boolean isVerified = Boolean.parseBoolean(
                    (String) ChainObjectDataManager.readAObjectSavedInPropertes("isVerified", properties,
                            privateKey));
            String nationality = (String) ChainObjectDataManager.readAObjectSavedInPropertes("nationality", properties,
                    privateKey);

            Person person = (Person) new PhysicalPerson(chainObject.getPrivateKey(), chainObject.getPublicKey(),
                    lastName, birDate, isVerified, null, nationality);

            return person;

        }

        return null;
    }

    // physicalPerson
    public static PhysicalPerson physicalPersonReadFromFile(File file, PrivateKey privateKey)
            throws ChainObjectException {
        return physicalPersonReadFromProperties(DataManager.read(file), privateKey);

    }

    public static PhysicalPerson physicalPersonReadFromProperties(Properties properties, PrivateKey privateKey)
            throws ChainObjectException {
        Person person = personReadFromProperties(properties, privateKey);
        if (person != null && properties.getProperty(
                SAVED_PUBLIC_VALUE_TAG + OBJECT_TYPE_KEY).contains(SAVED_PHYSICALPERSON_TAG)) {

            @SuppressWarnings("unchecked")
            ArrayList<String> forNames = (ArrayList<String>) readAObjectSavedInPropertes("forNames", properties,
                    privateKey);
            return new PhysicalPerson(person.getPrivateKey(), person.getPublicKey(), person.getLastName(),
                    person.getBirthDate(), person.isVerified(), forNames, person.getNationality());
        }
        return null;
    }

    /////////// CONFIG
    public static void setSrcPath(String srcPath) {
        if (DataManager.fileExist(srcPath) && DataManager.fileIsDirectory(srcPath)) {
            personSrcPath = srcPath;
        }
    }

    /////////// CHECK
    private static boolean isPerson(Properties properties) {
        return properties.getProperty(SAVED_PUBLIC_VALUE_TAG + OBJECT_TYPE_KEY).contains(SAVED_PERSON_TAG);
    }

    private static boolean isPhysicalPerson(Properties properties) {
        if (isPerson(properties)) {
            return properties.getProperty(SAVED_PUBLIC_VALUE_TAG + OBJECT_TYPE_KEY).contains(SAVED_PHYSICALPERSON_TAG);
        } else {
            return false;
        }

    }

}
