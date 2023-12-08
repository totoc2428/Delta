package chainobject.Wallet;

import java.nio.file.Paths;

import chainobject.ChainObject;
import util.data.DataProp;

public class Transaction extends ChainObject {
    protected static final String SRC_PATH = ChainObject.SRC_PATH + DataProp
            .read(Paths.get(DataProp.read(Paths.get("./resources/config/init.conf").toFile())
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("TransactionChainObjectSrcFolder");
    private Wallet giver;
    private Wallet receiver;
    private String name;
    private Double amount;
    private ChainObject consideration;

    public Transaction(Wallet giver, Wallet receiver, String name, Double amount, ChainObject consideration) {
        super();
        this.giver = giver;
        this.receiver = receiver;
        this.name = name;
        this.amount = amount;
        this.consideration = consideration;

        ChainObject.generate(this);
    }

    public Wallet getGiver() {
        return giver;
    }

    public Wallet getReceiver() {
        return receiver;
    }

    public String getName() {
        return name;
    }

    public Double getAmount() {
        return amount;
    }

    public ChainObject getConsideration() {
        return consideration;
    }

    @Override
    public String toString() {
        return super.toString() + "Transaction [giver=" + giver + ", receiver=" + receiver + ", amount=" + amount
                + ", consideration="
                + consideration + "]";
    }

}
