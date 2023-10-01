package serveur.util.ChainObject;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;

import serveur.util.data.prop.DataProp;
import serveur.util.security.Key;

public class ChainObject {
    protected final static String SRC_PATH = (String) (DataProp
            .read(Paths.get("./ressouces/platform/init.prop").toFile()))
            .get("ChainObjectSourcePath");
    private Key signature;

    /* Construcor */
    /* Base constructor */

    public ChainObject(Key signature) {
        this.signature = signature;
    }

    /* Construcor */
    /* File init constructor */
    public ChainObject(File file) {
        this(file.exists() ? ChainObject.readChainObject(file).getSignature() : null);
    }
    /* Object method */

    public Properties toWriteFormat() {
        Properties properties = new Properties();
        properties.setProperty("publicKey", signature.getPublickeyString());
        properties.setProperty("privateKey", signature.getPrivateKeyString());
        return properties;
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
    private static ChainObject readChainObject(File fileName) {
        Properties properties = DataProp.read(fileName);
        if (properties != null) {
            return new ChainObject(
                    new Key((String) properties.get("publicKey"), (String) properties.get("privateKey")));
        } else {
            return null;
        }
    }

    /* Check if the Object is valid ChainObject */
    public static boolean isAValidChainObject(Object obj) {
        return obj instanceof ChainObject;
    }

    /* Get an arrayList of the signature of each ChainObject */
    public static ArrayList<Key> getChainObjectSignature(ArrayList<ChainObject> chainObjectList) {
        ArrayList<Key> keyArrayList = new ArrayList<Key>();
        for (ChainObject chainObj : chainObjectList) {
            keyArrayList.add(chainObj.getSignature());
        }

        return keyArrayList;
    }

    /* Get a String arrayList of the Public signature of each ChainObject */
    public static ArrayList<String> getChainObjectSignaturePublicKeytoSring(ArrayList<ChainObject> chainObjectsList) {
        ArrayList<String> stringArrayList = new ArrayList<String>();
        for (Key signatureChaiObj : getChainObjectSignature(chainObjectsList)) {
            stringArrayList.add(signatureChaiObj.getPublickeyString());
        }
        return stringArrayList;
    }

    /* Get a String arrayList of the Private signature of each ChainObject */
    public static ArrayList<String> getChainObjectSignaturePrivateKeytoSring(ArrayList<ChainObject> chainObjectsList) {
        ArrayList<String> stringArrayList = new ArrayList<String>();
        for (Key signatureChaiObj : getChainObjectSignature(chainObjectsList)) {
            stringArrayList.add(signatureChaiObj.getPrivateKeyString());
        }
        return stringArrayList;
    }

}
