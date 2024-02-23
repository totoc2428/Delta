package model.dto.blockchain.chainobject.person;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDate;

import model.dto.blockchain.chainobject.ChainObject;

public class Person extends ChainObject {
    private String name;
    private LocalDate birthDate;
    private boolean isVerified;

    /* constructor */
    public Person(PrivateKey privateKey, PublicKey publicKey, String name, LocalDate birthData, boolean isVerified) {
        super(privateKey, publicKey);

        this.name = name;
        this.birthDate = birthData;
        this.isVerified = isVerified;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public boolean isVerified() {
        return isVerified;
    }
}
