package serveur.util.ChainObject.person;

import java.time.LocalDate;
import java.util.ArrayList;

import serveur.util.ChainObject.ChainObject;
import serveur.util.ChainObject.person.user.Account;
import serveur.util.ChainObject.location.Address;
import serveur.util.security.Key;

public abstract class Identity extends ChainObject {
    private String name;
    private LocalDate birthDate;
    private Address address;
    private ArrayList<Account> privateAccounts;
    private ArrayList<Account> publicAccounts;

    public Identity(Key signature, String name, LocalDate birthDate, Address address,
            ArrayList<Account> privateAccounts, ArrayList<Account> publicAccounts) {
        super(signature);
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.privateAccounts = privateAccounts;
        this.publicAccounts = publicAccounts;
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

    public ArrayList<Account> getPrivateAccounts() {
        return privateAccounts;
    }

    public ArrayList<Account> getPublicAccounts() {
        return publicAccounts;
    }

    @Override
    public String toWriteFormat() {
        String strPrivateAccount = "";
        String strPublicAccount = "";
        for (Account a : privateAccounts) {
            strPrivateAccount += a.getSignature() + ",";
        }
        for (Account a : publicAccounts) {
            strPublicAccount += a.getSignature() + ",";
        }
        return super.toWriteFormat() + ";" + name + ";" + birthDate + ";" + address.getSignature() + ";"
                + strPrivateAccount.substring(0, strPrivateAccount.length() - 1) + ";"
                + strPublicAccount.subSequence(0, strPublicAccount.length() - 1) + ";";
    }

}
