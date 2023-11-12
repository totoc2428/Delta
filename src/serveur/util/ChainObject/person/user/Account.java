package serveur.util.chainobject.person.user;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Properties;

import serveur.util.chainobject.ChainObject;
import serveur.util.data.prop.DataProp;
import serveur.util.security.Key;

public class Account extends ChainObject {
    protected final static String SRC_PATH = (String) (DataProp
            .read(Paths.get("./ressouces/platform/init.prop").toFile()))
            .get("AccountChainObjectSourcePath");
    String name;
    LocalDate birthDate;

    /* Construcor */
    /* Base constructor */
    public Account(Key signature, String name, LocalDate birthDate) {
        super(signature);
        this.name = name;
        this.birthDate = birthDate;
    }

    /* Key constructor */
    public Account(Key signature) {
        this(Paths.get(SRC_PATH + signature.getPublickeyString() + ".prop").toFile());
    }

    /* File init constructor */
    public Account(File fileName) {
        this(readAccount(fileName).getSignature(), readAccount(fileName).getName(),
                readAccount(fileName).getBirthDate());
    }

    /* Getter and setter method */
    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    /* Override method */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
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
        Account other = (Account) obj;
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
        return true;
    }

    @Override
    public String toString() {
        return "Account [name=" + name + ", birthDate=" + birthDate + "] extend " + super.toString();
    }

    @Override
    public Properties toWriteFormat() {
        Properties properties = super.toWriteFormat();
        properties.setProperty("name", name);
        properties.setProperty("birthDate", birthDate.toString());
        return properties;
    }

    /* Static Method */
    /* Read an Account saved in a file */
    private static Account readAccount(File fileName) {
        Properties properties = DataProp.read(fileName);
        if (properties != null) {
            return new Account(new Key(fileName), (String) properties.getProperty("name"),
                    LocalDate.parse((String) properties.getProperty("birthDate")));
        } else {
            return null;
        }
    }
}
