package serveur.util.ChainObject.location.level;

import java.util.ArrayList;
import java.util.HashMap;

import serveur.util.ChainObject.location.zone.LocationPoint;
import serveur.util.ChainObject.location.zone.LocationZone;
import serveur.util.ChainObject.person.Identity;
import serveur.util.security.Key;

public class District extends LocationZone {
    private int number;
    private String name;
    private LocationZone parent;

    public District(Key signature, ArrayList<LocationPoint> border,
            HashMap<Identity, Double> owner, int number, String name, LocationZone parent) {
        super(signature, border, owner);
        this.number = number;
        this.name = name;
        this.parent = parent;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public LocationZone getParent() {
        return parent;
    }

    @Override
    public String toWriteFormat() {
        return super.toWriteFormat() + ";" + number + ";" + name + ";" + parent.getSignature();
    }
}
