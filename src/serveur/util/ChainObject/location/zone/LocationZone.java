package serveur.util.ChainObject.location.zone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import serveur.util.ChainObject.ChainObject;
import serveur.util.ChainObject.person.Identity;
import serveur.util.security.Key;

public abstract class LocationZone extends ChainObject {
    ArrayList<LocationPoint> border;
    HashMap<Identity, Double> owner;

    public LocationZone(Key signature, ArrayList<LocationPoint> border,
            HashMap<Identity, Double> owner) {
        super(signature);
        this.border = border;
        this.owner = owner;
    }

    public ArrayList<LocationPoint> getBorder() {
        return border;
    }

    public HashMap<Identity, Double> getOwner() {
        return owner;
    }

}
