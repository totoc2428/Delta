package chainobject.person.moral.profit;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import chainobject.ChainObject;
import chainobject.account.hidden.PrivateAccount;
import chainobject.account.visible.PublicAccount;
import chainobject.person.Person;
import chainobject.person.moral.MoralPerson;
import chainobject.person.skill.Skill;
import util.data.DataProp;

public class ProfitMoralPerson extends MoralPerson {
        protected static final String SRC_PATH = MoralPerson.SRC_PATH + DataProp
                        .read(Paths.get(DataProp.read(Paths.get("./resources/config/init.conf").toFile())
                                        .getProperty("ChainObjectConfig")).toFile())
                        .getProperty("ProfitMoralPersonChainObjectSrcFolder");

        public ProfitMoralPerson(String name, LocalDate birthDate, boolean isVerified, PublicAccount mainAccount,
                        ArrayList<PrivateAccount> privateAccounts, ArrayList<Skill> skills, Person president,
                        HashMap<Person, Double> menbers, String rules) {
                super(name, birthDate, isVerified, mainAccount, privateAccounts, skills, president, menbers, rules);
                isOwnable = true;

                ChainObject.generate(this);
        }

}
