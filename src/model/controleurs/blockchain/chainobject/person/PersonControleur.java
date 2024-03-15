package model.controleurs.blockchain.chainobject.person;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Arrays;

import model.dao.DataManager;
import model.dao.blockchain.BlockchainDataMaganager;
import model.dao.blockchain.chainobject.person.PersonDataManager;
import model.dto.blockchain.chainobject.person.Person;
import model.dto.blockchain.chainobject.person.physical.PhysicalPerson;

public class PersonControleur {
    ArrayList<Person> otherPerson;

    PhysicalPerson identity;

    public PersonControleur(PrivateKey privateKey) {
        otherPerson = new ArrayList<Person>();
        setIdentity(privateKey);
    }

    public PersonControleur() {
        this(null);
    }

    public PhysicalPerson getIdentity() {
        return identity;
    }

    public boolean setIdentity(PrivateKey privateKey) {
        if (privateKey != null) {
            identity = PersonDataManager.getPhysicalPersonWithPrivateKey(privateKey);

            return true;
        }

        return false;
    }

    public PrivateKey createAPersonPrivateKeyWithAtribute(String name, String forNames, String localDate,
            String passPhrase) {

        PrivateKey privateKey = null;

        if (DataManager.passPhraseIsInCorectFormat(passPhrase)) {
            String stringKey = name.toLowerCase().replace(" ", DataManager.SAVED_LIST_SPACE)
                    + forNames.toLowerCase().replace(" ", DataManager.SAVED_LIST_SPACE)
                    + DataManager.parseDate(localDate).toString()
                    + passPhrase;

            privateKey = BlockchainDataMaganager.generatePrivateKeyFromString(stringKey);
        }

        return privateKey;
    }

    public boolean setIdentityAsCreatedIdentity(String name, String forNames, String localDate, String passPhrase,
            String nationality) {
        boolean created = false;
        if (DataManager.passPhraseIsInCorectFormat(passPhrase)) {
            PrivateKey privateKey = createAPersonPrivateKeyWithAtribute(name, forNames, localDate, passPhrase);
            PhysicalPerson physicalPerson = new PhysicalPerson(privateKey,
                    BlockchainDataMaganager.getPublicKeyFromPrivateKey(privateKey), name,
                    DataManager.parseDate(localDate), false, new ArrayList<>(Arrays.asList(forNames.split(" "))),
                    nationality);

            PersonDataManager.saveAPhysicalPerson(physicalPerson);
            this.identity = physicalPerson;

            created = true;
        }

        return created;
    }

    public void close() {
        PersonDataManager.saveAPhysicalPerson(identity);
    }
}
