package serveur.util.ChainObject.person.user;

import java.time.LocalDate;
import java.util.ArrayList;

import serveur.util.ChainObject.person.Identity;
import serveur.util.ChainObject.location.Address;
import serveur.util.security.Key;

public class User extends Identity {
    private String firstName;
    private ArrayList<String> otherName;
    private Account mainAccount;

    public User(Key signature, String name, LocalDate birthDate, Address address, ArrayList<Account> privateAccount,
            ArrayList<Account> publicAccount, String firstName, ArrayList<String> otherName, Account mainAccount) {
        super(signature, name, birthDate, address, privateAccount, publicAccount);
        this.firstName = firstName;
        this.otherName = otherName;
        this.mainAccount = mainAccount;
    }

    public String getFirstName() {
        return firstName;
    }

    public ArrayList<String> getOtherName() {
        return otherName;
    }

    public Account getMainAccount() {
        return mainAccount;
    }

}
