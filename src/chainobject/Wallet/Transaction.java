package chainobject.Wallet;

import chainobject.ChainObject;

public class Transaction extends ChainObject {
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
