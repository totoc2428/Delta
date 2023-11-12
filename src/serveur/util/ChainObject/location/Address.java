package serveur.util.chainobject.location;

import serveur.util.security.Key;

import java.io.File;
import java.nio.file.Paths;
import java.util.Properties;

import serveur.util.chainobject.ChainObject;
import serveur.util.chainobject.location.level.Proprety;
import serveur.util.data.prop.DataProp;

public class Address extends ChainObject {
    protected final static String SRC_PATH = ChainObject.SRC_PATH + ((String) DataProp
            .read(Paths.get("./ressouces/platform/init.prop").toFile()).get("AddressChainObjectSourcePath"));
    private Proprety proprety;

    /* Construcor */
    /**
     * Base constructor
     * 
     * @param signature     the signature of the Address.
     * @param encryptedSave the type of saving of the chain object.
     * @param proprety      the Property of the Address
     *                      {@link serveur.util.chainobject.location.level.Proprety}
     */
    public Address(Key signature, boolean encryptedSave, Proprety proprety) {
        super(signature, encryptedSave);
        this.proprety = proprety;
    }

    /**
     * Key constructor
     * Take a key to make an Address.
     * 
     * @param signature must be placed on SRC_PATH atribute of the Address type.
     */
    public Address(Key signature) {
        this(Paths.get(SRC_PATH + signature.getPublickeyString() + ".prop").toFile());
    }

    /**
     * File constructor
     * Take a file to make an Address.
     * 
     * @param fileName must be a type properties (.prop) and respect the syntax of
     *                 key-value pairs.
     */
    public Address(File fileName) {
        this(readAddress(fileName).getSignature(), readAddress(fileName).getEncryptedSave(),
                readAddress(fileName).getProprety());
    }

    /* Getter and setter method */
    public Proprety getProprety() {
        return proprety;
    }

    /* Override method */

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((proprety == null) ? 0 : proprety.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Address other = (Address) obj;
        if (proprety == null) {
            if (other.proprety != null)
                return false;
        } else if (!proprety.equals(other.proprety))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Address [ proprety=" + proprety + "] extend " + super.toString();
    }

    /* Static Method */
    /**
     * 
     * @param fileName a properties file who respecte the syntax of a Address.
     * @return the Address saved in a file. If an error is ocurate the medhod return
     *         {@null}.
     */
    private static Address readAddress(File fileName) {
        Properties properties = DataProp.read(fileName);
        if (properties != null) {
            ChainObject chainObject = new ChainObject(fileName);
            Proprety proprety = new Proprety(new Key(Key.PublicKeyfromString(properties.getProperty("proprety"))));
            return new Address(chainObject.getSignature(), chainObject.getEncryptedSave(), proprety);
        }
        return null;
    }
}
