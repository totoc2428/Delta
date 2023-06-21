package serveur.util.ChainObject.location;

import serveur.util.security.Key;
import serveur.util.ChainObject.ChainObject;
import serveur.util.ChainObject.location.level.Country;
import serveur.util.ChainObject.location.level.District;
import serveur.util.ChainObject.location.level.Proprety;
import serveur.util.ChainObject.location.level.Town;

public class Address extends ChainObject {
    private Proprety proprety;
    private Town town;
    private District district;
    private Country country;
    private String planet;

    public Address(Key signature, Proprety proprety, Town town, District district,
            Country country, String planet) {
        super(signature);
        this.proprety = proprety;
        this.town = town;
        this.district = district;
        this.country = country;
    }

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

    @Override
    public String toWriteFormat() {
        return super.toWriteFormat() + ";" + proprety.getSignature() + ";" + town.getSignature() + ";"
                + district.getSignature() + ";" + country.getSignature() + ";" + planet;
    }
}
