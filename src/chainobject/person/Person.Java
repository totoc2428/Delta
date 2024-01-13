package chainobject.person;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;

import chainobject.ChainObject;
import chainobject.account.Account;
import chainobject.account.hidden.PrivateAccount;
import chainobject.account.visible.PublicAccount;
import chainobject.person.skill.Skill;
import util.data.DataProp;
import util.security.Key;

public abstract class Person extends ChainObject {
    protected static final String SRC_PATH = ChainObject.SRC_PATH + DataProp
            .read(Paths.get(DataProp.read(Paths.get("./resources/config/init.conf").toFile())
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("PersonChainObjectSrcFolder");

    /**
     * The name of the person.
     */
    private String name;

    /**
     * The birth date of the person.
     */
    private LocalDate birthDate;
    /*
     * If the person is verifivied. If is set to true the identity of the person
     * whas checked and verified by other user.
     */
    private boolean isVerified;

    /**
     * The main account of the Person. This account is public. Is the main account
     * of the Person. This account have the SAME name than the name of the Person.
     */
    private PublicAccount mainAccount;
    /*
     * The private account of the person. This accounts is private.
     */
    private ArrayList<PrivateAccount> privateAccounts;
    /*
     * The other public Account of the compagnie.
     */
    private ArrayList<PublicAccount> publicAccounts;
    /*
     * The skills of the Person.
     */
    private ArrayList<Skill> skills;

    /* constructor */
    public Person(String name, LocalDate birthDate, boolean isVerified, PublicAccount mainAccount,
            ArrayList<PrivateAccount> privateAccounts, ArrayList<PublicAccount> publicAccounts,
            ArrayList<Skill> skills) {
        super();
        this.name = name;
        this.birthDate = birthDate;
        this.isVerified = isVerified;

        this.mainAccount = mainAccount;
        this.publicAccounts = publicAccounts;
        this.privateAccounts = privateAccounts;

        this.skills = skills;

        ChainObject.generate(this);
    }

    /* getter */
    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public boolean getIsVerified() {
        return isVerified;
    }

    public PublicAccount getMainAccount() {
        return mainAccount;
    }

    public ArrayList<PrivateAccount> getPrivateAccounts() {
        return privateAccounts;
    }

    public ArrayList<PublicAccount> getPublicAccounts() {
        return publicAccounts;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    /* overrided method */
    @Override
    public String toString() {
        return "Person [name=" + name + ", birthDate=" + birthDate + ", isVerified=" + isVerified + ", mainAccount="
                + mainAccount + ", privateAccounts=" + privateAccounts + ", skills=" + skills + "]";
    }

    @Override
    public void write() {
        super.write(this.initWrite(), SRC_PATH + Key.publicKeyToString(getPublicKey()));
    }

    @Override
    protected Properties initWrite() {
        Properties properties = super.initWrite();

        writeInProperties(properties, "name", name, true);
        writeInProperties(properties, "birthDate", birthDate.toString(), false);
        writeInProperties(properties, "isVerified", isVerified + "", true);
        writeInProperties(properties, "mainAccount", Key.privateKeyToString(getPrivateKey()), true);
        writeInProperties(properties, "privateAccounts", privateAccounts, false);
        writeInProperties(properties, "skills", skills, false);

        return properties;
    }

    /* person method */

    /**
     * This method update the {@link Person#name} of the Person to a new name.
     * 
     * @param name the new name of the Person.
     *             /!\ In do that you will lost the verified checking.
     */
    public void updateName(String name) {
        this.name = name;
        this.isVerified = false;
    }

    public void addAccount(Account account) {
        if (account.getPrivateKey() != null) {

        }
    }
}
