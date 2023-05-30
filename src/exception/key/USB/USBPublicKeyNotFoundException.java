package src.exception.key.USB;

import src.language.Language;

public class USBPublicKeyNotFoundException extends Exception {
    public USBPublicKeyNotFoundException(Language l) {
        super(l.getData().get("USBPublicKeyNotFoundException"));
    }

    public USBPublicKeyNotFoundException(String mesage) {
        super(mesage);
    }
}