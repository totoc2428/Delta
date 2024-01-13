package chainobject.person.moral;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import chainobject.ChainObject;
import chainobject.account.hidden.PrivateAccount;
import chainobject.account.visible.PublicAccount;
import chainobject.person.Person;
import chainobject.person.skill.Skill;
import util.data.DataProp;
import util.security.Key;

public class MoralPerson extends Person {
    protected static final String SRC_PATH = ChainObject.SRC_PATH + DataProp
            .read(Paths.get(DataProp.read(Paths.get("./resources/config/init.conf").toFile())
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("MoralPersonChainObjectSrcFolder");
    private Person president;
    private HashMap<Person, Double> menbers;
    private String rules;

    /* constructor */
    public MoralPerson(String name, LocalDate birthDate, boolean isVerified, PublicAccount mainAccount,
            ArrayList<PrivateAccount> privateAccount, ArrayList<PublicAccount> publicAccounts, ArrayList<Skill> skills,
            Person president,
            HashMap<Person, Double> menbers, String rules) {
        super(name, birthDate, isVerified, mainAccount, privateAccount, publicAccounts, skills);
        this.president = president;
        this.menbers = menbers;
        this.rules = rules;

        ChainObject.generate(this);
    }

    /* getter */

    public Person getPresident() {
        return president;
    }

    public HashMap<Person, Double> getMenbers() {
        return menbers;
    }

    public String getRules() {
        return rules;
    }

    /* overrided method */
    @Override
    public String toString() {
        return super.toString() + "MoralPerson [president=" + president + ", menbers=" + menbers + ", rules=" + rules
                + "]";
    }

    public void write() {
        super.write(this.initWrite(), SRC_PATH + Key.publicKeyToString(getPublicKey()));
    }

    @Override
    protected Properties initWrite() {
        Properties properties = super.initWrite();
        writeInProperties(properties, "president", president, true);
        writeInProperties(properties, "menbers", menbers, false);
        writeInProperties(properties, "rules", rules, false);

        return properties;
    }
}
