package serveur.util.ChainObject.location.level;

import java.util.ArrayList;
import java.util.HashMap;

import serveur.util.ChainObject.location.zone.LocationPoint;
import serveur.util.ChainObject.location.zone.LocationZone;
import serveur.util.ChainObject.person.Identity;
import serveur.util.security.Key;

public class Proprety extends LocationZone {
    private String complements;
    private String number;
    private String street;

    private HashMap<Identity, Double> tenant;
    private LocationZone parent;

    public Proprety(Key signature, ArrayList<LocationPoint> border,
            HashMap<Identity, Double> owner, String complements, String number, String street,
            HashMap<Identity, Double> tenant, LocationZone parent) {
        super(signature, border, owner);
        this.complements = complements;
        this.number = number;
        this.street = street;
        this.tenant = tenant;
        this.parent = parent;
    }

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

    public LocationZone getParent() {
        return parent;
    }

    @Override
    public String toWriteFormat() {
        return super.toWriteFormat() + ";" + complements + ";" + number + ";" + street + ";" + tenant + ";"
                + parent.getSignature();
    }
}
