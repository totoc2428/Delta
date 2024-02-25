package model.dao.blockchain.chainobject.person;

import java.util.ArrayList;
import java.util.Properties;

import model.dao.DataManager;
import model.dao.blockchain.chainobject.ChainObjectDataManager;
import model.dto.blockchain.chainobject.person.Person;

public abstract class PersonDataManager extends ChainObjectDataManager {
    public static final Properties PERSON_PROPERTIES = DataManager
            .read(DataManager.INIT_PROPERTIES.getProperty("PERSON_PROPERTIES"));

    public static ArrayList<Person> getAllPerson() {
        return null;
    }

}
