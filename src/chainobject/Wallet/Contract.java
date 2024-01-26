package chainobject.Wallet;

import java.nio.file.Paths;
import java.security.PublicKey;
import java.util.ArrayList;

import chainobject.ChainObject;
import chainobject.person.Person;
import util.data.Prop;
import util.exception.chainobject.wallet.BeneficiarySignException;

public class Contract extends ChainObject {
    protected final static String SRC_PATH = Prop
            .read(Paths.get(Prop.read(Paths.get("./resources/config/init.conf").toFile())
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("ContractChainObjectSrcFolder");

    /**
     * The beneficiary of the contract. The beneficiary is a {@link Person} type who
     * the contract is purposed.
     */
    private Person beneficiary;
    private Person signatory;
    private ArrayList<Transaction> transactions;
    private boolean finished;
    private String name;
    private String text;
    private boolean beneficiarySigned;
    private boolean signatorySigned;

    public Contract(Person beneficiary, Person signatory, ArrayList<Transaction> transactions, boolean finished,
            String name, String text, boolean beneficiarySigned, boolean signatorySigned) {
        super();
        this.beneficiary = beneficiary;
        this.signatory = signatory;
        this.transactions = transactions;
        this.finished = finished;
        this.name = name;
        this.text = text;
        this.beneficiarySigned = beneficiarySigned;
        this.signatorySigned = signatorySigned;

        ChainObject.generate(this);
    }

    /* Getter : */
    public Person getBeneficiary() {
        return beneficiary;
    }

    public Person getSignatory() {
        return signatory;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public boolean getFinished() {
        return finished;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public boolean getbeneficiarySigned() {
        return beneficiarySigned;
    }

    public boolean getsignatorySigned() {
        return signatorySigned;
    }

    /* method : */
    public void BeneficiarySign(PublicKey beneficiaryKey) {
        try {

        } catch (Exception e) {
            new BeneficiarySignException();
        }
    }

    /* overrided method : */
    @Override
    public String toString() {
        return super.toString() + "Contract [beneficiary=" + beneficiary + ", signatory=" + signatory
                + ", transactions=" + transactions
                + ", finished=" + finished + ", name=" + name + ", text=" + text + ", beneficiarySigned="
                + beneficiarySigned + ", signatorySigned=" + signatorySigned + "]";
    }

}
