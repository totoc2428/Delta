package src.util.repot;

import java.util.ArrayList;
import src.util.signature.*;

public class Repot {
    ArrayList<Signature> content;
    Signature signature;
    public Repot(ArrayList<Signature> content,Signature signature){
        this.content = content;
        this.signature = signature;
    }
    public Repot(Signature signature){
        
    }
}
