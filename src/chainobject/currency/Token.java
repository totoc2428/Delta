package chainobject.currency;

import java.util.HashMap;
import java.util.Properties;

import chainobject.ChainObject;
import chainobject.Wallet.Wallet;
import util.security.Key;

public class Token extends ChainObject {

    /**
     * The currency who is linked to the Token.
     */
    private Currency currency;

    /**
     * The number of the token. Is the asscendant order of the generated token.
     */
    private final int NUMBER;
    /*
     * The owners of the token. The String is the signature of any owners of the
     * token. The double is the per of the token who everyOne have.
     */
    private HashMap<String, Double> owners;

    public Token(Currency currency, int NUMBER, HashMap<String, Double> owners) {
        super();
        this.currency = currency;
        this.NUMBER = NUMBER;
        this.owners = owners;

        isOwnable = true;

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
        super.write(this.initWrite(), SRC_PATH + Key.publicKeyToString(getPublicKey()));
    }

    @Override
    protected Properties initWrite() {
        Properties properties = super.initWrite();
        writeInProperties(properties, "currency", currency, true);
        writeInProperties(properties, "NUMBER", NUMBER, true);
        writeInProperties(properties, "owners", owners, false);

        return properties;
    }

    /* token medthod */

    public boolean isOwners(Wallet giver, Wallet receiver) {
        return false;
    }

    public boolean sell(double value, String signature) {
        return false;
    }

}
