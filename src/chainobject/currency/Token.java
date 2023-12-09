package chainobject.currency;

import java.util.HashMap;
import java.util.Properties;

import chainobject.ChainObject;
import util.security.Key;

public class Token extends ChainObject {
    private Currency currency;
    private final int NUMBER;
    private HashMap<String, Double> owners;

    public Token(Currency currency, int NUMBER, HashMap<String, Double> owners) {
        super();
        this.currency = currency;
        this.NUMBER = NUMBER;
        this.owners = owners;

        ChainObject.generate(this);
    }

    /* getter */
    public Currency getCurrency() {
        return currency;
    }

    public int getNUMBER() {
        return NUMBER;
    }

    public HashMap<String, Double> getOwners() {
        return owners;
    }

    /* overrided method */
    @Override
    public String toString() {
        return "Token [currency=" + currency + ", NUMBER=" + NUMBER + ", owners=" + owners + "]";
    }

    @Override
    public void write() {

    }

    @Override
    protected Properties initWrite() {
        Properties properties = super.initWrite();
        writeInProperties(properties, "currency", Key.publicKeyToString(currency.getPublicKey()));
        writeInProperties(properties, "NUMBER", NUMBER + "");
        StringBuilder stringBuilder = new StringBuilder();
        for (String signature : owners.keySet()) {
            stringBuilder.append(signature + " ");
        }
        writeInProperties(properties, "ownersKEY_MAP", stringBuilder.toString());
        stringBuilder = new StringBuilder();
        for (Double value : owners.values()) {
            stringBuilder.append(value + " ");
        }
        writeInProperties(properties, "ownersVALUE_MAP", stringBuilder.toString());

        return properties;
    }
}
