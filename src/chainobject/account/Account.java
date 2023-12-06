package chainobject.account;

import java.time.LocalDate;

import chainobject.ChainObject;
import chainobject.Wallet.Wallet;

public abstract class Account extends ChainObject {
    private String name;
    private LocalDate birthDate;
    private Wallet wallet;

    public Account(String name, LocalDate birtDate, Wallet wallet) {
        this.name = name;
        this.birthDate = birtDate;
        this.wallet = wallet;

        ChainObject.generate(this);
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Wallet getWallet() {
        return wallet;
    }

    @Override
    public String toString() {
        return super.toString() + "Account [name=" + name + ", birthDate=" + birthDate + ", wallet=" + wallet + "]";
    }

}
