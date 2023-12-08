package chainobject.person.moral;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import chainobject.ChainObject;
import chainobject.account.hidden.PrivateAccount;
import chainobject.account.visible.PublicAccount;
import chainobject.person.Person;
import chainobject.person.skill.Skill;
import util.data.DataProp;

public class MoralPerson extends Person {
    protected static final String SRC_PATH = ChainObject.SRC_PATH + DataProp
            .read(Paths.get(DataProp.read(Paths.get("./resources/config/init.conf").toFile())
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("MoralPersonChainObjectSrcFolder");
    private Person president;
    private HashMap<Person, Double> menbers;
    private String rules;

    public MoralPerson(String name, LocalDate birthDate, boolean isVerified, PublicAccount mainAccount,
            ArrayList<PrivateAccount> privateAccount, ArrayList<Skill> skills, Person president,
            HashMap<Person, Double> menbers, String rules) {
        super(name, birthDate, isVerified, mainAccount, privateAccount, skills);
        this.president = president;
        this.menbers = menbers;
        this.rules = rules;

        ChainObject.generate(this);
    }

    public Person getPresident() {
        return president;
    }

    public HashMap<Person, Double> getMenbers() {
        return menbers;
    }

    public String getRules() {
        return rules;
    }

    @Override
    public String toString() {
        return super.toString() + "MoralPerson [president=" + president + ", menbers=" + menbers + ", rules=" + rules
                + "]";
    }

}
