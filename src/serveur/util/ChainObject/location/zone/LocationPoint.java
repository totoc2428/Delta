package serveur.util.chainobject.location.zone;

import java.io.File;
import java.nio.file.Paths;
import java.util.Properties;

import serveur.util.chainobject.ChainObject;
import serveur.util.data.prop.DataProp;
import serveur.util.security.Key;

public class LocationPoint extends ChainObject {
    protected final static String SRC_PATH = ChainObject.SRC_PATH + ((String) DataProp
            .read(Paths.get("./ressouces/platform/init.prop").toFile()).get("LocationPointChainObjectSourcePath"));
    private double longitude;
    private double latitude;

    /* Construcors : */
    /**
     * Base constructor :
     * 
     * @param signature     the signature of the elements.
     * @param encryptedSave the type of save the chain object.
     * @param longitude     the longitude value of the LocationPoint.
     * @param latitude      the latitue value of the LocationPoint.
     */
    public LocationPoint(Key signature, boolean encryptedSave, double longitude, double latitude) {
        super(signature, encryptedSave);
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * Key constructor :
     * Use a key to make a LocationPoint object. The Object must to be in the
     * LocationPointChainObjectSourcePath property.
     * 
     * @param signature must be placed on SRC_PATH atribute of the Address type.
     */
    public LocationPoint(Key signature) {
        this(Paths.get(SRC_PATH + signature.getPublickeyString()).toFile());
    }

    /**
     * File Constructor :
     * 
     * Take a file to make a LocationPoint object.
     * 
     * @param fileNamemust be a type properties (.prop) and respect the syntax of
     *                     key-value pairs.
     */
    public LocationPoint(File fileName) {
        this(LocationPoint.readLocationPoint(fileName).getSignature(),
                LocationPoint.readLocationPoint(fileName).getEncryptedSave(),
                LocationPoint.readLocationPoint(fileName).getLongitude(),
                LocationPoint.readLocationPoint(fileName).getLatitude());
    }

    /* Getter and setter method */
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    /* Override method */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(longitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(latitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        LocationPoint other = (LocationPoint) obj;
        if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
            return false;
        if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
            return false;
        return true;
    }

    /* Static Method */
    private static LocationPoint readLocationPoint(File fileName) {
        Properties properties = DataProp.read(fileName);
        if (properties != null) {
            ChainObject chainObject = new ChainObject(fileName);
            return new LocationPoint(chainObject.getSignature(), chainObject.getEncryptedSave(),
                    Double.parseDouble(properties.getProperty("longitude")),
                    Double.parseDouble(properties.getProperty("latitude")));
        }
        return null;
    }

}
