package util.exception.chainobject;

import java.io.File;

import util.data.DataProp;

public class ChainObjectGenerateException extends Exception {
    public ChainObjectGenerateException(File fileName) {
        super(DataProp.read(fileName).getProperty("ChainObjectGenerateException"));
    }
}
