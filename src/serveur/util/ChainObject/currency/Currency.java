package serveur.util.chainobject.currency;

import java.io.File;
import java.nio.file.Paths;
import java.util.Properties;

import serveur.network.Network;
import serveur.util.chainobject.ChainObject;
import serveur.util.chainobject.block.Block;
import serveur.util.data.prop.DataProp;
import serveur.util.security.Key;

public class Currency extends ChainObject {
    protected static final String SRC_PATH = ChainObject.SRC_PATH + (String) (DataProp
            .read(Paths.get("./ressouces/platform/init.prop").toFile()))
            .get("CurrencyChainObjectSourcePath");
    private double value;

    /* Construcor */
    /**
     * Base Constructor
     *
     * @param signature     the signature of the Currency instance.
     * @param encryptedSave the type of saving of the object.
     * @param value         the value of the Currency instance. The value is
     *                      depandanding of the number of the devices on the
     *                      network.
     */
    private Currency(Key signature, boolean encryptedSave, double value) {
        super(signature, encryptedSave);
        this.value = value;
    }

    /**
     * Key Construcor
     * 
     * @param signature the Currency pointed by the signature must be placed in
     *                  SRC_PATH of the Currency type.
     */
    public Currency(Key signature) {
        this(Paths.get(SRC_PATH + signature.getPublickeyString() + ".prop").toFile());
    }

    /**
     * File Constructor
     * Take a fileName to make a instance of new Block.
     * 
     * @param fileName must be a type properties (.prop) and respect the syntax of
     *                 key-value pairs.
     */
    public Currency(File fileName) {
        this(Currency.readCurrency(fileName).getSignature(), Currency.readCurrency(fileName).getEncryptedSave(),
                Currency.readCurrency(fileName).getValue());
    }

    /**
     * Introduce new coin with a block.
     * The block need to be valid.
     * 
     * @param block a Block of saved operation in the network who is valid.
     * @param value the value estimate for the new Coin. This estimate value will be
     *              divise by the size of the network.
     * @return New currency if the block is valid. null if not. The value of the
     *         currency can be very close of zero.
     */
    public static Currency introduceNewCoin(Block block, double value, Network network) {
        if (block.isValid()) {
            return new Currency(block.getSignature(), true, value / network.size());
        } else {
            return null;
        }
    }

    /**
     * Getter of the coin value.
     * 
     * @return the value of the Currecy instance.
     */
    public double getValue() {
        return value;
    }

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

    /**
     * Read a currency from a file.
     * 
     * @param fileName a properties file who respecte the syntax of a Currency.
     * @return the Currency saved in a file. If an error is ocurate the medhod
     *         return {@null}.
     */
    private static Currency readCurrency(File fileName) {
        Properties properties = DataProp.read(fileName);
        if (properties != null) {
            ChainObject chainObject = new ChainObject(fileName);

            return new Currency(chainObject.getSignature(), chainObject.getEncryptedSave(),
                    (double) properties.get("value"));
        } else {
            return null;
        }
    }

}
