package serveur.util.ChainObject.person.organisation;

import java.time.LocalDate;
import java.util.HashMap;

import serveur.util.ChainObject.location.Address;
import serveur.util.ChainObject.person.Identity;
import serveur.util.security.Key;

public abstract class Organisation extends Identity {
    private Identity president;
    private HashMap<Identity, Double> owners;

    public Organisation(Key signature, String name, LocalDate birthDate, Address address) {
        super(signature, name, birthDate, address);
    }

    public HashMap<Identity, Double> getOwners() {
        return owners;
    }

    public Identity getPresident() {
        return president;
    }

    private boolean IsOwnersValid() {
        double totalOnwerPer = 0.0;
        for (Double per : owners.values()) {
            totalOnwerPer += per;
        }

        return totalOnwerPer == 1;
    }
}
