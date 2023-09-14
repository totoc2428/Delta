package serveur.util.ChainObject.person;

import java.time.LocalDate;

import serveur.util.ChainObject.ChainObject;
import serveur.util.ChainObject.location.Address;
import serveur.util.security.Key;

public abstract class Identity extends ChainObject {
    private String name;
    private LocalDate birthDate;
    private Address address;

    public Identity(Key signature, String name, LocalDate birthDate, Address address) {
        super(signature);
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toWriteFormat() {

        return super.toWriteFormat() + ";" + name + ";" + birthDate + ";" + address.getSignature() + ";";
    }

}
