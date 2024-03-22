package main.model.dto.blockchain.chainobject.person.physical;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDate;
import java.util.ArrayList;

import main.model.dto.blockchain.chainobject.person.Person;

import exception.model.dto.blockchain.chainObject.ChainObjectException;

public class PhysicalPerson extends Person {
    private ArrayList<String> forNames;

    public PhysicalPerson(PrivateKey privateKey, PublicKey publicKey, KeyPair keyPair, String lastName,
            LocalDate birthData,
            boolean isVerified, ArrayList<String> forNames, String nationality) throws ChainObjectException {
        super(privateKey, publicKey, keyPair, lastName, birthData, isVerified, nationality);

        this.forNames = forNames;
    }

    public ArrayList<String> getForNames() {
        return forNames;
    }

}
