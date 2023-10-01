package serveur.util.ChainObject.person;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Properties;

import serveur.util.ChainObject.ChainObject;
import serveur.util.ChainObject.location.Address;
import serveur.util.data.prop.DataProp;
import serveur.util.security.Key;

public class Identity extends ChainObject {
    protected static final String SRC_PATH = ChainObject.SRC_PATH + (String) (DataProp
            .read(Paths.get("./ressouces/platform/init.prop").toFile()))
            .get("IdentityChainObjectSourcePath");
    private String name;
    private LocalDate birthDate;
    private Address address;

    /* Construcor */
    /* Base constructor */

    public Identity(Key signature, String name, LocalDate birthDate, Address address) {
        super(signature);
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
    }

    /* Key Constructor */
    public Identity(Key signature) {
        this(Paths.get(SRC_PATH + signature.getPublickeyString() + ".prop").toFile());
    }

    /* File Constructor */
    public Identity(File fileName) {
        this(readIdentity(fileName).getSignature(), readIdentity(fileName).getName(),
                readIdentity(fileName).getBirthDate(), readIdentity(fileName).getAddress());
    }

    /* Getter and setter method */
    public Address getAddress() {
        return address;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getName() {
        return name;
    }

    /* Override method */

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
        result = prime * result + ((address == null) ? 0 : address.hashCode());
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
        Identity other = (Identity) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (birthDate == null) {
            if (other.birthDate != null)
                return false;
        } else if (!birthDate.equals(other.birthDate))
            return false;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Identity [name=" + name + ", birthDate=" + birthDate + ", address=" + address + "] extended "
                + super.toString();
    }

    @Override
    public Properties toWriteFormat() {
        Properties properties = super.toWriteFormat();
        properties.setProperty("name", name);
        properties.setProperty("birthDate", birthDate.toString());
        properties.setProperty("address", address.getSignature().getPublickeyString());
        return properties;
    }

    /* Static Method */
    private static Identity readIdentity(File fileName) {
        Properties properties = DataProp.read(fileName);
        if (properties != null) {
            return new Identity(new Key(fileName), (String) properties.get("name"),
                    (LocalDate) properties.get("birthDate"),
                    new Address(new Key(Key.PublicKeyfromString((String) properties.get("Address")))));
        } else {
            return null;
        }
    }
}
