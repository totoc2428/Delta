package chainobject.currency;

import java.nio.file.Paths;
import java.util.Properties;

import chainobject.ChainObject;
import util.data.DataProp;
import util.exception.chainobject.currency.CurrencySetMaxTokenCapacityException;
import util.security.Key;

public class Currency extends ChainObject {
    protected static final String SRC_PATH = ChainObject.SRC_PATH + DataProp
            .read(Paths.get(DataProp.read(Paths.get("./resources/config/init.conf").toFile())
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("CurrencyChainObjectSrcFolder");
    /**
     * The name of the currency.
     */
    private String name;
    /**
     * The number of the token all ready generated for the currency.
     */
    private int numberToken;

    /**
     * The number of the maxToken who the currency can generate.
     */
    private int maxTokenCapacity;

    /**
     * If the capacity of the maxTokenCapacity can change. /!\ If this atribute is
     * false you can never update the {@link Currency#maxTokenCapacity} value.
     */
    private boolean capacityCanEvolve;

    /* base constructor */
    public Currency(String name, int numberToken, int maxTokenCapacity, boolean capacityCanEvolve) {
        super();
        this.name = name;
        this.numberToken = numberToken;
        this.maxTokenCapacity = maxTokenCapacity;
        this.capacityCanEvolve = capacityCanEvolve;

        ChainObject.generate(this);
    }

    /* getter */
    public String getName() {
        return name;
    }

    public int getNumberToken() {
        return numberToken;
    }

    public int getMaxTokenCapacity() {
        return maxTokenCapacity;
    }

    public boolean isCapacityCanEvolve() {
        return capacityCanEvolve;
    }

    /* setter */
    public void SetMaxTokenCapacity(int maxTokenCapacity) {
        if (capacityCanEvolve) {
            this.maxTokenCapacity = maxTokenCapacity;
        } else {
            new CurrencySetMaxTokenCapacityException();
        }
    }

    /* overrided method */
    @Override
    public String toString() {
        return "Currency [name=" + name + ", numberToken=" + numberToken + ", maxTokenCapacity=" + maxTokenCapacity
                + ", capacityCanEvolve=" + capacityCanEvolve + "]";
    }

    @Override
    public void write() {
        super.write(this.initWrite(), SRC_PATH + Key.publicKeyToString(getPublicKey()));
    }

    @Override
    protected Properties initWrite() {
        Properties properties = super.initWrite();

        writeInProperties(properties, "name", name, true);
        writeInProperties(properties, "numberToken", numberToken + "", true);
        writeInProperties(properties, "maxTokenCapacity", maxTokenCapacity + "", true);
        writeInProperties(properties, "capacityCanEvolve", capacityCanEvolve + "", true);

        return properties;
    }

    /* currency method */
    /**
     * @param signature the signature of the name of the currency signed with the
     *                  user to check if is in the saved result.
     * @return a {@link Boolean} type : true if the token is generated.
     */
    public boolean generateToken(String signature) {
        boolean generated = false;
        if (Key.verifySignature(getPublicKey(), name, signature)) {
            if (numberToken + 1 <= maxTokenCapacity) {
                // new Token();
                // TODO check the generation
            }
        }

        return generated;
    }

}
