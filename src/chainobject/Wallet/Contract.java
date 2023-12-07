package chainobject.Wallet;

import java.nio.file.Paths;
import java.security.PublicKey;
import java.util.ArrayList;

import chainobject.ChainObject;
import chainobject.person.Person;
import util.data.DataProp;
import util.exception.chainobject.wallet.BeneficiarySignException;

public class Contract extends ChainObject {
    protected final static String SRC_PATH = DataProp
            .read(Paths.get(DataProp.read(Paths.get("./resources/config/init.conf").toFile())
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("ContractChainObjectSrcFolder");

    private Person beneficiary;
    private Person signatory;
    private ArrayList<Transaction> transactions;
    private boolean finished;
    private String name;
    private String text;
    private boolean BeneficiarySigned;
    private boolean SignatorySigned;

    public Contract(Person beneficiary, Person signatory, ArrayList<Transaction> transactions, boolean finished,
            String name, String text, boolean BeneficiarySigned, boolean SignatorySigned) {
        super();
        this.beneficiary = beneficiary;
        this.signatory = signatory;
        this.transactions = transactions;
        this.finished = finished;
        this.name = name;
        this.text = text;
        this.BeneficiarySigned = BeneficiarySigned;
        this.SignatorySigned = SignatorySigned;

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

    public boolean getBeneficiarySigned() {
        return BeneficiarySigned;
    }

    public boolean getSignatorySigned() {
        return SignatorySigned;
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
                + ", finished=" + finished + ", name=" + name + ", text=" + text + ", BeneficiarySigned="
                + BeneficiarySigned + ", SignatorySigned=" + SignatorySigned + "]";
    }

}
