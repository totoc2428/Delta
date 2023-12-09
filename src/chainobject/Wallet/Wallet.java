package chainobject.Wallet;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import chainobject.ChainObject;
import util.data.DataProp;
import util.security.Key;

public class Wallet extends ChainObject {
    protected static final String SRC_PATH = ChainObject.SRC_PATH + DataProp
            .read(Paths.get(DataProp.read(Paths.get("./resources/config/init.conf").toFile())
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("WalletChainObjectSrcFolder");
    /**
     * The global Amount of the Wallet.
     */
    private Double globalAmount;
    /*
     * The proprety owned by the Wallet. The {@link ChainObject} is the proprety
     * type. The {@link Double} i the percetange of owners. Must to be between 0 and
     * 1.
     */
    private HashMap<ChainObject, Double> propety;
    /*
     * The transaction in live. All this transaction will be encrypted with the
     * publicKey is here in waiting of the owners of the wallet
     * accept it.
     */
    private ArrayList<Transaction> transactions;
    /*
     * The history of all transaction passed and already accepted by the owners of
     * the Wallet.
     */
    private ArrayList<Transaction> transactionsHistory;
    /* The contract porposed by the wallet. */
    private ArrayList<Contract> contracts;

    /* base constructor */
    public Wallet(Double globalAmount, HashMap<ChainObject, Double> propety, ArrayList<Transaction> transactionsHistory,
            ArrayList<Contract> contracts) {
        super();
        this.globalAmount = globalAmount;
        this.propety = propety;
        this.transactionsHistory = transactionsHistory;
        this.contracts = contracts;

        ChainObject.generate(this);
    }

    /* getter */

    public Double getGlobalAmount() {
        return globalAmount;
    }

    public HashMap<ChainObject, Double> getPropety() {
        return propety;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public ArrayList<Transaction> getTransactionsHistory() {
        return transactionsHistory;
    }

    public ArrayList<Contract> getContracts() {
        return contracts;
    }

    /* overided */
    @Override
    public String toString() {
        return super.toString() + "Wallet [globalAmount=" + globalAmount + ", propety=" + propety
                + ", transactionsHistory="
                + transactionsHistory + ", contracts=" + contracts + "]";
    }

    @Override
    public void write() {
        super.write(this.initWrite(), SRC_PATH + Key.publicKeyToString(getPublicKey()));
    }

    @Override
    public Properties initWrite() {
        Properties properties = super.initWrite();
        writeInProperties(properties, "globalAmount", globalAmount, false);
        writeInProperties(properties, "propety", propety, false);
        writeInProperties(properties, "transactions", transactions, false);
        writeInProperties(properties, "transactionsHistory", transactionsHistory, false);
        writeInProperties(properties, "contracts", contracts, false);
        return properties;
    }
}
