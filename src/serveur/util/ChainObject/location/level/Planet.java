package serveur.util.chainobject.location.level;

import java.io.File;
import java.nio.file.Paths;
import java.util.Properties;

import serveur.util.chainobject.ChainObject;
import serveur.util.chainobject.location.zone.LocationUniverPoint;
import serveur.util.data.prop.DataProp;
import serveur.util.security.Key;

public class Planet extends ChainObject {
    protected static final String SRC_PATH = ChainObject.SRC_PATH + (String) (DataProp
            .read(Paths.get("./ressouces/platform/init.prop").toFile()))
            .get("PlanetChainObjectSourcePath");
    private String name;
    private LocationUniverPoint locationUniverPoint;

    /* Construcor */
    /**
     * Base constructor
     * 
     * @param signature           the signature of the elements.
     * @param name                the name of the planet.
     * @param locationUniverPoint the location point of the planet
     *                            {@link serveur.util.chainobject.location.zone.LocationUniverPoint}
     */
    public Planet(Key signature, String name, LocationUniverPoint locationUniverPoint) {
        super(signature);
        this.name = name;
        this.locationUniverPoint = locationUniverPoint;
    }

    /**
     * Key constructor
     * Take a key an return a Planet.
     * 
     * @param signature must be placed on SRC_PATH atribute of the Address type.
     */
    public Planet(Key signature) {
        this(Paths.get(SRC_PATH + signature.getPublickeyString() + ".prop").toFile());
    }

    /**
     * File constructor
     * Take a file to return a Planet.
     * 
     * @param fileName must be a type properties (.prop) and respect the syntax of
     *                 key-value pairs.
     */
    public Planet(File fileName) {
        this(readPlanet(fileName).getSignature(), readPlanet(fileName).getName(),
                readPlanet(fileName).getLocationUniverPoint());
    }

    /* Getter and setter method */
    public LocationUniverPoint getLocationUniverPoint() {
        return locationUniverPoint;
    }

    public String getName() {
        return name;
    }

    /* Override method */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((locationUniverPoint == null) ? 0 : locationUniverPoint.hashCode());
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
        Planet other = (Planet) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (locationUniverPoint == null) {
            if (other.locationUniverPoint != null)
                return false;
        } else if (!locationUniverPoint.equals(other.locationUniverPoint))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Planet [name=" + name + ", locationUniverPoint=" + locationUniverPoint + "] extend" + super.toString();
    }

    @Override
    public Properties toWriteFormat() {
        Properties properties = super.toWriteFormat();
        properties.setProperty("name", name);
        properties.setProperty("locationUniverPoint", locationUniverPoint.getSignature().getPublickeyString());
        return properties;
    }

    /* Static Method */
    /**
     * 
     * @param fileName a properties file who respecte the syntax of a Planet.
     * @return the Planet saved in a file. If an error is ocurate the medhod return
     *         {@null}.
     */
    private static Planet readPlanet(File fileName) {
        Properties properties = DataProp.read(fileName);
        if (properties != null) {
            return new Planet(new Key(fileName), (String) properties.get("name"),
                    new LocationUniverPoint(new Key((String) properties.get("locationUniverPoint"), null)));
        }
        return null;
    }

}
