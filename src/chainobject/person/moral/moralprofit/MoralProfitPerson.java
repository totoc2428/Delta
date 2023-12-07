package chainobject.person.moral.moralprofit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import chainobject.ChainObject;
import chainobject.account.hidden.PrivateAccount;
import chainobject.account.visible.PublicAccount;
import chainobject.person.Person;
import chainobject.person.moral.MoralPerson;
import chainobject.person.skills.Skill;

public class MoralProfitPerson extends MoralPerson {
    public MoralProfitPerson(String name, LocalDate birthDate, boolean isVerified, PublicAccount mainAccount,
            ArrayList<PrivateAccount> privateAccounts, ArrayList<Skill> skills, Person president,
            HashMap<Person, Double> menbers, String rules) {
        super(name, birthDate, isVerified, mainAccount, privateAccounts, skills, president, menbers, rules);
        isOwnable = true;

        ChainObject.generate(this);
    }
}
