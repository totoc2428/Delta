package model.controleurs.blockchain.chainobject.person;

import java.security.PrivateKey;
import java.util.ArrayList;

import model.dao.blockchain.chainobject.person.PersonDataManager;
import model.dto.blockchain.chainobject.person.Person;
import model.dto.blockchain.chainobject.person.physical.PhysicalPerson;

public class PersonControleur {
    ArrayList<Person> otherPerson;

    PhysicalPerson identity;

    public PersonControleur(PrivateKey privateKey) {
        otherPerson = new ArrayList<Person>();

        if (privateKey != null) {
            identity = PersonDataManager.getPhysicalPersonWithPrivateKey(privateKey);
        }
    }

    public PersonControleur() {
        this(null);
    }

    public PhysicalPerson getIdentity() {
        return identity;
    }

    public void close() {
        PersonDataManager.saveAPhysicalPerson(identity);
    }
}
