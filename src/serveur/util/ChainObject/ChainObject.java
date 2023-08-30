package serveur.util.ChainObject;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

import serveur.util.data.DataCSV;
import serveur.util.security.Key;

public abstract class ChainObject {
    private Key signature;

    public ChainObject(Key signature) {
        this.signature = signature;
    }

    public ChainObject(String signature, String privatekey) {
        if (Paths.get(signature + ".csv").toFile().exists()) {
            ArrayList<String> list = DataCSV.filetoStringArrayList(
                    Paths.get("chainObject" + File.separator + "" + signature + ".csv").toFile());

        } else {
            // ..
        }
    }

    public Key getSignature() {
        return signature;
    }

    public String toWriteFormat() {
        return signature.getPublickey();
    }
}
