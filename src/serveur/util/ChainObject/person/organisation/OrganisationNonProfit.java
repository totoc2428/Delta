package serveur.util.ChainObject.person.organisation;

import java.time.LocalDate;
import java.util.HashMap;

import serveur.util.ChainObject.location.Address;
import serveur.util.ChainObject.person.Identity;
import serveur.util.ChainObject.person.user.User;
import serveur.util.security.Key;

public class OrganisationNonProfit extends Organisation {

    OrganisationNonProfit(Key signature, String name, LocalDate localDate, Address address, User president,
            HashMap<Identity, Double> owners) {
        super(signature, name, localDate, address, president, owners);

    }
}
