package model.dto.blockchain.chainobject.person;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDate;

import model.dto.blockchain.chainobject.ChainObject;

public abstract class Person extends ChainObject {
    private String lastName;

    private String nationality;

    private LocalDate birthDate;
    private boolean isVerified;

    /* constructor */
    public Person(PrivateKey privateKey, PublicKey publicKey, String lastName, LocalDate birthData, boolean isVerified,
            String nationality) {
        super(privateKey, publicKey);

        this.lastName = lastName;
        this.birthDate = birthData;
        this.isVerified = isVerified;

        this.nationality = nationality;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public String getNationality() {
        return nationality;
    }
}
