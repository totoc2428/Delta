package chainobject.Wallet;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import chainobject.ChainObject;
import util.data.DataProp;

public class Wallet extends ChainObject {
    protected static final String SRC_PATH = ChainObject.SRC_PATH + DataProp
            .read(Paths.get(DataProp.read(Paths.get("./resources/config/init.conf").toFile())
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("WalletChainObjectSrcFolder");
    private Double globalAmount;
    private HashMap<ChainObject, Double> propety;
    private ArrayList<Transaction> transactionsHistory;
    private ArrayList<Contract> contracts;

    public Wallet(Double globalAmount, HashMap<ChainObject, Double> propety, ArrayList<Transaction> transactionsHistory,
            ArrayList<Contract> contracts) {
        super();
        this.globalAmount = globalAmount;
        this.propety = propety;
        this.transactionsHistory = transactionsHistory;
        this.contracts = contracts;

        ChainObject.generate(this);
    }

    public Double getGlobalAmount() {
        return globalAmount;
    }

    public HashMap<ChainObject, Double> getPropety() {
        return propety;
    }

    public ArrayList<Transaction> getTransactionsHistory() {
        return transactionsHistory;
    }

    public ArrayList<Contract> getContracts() {
        return contracts;
    }

    @Override
    public String toString() {
        return super.toString() + "Wallet [globalAmount=" + globalAmount + ", propety=" + propety
                + ", transactionsHistory="
                + transactionsHistory + ", contracts=" + contracts + "]";
    }

}
