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

public class Country extends LocationZone {
    protected final static String SRC_PATH = ChainObject.SRC_PATH + ((String) DataProp
            .read(Paths.get("./ressouces/platform/init.prop").toFile())
            .get("CountryLocationZoneChainObjectSourcePath"));
    private String name;

    public Country(Key signature, ArrayList<LocationPoint> border,
            HashMap<Identity, Double> owner, String name) {
        super(signature, border, owner);
        this.name = name;
    }

    public Country(Key signature) {
        this(Paths.get(SRC_PATH + signature.getPublickeyString() + ".prop").toFile());
    }

    public Country(File fileName) {
        this(readCountry(fileName).getSignature(), readCountry(fileName).getBorder(), readCountry(fileName).getOwners(),
                readCountry(fileName).getName());
    }

    public String getName() {
        return name;
    }

    /* Static Method */

    public static Country readCountry(File fileName) {
        Properties properties = DataProp.read(fileName);
        if (properties != null) {
            LocationZone locationZone = new LocationZone(fileName);
            return new Country(locationZone.getSignature(), locationZone.getBorder(), locationZone.getOwners(),
                    (String) properties.getProperty("name"));
        }

        return null;
    }

}
