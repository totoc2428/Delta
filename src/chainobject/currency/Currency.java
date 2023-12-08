package chainobject.currency;

import java.security.PrivateKey;

import chainobject.ChainObject;

public class Currency extends ChainObject {

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

    public Currency(String name, int numberToken, int maxTokenCapacity, boolean capacityCanEvolve) {
        super();
        this.name = name;
        this.numberToken = numberToken;
        this.maxTokenCapacity = maxTokenCapacity;
        this.capacityCanEvolve = capacityCanEvolve;

        ChainObject.generate(this);
    }

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

    public boolean generateToken(PrivateKey UserprivateKey) {
        boolean generated = false;
        if (getPrivateKey().equals(UserprivateKey)) {
            if (numberToken + 1 > maxTokenCapacity) {

            }
        }

        return generated;
    }
}
