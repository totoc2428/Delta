package serveur.util.ChainObject.currency;

import java.io.File;
import java.util.Properties;

import serveur.util.ChainObject.ChainObject;
import serveur.util.ChainObject.block.Block;
import serveur.util.data.prop.DataProp;
import serveur.util.security.Key;

public class Currency extends ChainObject {

    private double value;

    /* Construcor */
    /* Base constructor */
    public Currency(Key signature, double value) {
        super(signature);
    }

    /* Construcor */
    /* Constructor with one key //set value to 0 */
    public Currency(Key signature) {
        this(signature, 0);
    }

    public Currency(File fileName) {
        this(Currency.readCurrency(fileName).getSignature(), Currency.readCurrency(fileName).getValue());
    }

    /* Object method */
    public static Currency introduceNewCoin(Block block, double value) {
        if (block.isValid()) {
            return new Currency(block.getSignature(), value);
        } else {
            return null;
        }
    }

    /* Getter and setter method */
    public double getValue() {
        return value;
    }

    /* Override method */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(value);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Currency other = (Currency) obj;
        if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Currency [value=" + value + "] extended " + super.toString();
    }

    /* Static Method */
    /* Read a Currency saved in a file */
    private static Currency readCurrency(File fileName) {
        Properties properties = DataProp.read(fileName);
        if (properties != null) {
            return new Currency(new Key(fileName), (double) properties.get("value"));
        } else {
            return null;
        }
    }

}
