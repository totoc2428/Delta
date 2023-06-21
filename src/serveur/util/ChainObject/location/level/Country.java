package serveur.util.ChainObject.location.level;

import java.util.ArrayList;
import java.util.HashMap;

import serveur.util.ChainObject.location.zone.LocationPoint;
import serveur.util.ChainObject.location.zone.LocationZone;
import serveur.util.ChainObject.person.Identity;
import serveur.util.security.Key;

public class Country extends LocationZone {
    private String name;

    public Country(Key signature, ArrayList<LocationPoint> border,
            HashMap<Identity, Double> owner, String name) {
        super(signature, border, owner);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toWriteFormat() {
        return super.toWriteFormat() + ";" + name;
    }
}
