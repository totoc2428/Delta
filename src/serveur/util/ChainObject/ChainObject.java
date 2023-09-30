package serveur.util.ChainObject;

import java.nio.file.Paths;
import java.util.ArrayList;

import serveur.util.data.csv.DataCSV;
import serveur.util.security.Key;

public abstract class ChainObject {

    private Key signature;

    /* Construcor */
    /* Base constructor */

    public ChainObject(Key signature) {
        this.signature = signature;
    }

    /* Construcor */
    /* File init constructor */
    public ChainObject(String file) {

    }

    /* Construcor */
    /* String init constructor */
    public ChainObject(String signature, Key privatekey) {
        if (Paths.get(signature + ".csv").toFile().exists()) {
            ArrayList<String> list = DataCSV.decryptCSV(Paths.get(signature + ".csv").toFile(),
                    privatekey.getPrivateKey());
            System.out.println(list);
        } else {
            // ..
        }
    }

    /* Object method */
    public String toWriteFormat() {
        return signature.getPublickeyString();
    }

    public void write() {

    }

    /* Getter and setter method */
    public Key getSignature() {
        return signature;
    }

    /* Override method */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ChainObject) {
            return ((ChainObject) obj).getSignature().equals(this.getSignature());
        }
        return false;
    }

    @Override
    public String toString() {
        return "ChainObject [signature=" + signature + "]";
    }

    /* Static Method */
    /* Read a block saved in a file */
    public static boolean isAValidChainObject(Object obj) {
        return obj instanceof ChainObject;
    }
}
