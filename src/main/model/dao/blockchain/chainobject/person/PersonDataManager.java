package main.model.dao.blockchain.chainobject.person;

import java.io.File;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;

import exception.model.dto.blockchain.chainObject.ChainObjectException;
import io.jsonwebtoken.lang.Arrays;
import main.model.dao.DataManager;
import main.model.dao.blockchain.chainobject.ChainObjectDataManager;
import main.model.dto.blockchain.chainobject.ChainObject;
import main.model.dto.blockchain.chainobject.person.Person;
import main.model.dto.blockchain.chainobject.person.physical.PhysicalPerson;
import main.util.style.TerminalStyle;

public abstract class PersonDataManager extends ChainObjectDataManager {
    protected static final Properties PERSON_PROPERTIES = read(CHAINOBJECT_PROPERTIES.getProperty("PERSON_PROPERTIES"));

    private static final String SAVED_PERSON_TAG = PERSON_PROPERTIES.getProperty("SAVED_PERSON_TAG");
    private static final String SAVED_PHYSICALPERSON_TAG = PERSON_PROPERTIES.getProperty("SAVED_PHYSICALPERSON_TAG");

    private static final String PERSON_FILE_SAVED_TAG = PERSON_PROPERTIES.getProperty("PERSON_FILE_SAVED_TAG");
    private static final String PHYSICALPERSON_FILE_SAVED_TAG = PERSON_PROPERTIES
            .getProperty("PHYSICALPERSON_FILE_SAVED_TAG");

    private static String personSrcPath = PERSON_PROPERTIES.getProperty("personSrcPath");

    ////
    // get
    public static PhysicalPerson getPhysicalPersonWithPrivateKey(PrivateKey privateKey) {
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

    // save
    private static Properties personToAProperties(Person person) {
        Properties properties = ChainObjectDataManager.chainObjectToAProperties(person);
        saveAnObjectInAProperties(OBJECT_TYPE_KEY, properties,
                properties.getProperty(OBJECT_TYPE_KEY) + SAVED_PERSON_TAG, null);

        saveAnObjectInAProperties("lastName", properties, person.getLastName(), null);
        saveAnObjectInAProperties("birthDate", properties, person.getBirthDate(), null);
        saveAnObjectInAProperties("isVerified", properties, person.isVerified(), null);

        return properties;
    }

    private static Properties physicalPersonToAProperties(PhysicalPerson physicalPerson) {
        Properties properties = personToAProperties(physicalPerson);
        saveAnObjectInAProperties(OBJECT_TYPE_KEY, properties,
                properties.getProperty(OBJECT_TYPE_KEY) + SAVED_PHYSICALPERSON_TAG, null);

        saveAnObjectInAProperties("forNames", properties, properties, null);

        return properties;
    }

    private static void saveAPerson(Properties personProperties, String fileName) {
        save(personProperties, personSrcPath + fileName + PERSON_FILE_SAVED_TAG);
    }

    public static void saveAPhysicalPerson(PhysicalPerson physicalPerson) {
        saveAPerson(physicalPersonToAProperties(physicalPerson),
                publicKeyToString(physicalPerson.getPublicKey()) + PHYSICALPERSON_FILE_SAVED_TAG);
    }

    /////////// READ
    // person
    private static Person personReadFromProperties(Properties properties, PrivateKey privateKey) {
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

            try {
                Person person = (Person) new PhysicalPerson(chainObject.getPrivateKey(), chainObject.getPublicKey(),
                        null,
                        lastName, birDate, isVerified, null, nationality);
                return person;
            } catch (ChainObjectException e) {
                // TODO: handle exception
            }

        }

        return null;
    }

    // physicalPerson
    public static PhysicalPerson physicalPersonReadFromFile(File file, PrivateKey privateKey) {
        return physicalPersonReadFromProperties(DataManager.read(file), privateKey);

    }

    public static PhysicalPerson physicalPersonReadFromProperties(Properties properties, PrivateKey privateKey) {
        Person person = personReadFromProperties(properties, privateKey);
        if (person != null && properties.getProperty(
                SAVED_PUBLIC_VALUE_TAG + OBJECT_TYPE_KEY).contains(SAVED_PHYSICALPERSON_TAG)) {

            @SuppressWarnings("unchecked")
            ArrayList<String> forNames = (ArrayList<String>) readAObjectSavedInPropertes("forNames", properties,
                    privateKey);

            try {
                return new PhysicalPerson(person.getPrivateKey(), person.getPublicKey(), null, person.getLastName(),
                        person.getBirthDate(), person.isVerified(), forNames, person.getNationality());
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        return null;
    }

    // config
    public static void setSrcPath(String srcPath) {
        if (DataManager.fileExist(srcPath) && DataManager.fileIsDirectory(srcPath)) {
            personSrcPath = srcPath;
        }
    }

    // check
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

    public static void main(String[] args) {
        TerminalStyle.showInformation("démarage");
        PrivateKey privateKey = PersonDataManager.generatePrivateKeyFromString("test");
        TerminalStyle.showDone("clé privé créer : " + privateKey);
        try {
            PhysicalPerson physicalPerson = new PhysicalPerson(privateKey, getPublicKeyFromPrivateKey(privateKey), null,
                    "test",
                    LocalDate.now(), false, new ArrayList<String>(Arrays.asList(new String[] { "test", "truc" })),
                    "fr");
            TerminalStyle.showDone("identité créer");
            System.out.println(physicalPerson.getPrivateKey());
            System.out.println(physicalPerson.getPublicKey());

            TerminalStyle.showInformation("la on test la clé privé en str");
            String pk = privateKeyToString(privateKey);
            System.out.println(pk);
            System.out.println(encryptWithPublicKey(pk, physicalPerson.getPublicKey()));// TODO vérifier la méthode
                                                                                        // d'encryption
            TerminalStyle.showDone("start save :");

            saveAPhysicalPerson(physicalPerson);
        } catch (ChainObjectException e) {
            TerminalStyle.showError(e.getMessage());
        }

    }

}
