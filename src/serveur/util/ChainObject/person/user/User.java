package serveur.util.ChainObject.person.user;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import serveur.util.ChainObject.person.Identity;
import serveur.util.data.prop.DataProp;
import serveur.util.ChainObject.ChainObject;
import serveur.util.ChainObject.location.Address;
import serveur.util.security.Key;

public class User extends Identity {
    protected static final String SRC_PATH = Identity.SRC_PATH + (String) (DataProp
            .read(Paths.get("./ressouces/platform/init.prop").toFile()))
            .get("UserIdentityChainObjectSourcePath");
    private String firstName;
    private ArrayList<String> otherName;
    private Account mainAccount;
    private ArrayList<Account> privateAccounts;
    private ArrayList<Account> publicAccounts;

    /* Construcor */
    /* Base constructor */
    public User(Key signature, String name, LocalDate birthDate, Address address, String firstName,
            ArrayList<String> otherName, Account mainAccount, ArrayList<Account> privateAccount,
            ArrayList<Account> publicAccount) {
        super(signature, name, birthDate, address);
        this.firstName = firstName;
        this.otherName = otherName;
        this.mainAccount = mainAccount;
        this.privateAccounts = privateAccount;
        this.publicAccounts = publicAccount;
    }

    /* File Constructor */
    public User(File fileName) {
        this(readUser(fileName).getSignature(), readUser(fileName).getName(), readUser(fileName).getBirthDate(),
                readUser(fileName).getAddress(), readUser(fileName).getFirstName(), readUser(fileName).getOtherName(),
                readUser(fileName).getMainAccount(), readUser(fileName).getPrivateAccounts(),
                readUser(fileName).getPublicAccounts());
    }

    /* Key Signature Constructor */
    public User(Key signature) {
        this(Paths.get(SRC_PATH + signature.getPublickeyString() + ".prop").toFile());
    }

    private User(Identity identity, String firstName,
            ArrayList<String> otherName, Account mainAccount, ArrayList<Account> privateAccount,
            ArrayList<Account> publicAccount) {
        this(identity.getSignature(), identity.getName(), identity.getBirthDate(), identity.getAddress(), firstName,
                otherName, mainAccount, privateAccount, publicAccount);
    }

    /* Getter and setter method */
    public ArrayList<Account> getPrivateAccounts() {
        return privateAccounts;
    }

    public ArrayList<Account> getPublicAccounts() {
        return publicAccounts;
    }

    public String getFirstName() {
        return firstName;
    }

    public ArrayList<String> getOtherName() {
        return otherName;
    }

    public Account getMainAccount() {
        return mainAccount;
    }

    /* Override method */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((otherName == null) ? 0 : otherName.hashCode());
        result = prime * result + ((mainAccount == null) ? 0 : mainAccount.hashCode());
        result = prime * result + ((privateAccounts == null) ? 0 : privateAccounts.hashCode());
        result = prime * result + ((publicAccounts == null) ? 0 : publicAccounts.hashCode());
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
        User other = (User) obj;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (otherName == null) {
            if (other.otherName != null)
                return false;
        } else if (!otherName.equals(other.otherName))
            return false;
        if (mainAccount == null) {
            if (other.mainAccount != null)
                return false;
        } else if (!mainAccount.equals(other.mainAccount))
            return false;
        if (privateAccounts == null) {
            if (other.privateAccounts != null)
                return false;
        } else if (!privateAccounts.equals(other.privateAccounts))
            return false;
        if (publicAccounts == null) {
            if (other.publicAccounts != null)
                return false;
        } else if (!publicAccounts.equals(other.publicAccounts))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [firstName=" + firstName + ", otherName=" + otherName + ", mainAccount=" + mainAccount
                + ", privateAccounts=" + privateAccounts + ", publicAccounts=" + publicAccounts + "] extend "
                + super.toString();
    }

    @Override
    public Properties toWriteFormat() {
        Properties properties = super.toWriteFormat();
        properties.setProperty("firstName", firstName);
        properties.setProperty("otherName", String.join(" ", otherName));
        properties.setProperty("mainAccount", mainAccount.getSignature().getPublickeyString());
        properties.setProperty("privateAccounts",
                String.join(" ", getChainObjectSignaturePublicKeytoSring(new ArrayList<ChainObject>(privateAccounts))));
        properties.setProperty("publicAccounts",
                String.join(" ", getChainObjectSignaturePublicKeytoSring(new ArrayList<ChainObject>(publicAccounts))));
        return properties;
    }

    /* Static Method */

    private static User readUser(File fileName) {
        Properties properties = DataProp.read(fileName);
        if (properties != null) {
            Identity id = new Identity(fileName);
            return new User(id, (String) properties.get("firstName"),
                    new ArrayList<String>(
                            Arrays.asList(((String) properties.get("otherName")).split(" "))),
                    null,
                    null, null);
        } else {
            return null;
        }
    }
}
