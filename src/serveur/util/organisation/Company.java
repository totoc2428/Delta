package serveur.util.organisation;

import serveur.util.location.Address;
import serveur.util.user.Identity;

public class Company {
    Identity president;
    String name;
    Address address;

    public Identity getPresident() {
        return president;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }
}
