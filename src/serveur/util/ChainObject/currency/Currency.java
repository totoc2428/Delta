package serveur.util.ChainObject.currency;

import serveur.util.ChainObject.ChainObject;
import serveur.util.ChainObject.block.Block;
import serveur.util.security.Key;

public class Currency extends ChainObject {

    private double value;

    public Currency(Key signature, double value) {
        super(signature);
    }

    public Currency(Key signature) {
        this(signature, 0);
    }

    public static Currency introduceNewCoin(Block block, double value) {
        if (block.isValid()) {
            return new Currency(block.getSignature(), value);
        } else {
            return null;
        }
    }

    public double getValue() {
        return value;
    }
}
