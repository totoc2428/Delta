package model.dto.blockchain.chainobject.person.physical;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDate;
import java.util.ArrayList;

import model.dto.blockchain.chainobject.person.Person;

public class PhysicalPerson extends Person {
    private ArrayList<String> forNames;

    public PhysicalPerson(PrivateKey privateKey, PublicKey publicKey, String lastName, LocalDate birthData,
            boolean isVerified, ArrayList<String> forNames, String nationality) {
        super(privateKey, publicKey, lastName, birthData, isVerified, nationality);

        this.forNames = forNames;
    }

    public ArrayList<String> getForNames() {
        return forNames;
    }

}
