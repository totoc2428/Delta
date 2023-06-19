package src.serveur.util.organisation;

import src.serveur.util.location.Address;
import src.serveur.util.user.Identity;

public class Association {
    private Identity president;
    private String name;
    private Address mainAddress;

    public Identity getPresident() {
        return president;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return mainAddress;
    }
}
