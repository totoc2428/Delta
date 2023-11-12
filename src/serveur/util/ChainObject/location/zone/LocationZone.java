package serveur.util.chainobject.location.zone;

import java.io.File;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import serveur.util.chainobject.ChainObject;
import serveur.util.chainobject.person.Identity;
import serveur.util.data.prop.DataProp;
import serveur.util.security.Key;

public class LocationZone extends ChainObject {
    protected final static String SRC_PATH = ChainObject.SRC_PATH + ((String) DataProp
            .read(Paths.get("./ressouces/platform/init.prop").toFile()).get("LocationZoneChainObjectSourcePath"));
    ArrayList<LocationPoint> border;
    HashMap<Identity, Double> owners;

    /* Constructor */
    /**
     * Base constructor
     * 
     * @param signature the signature of the chainObject element
     * @param border    the border of the LocationZone
     * @param owners    the owner of the location zone
     */
    public LocationZone(Key signature, ArrayList<LocationPoint> border,
            HashMap<Identity, Double> owners) {
        super(signature);
        this.border = border;
        this.owners = owners;
    }

    public LocationZone(Key signature) {
        this(Paths.get(SRC_PATH + signature.getPublickeyString() + ".prop").toFile());
    }

    public LocationZone(File fileName) {
        this(readLocationZone(fileName).getSignature(), readLocationZone(fileName).getBorder(),
                readLocationZone(fileName).getOwners());
    }

    public ArrayList<LocationPoint> getBorder() {
        return border;
    }

    public HashMap<Identity, Double> getOwners() {
        return owners;
    }

    /* Static Method : */

    private static LocationZone readLocationZone(File fileName) {
        Properties properties = DataProp.read(fileName);
        if (properties != null) {
            ChainObject chainObject = new ChainObject(fileName);
            ArrayList<LocationPoint> border = new ArrayList<LocationPoint>();
            for (String string : properties.getProperty("border").split(" ")) {
                border.add(new LocationPoint(new Key(Key.PublicKeyfromString(string))));
            }
            HashMap<Identity, Double> owners = new HashMap<Identity, Double>();
            String[] ownersIdentity = properties.getProperty("ownersIdentity").split(" ");
            String[] ownersDouble = properties.getProperty("ownersDouble").split(" ");
            for (int i = 0; i < ownersIdentity.length; i++) {
                owners.put(new Identity(new Key(Key.PublicKeyfromString(ownersIdentity[i]))),
                        Double.parseDouble(ownersDouble[i]));
            }
            return new LocationZone(chainObject.getSignature(), border, owners);
        }

        return null;
    }
}
