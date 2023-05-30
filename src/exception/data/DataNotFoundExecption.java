package src.exception.data;

import src.language.Language;

public class DataNotFoundExecption extends Exception {
    public DataNotFoundExecption(Language l) {
        super(l.getData().get("DataNotFoundExecption"));
    }

    public DataNotFoundExecption(String message) {
        super(message);
    }
}
