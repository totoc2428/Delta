package serveur.util.organisation;

import java.util.ArrayList;

import serveur.util.security.key.Key;
import serveur.util.user.Identity;

public class Familly {
    private Identity father;
    private Identity mother;
    private ArrayList<Identity> childs;
    private Key signature;

    public Familly(Identity father, Identity mother, ArrayList<Identity> childs) {
        this.father = father;
        this.mother = mother;
        this.childs = childs;
    }

    public Familly(Identity father, Identity mother) {
        this(father, mother, new ArrayList<Identity>());
    }

    public Identity getFather() {
        return father;
    }

    public Identity getMother() {
        return mother;
    }

    public ArrayList<Identity> getChilds() {
        return childs;
    }

    public Key getSignature() {
        return signature;
    }

}
