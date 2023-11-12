package serveur.util.chainobject.location.level;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import serveur.util.chainobject.ChainObject;
import serveur.util.chainobject.location.zone.LocationPoint;
import serveur.util.chainobject.location.zone.LocationZone;
import serveur.util.chainobject.person.Identity;
import serveur.util.chainobject.person.organisation.OrganisationNonProfit;
import serveur.util.data.prop.DataProp;
import serveur.util.security.Key;

public class Town extends LocationZone {
    protected final static String SRC_PATH = ChainObject.SRC_PATH + ((String) DataProp
            .read(Paths.get("./ressouces/platform/init.prop").toFile())
            .get("TownLocationZoneChainObjectSourcePath"));
    private int number;
    private String name;
    private District parent;
    private OrganisationNonProfit administration;

    public Town(Key signature, ArrayList<LocationPoint> border,
            HashMap<Identity, Double> owner, int number, String name, District parent) {
        super(signature, border, owner);
        this.number = number;
        this.name = name;
        this.parent = parent;
    }

    public Town(Key signature) {
        super(Paths.get(SRC_PATH + signature.getPublickeyString() + ".prop").toFile());
    }

    public Town(File fileName) {
        super(readTown(fileName).getSignature(), readTown(fileName).getBorder(), readTown(fileName).getOwners());
    }

    /* Getter and Setter : */

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public LocationZone getParent() {
        return parent;
    }

    public OrganisationNonProfit getAdministration() {
        return administration;
    }

    /* Static Method */

    private static Town readTown(File fileName) {
        Properties properties = DataProp.read(fileName);
        if (properties != null) {
            LocationZone locationZone = new LocationZone(fileName);
            District district = new District(new Key(Key.PublicKeyfromString(properties.getProperty("parent"))));

            return new Town(locationZone.getSignature(), locationZone.getBorder(), locationZone.getOwners(),
                    Integer.parseInt(properties.getProperty("number")), (String) properties.getProperty("name"),
                    district);
        }

        return null;
    }

}
