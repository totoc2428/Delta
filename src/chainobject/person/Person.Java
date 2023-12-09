package chainobject.person;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;

import chainobject.ChainObject;
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

    private String name;
    private LocalDate birthDate;
    private boolean isVerified;

    private PublicAccount mainAccount;
    private ArrayList<PrivateAccount> privateAccounts;
    private ArrayList<Skill> skills;

    public Person(String name, LocalDate birthDate, boolean isVerified, PublicAccount mainAccount,
            ArrayList<PrivateAccount> privateAccounts, ArrayList<Skill> skills) {
        super();
        this.name = name;
        this.birthDate = birthDate;
        this.isVerified = isVerified;

        this.mainAccount = mainAccount;
        this.privateAccounts = privateAccounts;

        this.skills = skills;

        ChainObject.generate(this);
    }

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

    public ArrayList<Skill> getSkills() {
        return skills;
    }

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
        writeInProperties(properties, "name", name);
        writeInProperties(properties, "birthDate", birthDate.toString());
        writeInProperties(properties, "isVerified", isVerified + "");
        writeInProperties(properties, "mainAccount", Key.privateKeyToString(getPrivateKey()));
        StringBuilder stringBuilder = new StringBuilder();
        for (PrivateAccount privateAccount : privateAccounts) {
            stringBuilder.append(Key.privateKeyToString(privateAccount.getPrivateKey()) + " ");
        }
        writeInProperties(properties, "privateAccounts", stringBuilder.toString());
        stringBuilder = new StringBuilder();
        for (Skill skill : skills) {
            stringBuilder.append(Key.privateKeyToString(skill.getPrivateKey()) + " ");
        }
        writeInProperties(properties, "privateAccounts", stringBuilder.toString());

        return properties;
    }

}
