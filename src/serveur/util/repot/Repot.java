package src.serveur.util.repot;

import java.util.ArrayList;

import src.serveur.util.repot.format.RepotFormat;
import src.serveur.util.repot.format.RepotFormatName;
import src.serveur.util.security.key.Key;

public class Repot {
    private Key signature;
    private ArrayList<RepotFormat> repot;

    public Repot(Key signature, ArrayList<RepotFormat> repot) {
        this.signature = signature;
        this.repot = repot != null ? repot : new ArrayList<RepotFormat>();
    }

    public Repot(Key signature) {
        this(signature, null);
    }

    public ArrayList<RepotFormat> getAllMessage() {
        return getAllOfRepotFormatName(RepotFormatName.MESSAGE);
    }

    public ArrayList<RepotFormat> getAllTransaction() {
        return getAllOfRepotFormatName(RepotFormatName.TRANSACTION);
    }

    public ArrayList<RepotFormat> getAllAccess() {
        return getAllOfRepotFormatName(RepotFormatName.ACCESS);
    }

    private ArrayList<RepotFormat> getAllOfRepotFormatName(RepotFormatName type) {
        ArrayList<RepotFormat> messageList = new ArrayList<RepotFormat>();
        for (RepotFormat r : repot) {
            if (r.getFormat() == type) {
                messageList.add(r);
            }
        }
        return messageList;
    }

    public Key getSignature() {
        return signature;
    }

    public ArrayList<RepotFormat> getRepot() {
        return repot;
    }
}
