package model.controleurs.node.terminal;

import java.util.Properties;

import model.dao.DataManager;
import model.dto.blockchain.chainobject.person.Person;

public class TerminalControleur {
    private final static Properties TERMINALCONTROLEUR_PROPERTIES = DataManager
            .read(DataManager.INIT_PROPERTIES.getProperty("TERMINALCONTROLEUR_PROPERTIES"));

    private Person identity;

    public TerminalControleur() {
        this.identity = null;
    }

    public Person getIdentity() {
        return identity;
    }
}
