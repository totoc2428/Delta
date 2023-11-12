package serveur.util.chainobject.person.organisation;

import java.time.LocalDate;
import java.util.HashMap;

import serveur.util.chainobject.location.Address;
import serveur.util.chainobject.person.Identity;
import serveur.util.chainobject.person.user.User;
import serveur.util.security.Key;

public class OrganisationNonProfit {// extends Organisation {

    /* Constructor : */
    /**
     * @param signature the signature of the Organisation
     *                  {@link serveur.util.security.Key}.
     * @param name      the name of the Non profit Organisation.
     * @param birthDate the birth date of the Non profit Organisation.
     * @param address   the main adress of the Non profit Organisation.
     * @param president the president of the Non profit Organisation.
     * @param owners    the owners/members of the Non profit Organisation.
     */
    OrganisationNonProfit(Key signature, boolean encryptedSave, String name, LocalDate birthDate, Address address,
            User president,
            HashMap<Identity, Double> owners) {
        // super(signature, encryptedSave, name, birthDate, address, president, owners);

    }

}
