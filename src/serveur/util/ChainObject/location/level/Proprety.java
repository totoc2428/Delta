package serveur.util.chainobject.location.level;

import java.io.File;
import java.nio.file.Path;
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

public class Proprety extends LocationZone {
    protected final static String SRC_PATH = ChainObject.SRC_PATH + ((String) DataProp
            .read(Paths.get("./ressouces/platform/init.prop").toFile())
            .get("PropretyLocationZoneChainObjectSourcePath"));
    private String complements;
    private String number;
    private String street;
    private HashMap<Identity, Double> tenant;
    private Town town;

    /* Constructor : */

    /**
     * Base constructor :
     * 
     * @param signature   the signature of the Object.
     * @param border      the bordure of the prorepty (perimeter);
     * @param owners      the owners of the proprepety an HasMap with the Owner and
     *                    percentage of possesion.
     * @param complements the complement of the adress (bulding, floor...)
     * @param number      the number of the buildingth.
     * @param street      the street of the entry door
     * @param tenant      the tenant of the proprety. If is null there are not
     *                    tenant.
     * @param town        the Location zone parent.
     */
    public Proprety(Key signature, ArrayList<LocationPoint> border,
            HashMap<Identity, Double> owners, String complements, String number, String street,
            HashMap<Identity, Double> tenant, Town town) {
        super(signature, border, owners);
        this.complements = complements;
        this.number = number;
        this.street = street;
        this.tenant = tenant;
        this.town = town;
    }

    public Proprety(Key signature) {
        this(Paths.get(SRC_PATH + signature.getPublickeyString() + ".prop").toFile());
    }

    public Proprety(File fileName) {
        this(readPropety(fileName).getSignature(), readPropety(fileName).getBorder(), readPropety(fileName).getOwners(),
                readPropety(fileName).getComplements(), readPropety(fileName).getNumber(),
                readPropety(fileName).getStreet(), readPropety(fileName).getTenant(), readPropety(fileName).getTown());
    }

    /* Getter and Setter : */
    public String getComplements() {
        return complements;
    }

    public String getNumber() {
        return number;
    }

    public String getStreet() {
        return street;
    }

    public HashMap<Identity, Double> getTenant() {
        return tenant;
    }

    public Town getTown() {
        return town;
    }

    /* Static Method : */
    private static Proprety readPropety(File fileName) {
        Properties properties = DataProp.read(fileName);
        if (properties != null) {
            LocationZone locationZone = new LocationZone(fileName);
            HashMap<Identity, Double> tenant = new HashMap<Identity, Double>();
            Town town = new Town(new Key(Key.PublicKeyfromString(properties.getProperty("town"))));
            return new Proprety(locationZone.getSignature(), locationZone.getBorder(), locationZone.getOwners(),
                    (String) properties.getProperty("complements"), (String) properties.getProperty("number"),
                    (String) properties.getProperty("street"),
                    tenant, town);
        }

        return null;
    }
}
