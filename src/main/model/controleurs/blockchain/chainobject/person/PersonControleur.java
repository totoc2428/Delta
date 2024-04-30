package main.model.controleurs.blockchain.chainobject.person;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Arrays;

import main.model.dao.DataManager;
import main.model.dao.blockchain.BlockchainDataMaganager;
import main.model.dao.blockchain.chainobject.person.PersonDataManager;
import main.model.dto.blockchain.chainobject.person.Person;
import main.model.dto.blockchain.chainobject.person.physical.PhysicalPerson;
import exception.model.dao.createprivateKey.BlockchainDataManagerCreatePrivateKeyDateIsNullException;
import exception.model.dao.createprivateKey.BlockchainDataManagerCreatePrivateKeyException;
import exception.model.dao.createprivateKey.BlockchainDataManagerCreatePrivateKeyForNamesIsNullException;
import exception.model.dao.createprivateKey.BlockchainDataManagerCreatePrivateKeyNameIsNullException;
import exception.model.dao.createprivateKey.BlockchainDataManagerCreatePrivateKeyPassPhraseIsNullException;
import exception.model.dto.blockchain.chainObject.ChainObjectException;

public class PersonControleur {
    ArrayList<Person> otherPerson;

    PhysicalPerson identity;

    public PersonControleur(PrivateKey privateKey) throws ChainObjectException {
        otherPerson = new ArrayList<Person>();
        setIdentity(privateKey);
    }

    public PersonControleur() throws ChainObjectException {
        this(null);
    }

    public PhysicalPerson getIdentity() {
        return identity;
    }

    public boolean setIdentity(PrivateKey privateKey) throws ChainObjectException {
        if (privateKey != null) {
            identity = PersonDataManager.getPhysicalPersonWithPrivateKey(privateKey);
            return true;
        }

        return false;
    }

    public PrivateKey createAPersonPrivateKeyWithAtribute(String name, String forNames, String localDate,
            String passPhrase) throws BlockchainDataManagerCreatePrivateKeyException {

        PrivateKey privateKey = null;

        if (name == null) {
            throw new BlockchainDataManagerCreatePrivateKeyNameIsNullException();
        }
        if (forNames == null) {
            throw new BlockchainDataManagerCreatePrivateKeyForNamesIsNullException();
        }
        if (localDate == null) {
            throw new BlockchainDataManagerCreatePrivateKeyDateIsNullException();
        }
        if (passPhrase == null) {
            throw new BlockchainDataManagerCreatePrivateKeyPassPhraseIsNullException();
        }

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
            String nationality) throws ChainObjectException, BlockchainDataManagerCreatePrivateKeyException {
        boolean seted = false;
        if (DataManager.passPhraseIsInCorectFormat(passPhrase)) {
            PrivateKey privateKey;
            privateKey = createAPersonPrivateKeyWithAtribute(name, forNames, localDate, passPhrase);
            PhysicalPerson physicalPerson = new PhysicalPerson(privateKey,
                    BlockchainDataMaganager.getPublicKeyFromPrivateKey(privateKey), name,
                    DataManager.parseDate(localDate), false, new ArrayList<>(Arrays.asList(forNames.split(" "))),
                    nationality);
            PersonDataManager.saveAPhysicalPerson(physicalPerson);
            this.identity = physicalPerson;
            seted = true;
        }

        return seted;
    }

    public void close() {
        PersonDataManager.saveAPhysicalPerson(identity);
    }
}
