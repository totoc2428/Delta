package src.exception.key.USB;

import src.language.Language;

public class USBPrivateKeyNotFoundException extends Exception {
    public USBPrivateKeyNotFoundException(Language l) {
        super(l.getData().get("USBPrivateKeyNotFoundException"));
    }
}
