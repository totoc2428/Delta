package serveur.util.chainobject.location.zone;

import java.io.File;
import java.nio.file.Paths;
import java.util.Properties;

import serveur.util.chainobject.ChainObject;
import serveur.util.data.prop.DataProp;
import serveur.util.security.Key;

public class LocationUniverPoint extends ChainObject {
    protected static final String SRC_PATH = ChainObject.SRC_PATH + (String) (DataProp
            .read(Paths.get("./ressouces/platform/init.prop").toFile()))
            .get("LocationUniverPointChainObjectSourcePath");
    private double xCoordonates;
    private double yCoordonates;
    private double zCoordonates;

    /* Construcor */
    /**
     * Base constructor
     * 
     * @param signature    the signature of the planet
     * @param xCoordonates the coordonates on x axes for the planet.
     * @param yCoordonates the coordonates on y axes for the planet.
     * @param zCoordonates the coordonates on z axes for the planet.
     */
    public LocationUniverPoint(Key signature, double xCoordonates, double yCoordonates, double zCoordonates) {
        super(signature);
        this.xCoordonates = xCoordonates;
        this.yCoordonates = yCoordonates;
        this.zCoordonates = zCoordonates;
    }

    /**
     * Key constructor
     * Take a key to make a LocationUniverPoint
     * 
     * @param signature must be placed on SRC_PATH atribute of the
     *                  LocationUniverPoint type.
     */
    public LocationUniverPoint(Key signature) {
        this(Paths.get(SRC_PATH + signature.getPublickeyString() + ".prop").toFile());
    }

    /**
     * File constructor
     * Take a file to make an LocationUniverPoint.
     * 
     * @param fileName must be a type properties (.prop) and respect the syntax of
     *                 key-value pairs.
     */
    public LocationUniverPoint(File fileName) {
        this(readLocationUniverPoint(fileName).getSignature(),
                readLocationUniverPoint(fileName).getxCoordonates(),
                readLocationUniverPoint(fileName).getyCoordonates(),
                readLocationUniverPoint(fileName).getzCoordonates());
    }

    /* Getter and setter method */
    public double getxCoordonates() {
        return xCoordonates;
    }

    public double getyCoordonates() {
        return yCoordonates;
    }

    public double getzCoordonates() {
        return zCoordonates;
    }

    /* Override method */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(xCoordonates);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(yCoordonates);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(zCoordonates);
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
        LocationUniverPoint other = (LocationUniverPoint) obj;
        if (Double.doubleToLongBits(xCoordonates) != Double.doubleToLongBits(other.xCoordonates))
            return false;
        if (Double.doubleToLongBits(yCoordonates) != Double.doubleToLongBits(other.yCoordonates))
            return false;
        if (Double.doubleToLongBits(zCoordonates) != Double.doubleToLongBits(other.zCoordonates))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "LocationUniverPoint [xCoordonates=" + xCoordonates + ", yCoordonates=" + yCoordonates
                + ", zCoordonates=" + zCoordonates + "] extend" + super.toString();
    }

    @Override
    public Properties toWriteFormat() {
        Properties properties = super.toWriteFormat();
        properties.setProperty("xCoordonates", "" + xCoordonates);
        properties.setProperty("yCoordonates", "" + yCoordonates);
        properties.setProperty("zCoordonates", "" + zCoordonates);
        return properties;
    }

    /* Static Method */
    /**
     * 
     * @param fileName a properties file who respecte the syntax of a
     *                 UniverLocationPoint.
     * @return the LocationUniverPoint saved in a file. If an error is ocurate the
     *         medhod return
     *         {@null}.
     */
    private static LocationUniverPoint readLocationUniverPoint(File fileName) {
        Properties properties = DataProp.read(fileName);
        if (properties != null) {
            return new LocationUniverPoint(new Key(fileName), (double) properties.get("xCoordonates"),
                    (double) properties.get("yCoordonates"), (double) properties.get("zCoordonates"));
        }

        return null;
    }
}
