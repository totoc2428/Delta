package src.serveur.util.organisation;

import java.util.ArrayList;

import src.serveur.util.security.key.Key;
import src.serveur.util.user.Identity;

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
        this(father, mother, new ArrayList<Identity>(), false);
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

    public getSignature(){
        return signature;
    }

}
