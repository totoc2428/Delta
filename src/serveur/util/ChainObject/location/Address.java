package serveur.util.ChainObject.location;

import serveur.util.security.Key;

import java.io.File;
import java.nio.file.Paths;
import java.util.Properties;

import serveur.util.ChainObject.ChainObject;
import serveur.util.ChainObject.location.level.Country;
import serveur.util.ChainObject.location.level.District;
import serveur.util.ChainObject.location.level.Proprety;
import serveur.util.ChainObject.location.level.Town;
import serveur.util.data.prop.DataProp;

public class Address extends ChainObject {
    protected final static String SRC_PATH = ChainObject.SRC_PATH + ((String) DataProp
            .read(Paths.get("./ressouces/platform/init.prop").toFile()).get("AddressChainObjectSourcePath"));
    private Proprety proprety;
    private Town town;
    private District district;
    private Country country;
    private String planet;

    /* Construcor */
    /* Base constructor */
    public Address(Key signature, Proprety proprety, Town town, District district,
            Country country, String planet) {
        super(signature);
        this.proprety = proprety;
        this.town = town;
        this.district = district;
        this.country = country;
    }

    /* Construcor */
    /* Key constructor */
    public Address(Key signature) {
        this(Paths.get(SRC_PATH + signature.getPublickeyString() + ".prop").toFile());
    }

    /* File constructor */
    public Address(File fileName) {
        this(readAddress(fileName).getSignature(), readAddress(fileName).getProprety(), readAddress(fileName).getTown(),
                readAddress(fileName).getDistrict(),
                readAddress(fileName).getCountry(), readAddress(fileName).getPlanet());
    }

    /* Getter and setter method */
    public Proprety getProprety() {
        return proprety;
    }

    public Town getTown() {
        return town;
    }

    public District getDistrict() {
        return district;
    }

    public Country getCountry() {
        return country;
    }

    public String getPlanet() {
        return planet;
    }

    /* Override method */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((proprety == null) ? 0 : proprety.hashCode());
        result = prime * result + ((town == null) ? 0 : town.hashCode());
        result = prime * result + ((district == null) ? 0 : district.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((planet == null) ? 0 : planet.hashCode());
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
        Address other = (Address) obj;
        if (proprety == null) {
            if (other.proprety != null)
                return false;
        } else if (!proprety.equals(other.proprety))
            return false;
        if (town == null) {
            if (other.town != null)
                return false;
        } else if (!town.equals(other.town))
            return false;
        if (district == null) {
            if (other.district != null)
                return false;
        } else if (!district.equals(other.district))
            return false;
        if (country == null) {
            if (other.country != null)
                return false;
        } else if (!country.equals(other.country))
            return false;
        if (planet == null) {
            if (other.planet != null)
                return false;
        } else if (!planet.equals(other.planet))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Address [proprety=" + proprety + ", town=" + town + ", district=" + district + ", country=" + country
                + ", planet=" + planet + "] extend " + super.toString();
    }

    /* Static Method */
    private static Address readAddress(File fileName) {
        Properties properties = DataProp.read(fileName);
        if (properties != null) {
            return new Address(new Key(fileName));
        }
        return null;
    }
}
