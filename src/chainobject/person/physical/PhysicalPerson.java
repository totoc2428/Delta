package chainobject.person.physical;

import java.time.LocalDate;
import java.util.ArrayList;

import chainobject.ChainObject;
import chainobject.account.hidden.PrivateAccount;
import chainobject.account.visible.PublicAccount;
import chainobject.person.Person;
import chainobject.person.skill.Skill;

public class PhysicalPerson extends Person {
    private ArrayList<String> foreNames;
    private ArrayList<PrivateAccount> privateAccounts;

    public PhysicalPerson(String name, LocalDate birthDate,
            boolean isVerified, ArrayList<Skill> skills, PublicAccount mainAccount,
            ArrayList<PrivateAccount> privateAccounts) {
        super(name, birthDate, isVerified, mainAccount, privateAccounts, skills);

        ChainObject.generate(this);
    }

    public ArrayList<String> getForeNames() {
        return foreNames;
    }

    public ArrayList<PrivateAccount> getPrivateAccounts() {
        return privateAccounts;
    }

}
