package model.controleurs.blockchain.chainobject.person;

import java.security.PrivateKey;
import java.time.LocalDate;
import java.util.ArrayList;

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

        String[] localDateTab = (localDate.replace('/', '-')).split("-");
        localDate = localDateTab[2] + "-" + localDateTab[1] + "-" + localDateTab[0];
        localDate = LocalDate.parse(localDate).toString();
        String stringKey = name.toLowerCase().replace(' ', '_')
                + forNames.toLowerCase().replace(' ', '_')
                + localDate
                + passPhrase;

        PrivateKey privateKey = BlockchainDataMaganager.generatePrivateKeyFromString(stringKey);

        return privateKey;
    }

    public boolean passPhraseIsInCorectFormat(String passPhrase) {
        boolean isCorrect = false;
        if (passPhrase != null) {
            if (passPhrase.length() >= 8) {
                if (!passPhrase.contains(" ") && DataManager.wordContainUpperCase(passPhrase)
                        && DataManager.wordContainSpecialChar(passPhrase)) {
                    isCorrect = true;
                }
            }
        }

        return isCorrect;
    }

    public void close() {
        PersonDataManager.saveAPhysicalPerson(identity);
    }
}
