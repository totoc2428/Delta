package util.exception.chainobject.wallet;

import java.io.File;

import util.data.DataProp;

public class BeneficiarySignException extends Exception {
    public BeneficiarySignException(File fileName) {
        super(DataProp.read(fileName).getProperty("ChainObjectGenerateException"));
    }
}
