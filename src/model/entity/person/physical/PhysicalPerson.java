package model.entity.person.physical;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDate;
import java.util.List;

import model.entity.person.Person;

public class PhysicalPerson extends Person {
    private List<String> forNames;

    public PhysicalPerson(PrivateKey privateKey, PublicKey publicKey, String name, LocalDate birthDate,
            boolean isVerified, List<String> forNames) {
        super(privateKey, publicKey, name, birthDate, isVerified);
        this.forNames = forNames;
    }

    public List<String> getForNames() {
        return forNames;
    }

}
