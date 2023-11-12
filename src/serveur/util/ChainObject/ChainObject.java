package serveur.util.chainobject;

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
    private boolean encryptedSave;

    /* Construcor */
    /**
     * Base constructor
     * 
     * @param signature     the signature of the chain Object.
     * @param encryptedSave the type of encryption of the chain Object.
     */
    public ChainObject(Key signature, boolean encryptedSave) {
        this.signature = signature;
        this.encryptedSave = encryptedSave;
    }

    public ChainObject(Key signature) {
        this(Paths.get(SRC_PATH + signature.getPublickeyString() + ".prop").toFile());
    }

    /**
     * File init constructor
     * Take a file to return a Planet.
     * 
     * @param file must be a type properties (.prop) and respect the syntax of the
     *             key-values pairs.
     */
    public ChainObject(File file) {
        this(ChainObject.readChainObject(file).getSignature(), ChainObject.readChainObject(file).getEncryptedSave());
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

    public boolean getEncryptedSave() {
        return this.encryptedSave;
    }

    public void setEncryptedSave(boolean value) {
        this.encryptedSave = value;
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
                    new Key(fileName), properties.getProperty("encryptedSave").equals("true"));
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
