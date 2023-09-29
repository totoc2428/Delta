package serveur.util.ChainObject.person.organisation;

import java.time.LocalDate;
import java.util.HashMap;

import serveur.util.ChainObject.location.Address;
import serveur.util.ChainObject.person.Identity;
import serveur.util.ChainObject.person.user.User;
import serveur.util.security.Key;

public abstract class Organisation extends Identity {
    private User president;
    private HashMap<Identity, Double> owners;
    private double capital;
    private HashMap<Object, Double> proprety;

    public Organisation(Key signature, String name, LocalDate birthDate, Address address, User president,
            HashMap<Identity, Double> owners) {
        super(signature, name, birthDate, address);
        this.president = president;
        this.owners = isOwnersValid(owners) ? owners : new HashMap<Identity, Double>();// Mettre une exception
    }

    public HashMap<Identity, Double> getOwners() {
        return owners;
    }

    public void setOwners(HashMap<Identity, Double> ownersSet) {
        if (isOwnersValid(ownersSet)) {
            owners = ownersSet;
        } else {
            // exception
        }

    }

    private boolean isOwnersValid(HashMap<Identity, Double> ownersTest) {
        double totalOnwerPer = 0.0;
        for (Double per : ownersTest.values()) {
            totalOnwerPer += per;
        }

        return totalOnwerPer == 1;
    }

    public User getPresident() {
        return president;
    }

    public double getCapital() {
        return capital;
    }

    public HashMap<Object, Double> getProprety() {
        return proprety;
    }

    public HashMap<Organisation, Double> getPropretyOraganisation() {
        HashMap<Organisation, Double> organisationMap = new HashMap<Organisation, Double>();
        for (Object obj : proprety.values()) {
            if (obj instanceof Organisation) {
                organisationMap.put((Organisation) obj, proprety.get(obj));
            }
        }
        return organisationMap;
    }

}
