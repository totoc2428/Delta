package model.dao.blockchain.chainobject.person;

import java.util.Properties;

import model.dao.DataManager;
import model.dao.blockchain.chainobject.ChainObjectDataManager;

public abstract class PersonDataManager extends ChainObjectDataManager {
    public static final Properties PERSON_PROPERTIES = DataManager
            .read(DataManager.INIT_PROPERTIES.getProperty("PERSON_PROPERTIES"));

}
