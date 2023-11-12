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
import serveur.util.data.prop.DataProp;
import serveur.util.security.Key;

public class District extends LocationZone {
    protected final static String SRC_PATH = ChainObject.SRC_PATH + ((String) DataProp
            .read(Paths.get("./ressouces/platform/init.prop").toFile())
            .get("DisctrictLocationZoneChainObjectSourcePath"));
    private int number;
    private String name;
    private Country parent;

    /* Constructor : */
    public District(Key signature, ArrayList<LocationPoint> border,
            HashMap<Identity, Double> owner, int number, String name, Country parent) {
        super(signature, border, owner);
        this.number = number;
        this.name = name;
        this.parent = parent;
    }

    public District(Key signature) {
        this(Paths.get(SRC_PATH + signature.getPublickeyString() + ".prop").toFile());
    }

    public District(File fileName) {
        this(readDistrict(fileName).getSignature(), readDistrict(fileName).getBorder(),
                readDistrict(fileName).getOwners(), readDistrict(fileName).getNumber(),
                readDistrict(fileName).getName(), readDistrict(fileName).getParent());
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public Country getParent() {
        return parent;
    }

    /* Static method : */

    private static District readDistrict(File fileName) {
        Properties properties = DataProp.read(fileName);
        if (properties != null) {
            LocationZone locationZone = new LocationZone(fileName);
            Country parent = new Country(new Key(Key.PublicKeyfromString(properties.getProperty("parent"))));

            return new District(locationZone.getSignature(), locationZone.getBorder(), locationZone.getOwners(),
                    Integer.parseInt(properties.getProperty("number")), (String) properties.getProperty("name"),
                    parent);

        }

        return null;
    }
}
