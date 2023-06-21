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

    @Override
    public String toWriteFormat() {
        String strLocationPoint = "";
        for (LocationPoint l : border) {
            strLocationPoint += l.toWriteFormat() + ",";
        }
        String strOwner = "";
        for (Map.Entry<Identity, Double> ow : owner.entrySet()) {
            strOwner += ow.getKey().getSignature() + "|" + ow.getValue() + ",";
        }
        return super.toWriteFormat() + ";" + strLocationPoint.substring(0, strLocationPoint.length() - 1) + ";"
                + strOwner.substring(0, strOwner.length() - 1);
    }
}
