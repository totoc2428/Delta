package serveur.util.chainobject.person.organisation;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Properties;

import serveur.util.chainobject.ChainObject;
import serveur.util.chainobject.location.Address;
import serveur.util.chainobject.person.Identity;
import serveur.util.chainobject.person.user.User;
import serveur.util.data.prop.DataProp;
import serveur.util.security.Key;

public class Organisation extends Identity {
    private User president;
    private double capital;
    private HashMap<Identity, Double> owners;
    private HashMap<ChainObject, Double> proprety;

    public Organisation(Key signature, boolean encryptedSave, String name, LocalDate birthDate, Address address,
            User president,
            HashMap<Identity, Double> owners, HashMap<ChainObject, Double> proprety) {
        super(signature, encryptedSave, name, birthDate, address);
        this.president = president;
        this.owners = isOwnersValid(owners) ? owners : new HashMap<Identity, Double>();// Mettre une exception
        this.proprety = proprety;
    }

    public Organisation(File fileName) {
        this(readOrganisation(fileName).getSignature(), readOrganisation(fileName).getEncryptedSave(),
                readOrganisation(fileName).getName(),
                readOrganisation(fileName).getBirthDate(), readOrganisation(fileName).getAddress(),
                readOrganisation(fileName).getPresident(), readOrganisation(fileName).getOwners(),
                readOrganisation(fileName).getProprety());
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

    public HashMap<ChainObject, Double> getProprety() {
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

    private static Organisation readOrganisation(File fileName) {
        Properties properties = DataProp.read(fileName);
        if (properties != null) {
            Identity identity = new Identity(fileName);
            User user = new User(Paths.get(properties.getProperty("president") + ".prop").toFile());
            return new Organisation(identity.getSignature(), identity.getEncryptedSave(), identity.getName(),
                    identity.getBirthDate(), identity.getAddress(), user, null, null);
        }
        return null;

    }
}
