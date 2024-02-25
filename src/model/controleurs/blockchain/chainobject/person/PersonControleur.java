package model.controleurs.blockchain.chainobject.person;

import java.security.PrivateKey;
import java.util.ArrayList;

import model.dao.blockchain.chainobject.person.PersonDataManager;
import model.dto.blockchain.chainobject.person.Person;

public class PersonControleur {

    ArrayList<Person> persons;

    Person identity;

    public PersonControleur(PrivateKey privateKey) {
        this.persons = PersonDataManager.getAllPerson();
        if (privateKey != null) {
            // this.identity = persons.get();

        }
    }

    public PersonControleur() {
        this(null);
    }

    public Person getIdentity() {
        return identity;
    }
}
