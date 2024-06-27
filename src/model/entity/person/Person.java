package model.entity.person;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDate;

import model.entity.ChainObject;

public class Person extends ChainObject {
    private String name;
    private LocalDate brithDate;

    private boolean isVerified;

    public Person(PrivateKey privateKey, PublicKey publicKey, String name, LocalDate birthDate, boolean isVerified) {
        super(privateKey, publicKey);
        this.name = name;
        this.brithDate = birthDate;
        this.isVerified = isVerified;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBrithDate() {
        return brithDate;
    }

    public boolean isVerified() {
        return isVerified;
    }
}
