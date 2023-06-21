package serveur.util.ChainObject.person.user;

import java.time.LocalDate;

import serveur.util.ChainObject.ChainObject;
import serveur.util.security.Key;

public class Account extends ChainObject {
    String name;
    LocalDate birthDate;

    public Account(Key signature, String name, LocalDate birthDate) {
        super(signature);
        this.name = name;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
}
