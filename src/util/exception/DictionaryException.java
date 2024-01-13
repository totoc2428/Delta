package util.exception;

import java.nio.file.Paths;
import java.util.Properties;

import util.data.Prop;

public abstract class DictionaryException extends Exception {
    private final static Properties INIT_PROPERTIES = Prop.read(Paths.get("./resources/config/init.conf").toFile());
    private final static Properties LANGUAGE_CONFIG = Prop
            .read(Paths.get(INIT_PROPERTIES.getProperty("LanguageConfig")).toFile());
    protected final static Properties LANGUAGE_FILE = Prop
            .read(Paths
                    .get("./resources/language" + LANGUAGE_CONFIG.getProperty("DeviceLanguage") + "/device/error.conf")
                    .toFile());

    public DictionaryException(String messageKey) {
        super(LANGUAGE_FILE.getProperty(messageKey));
    }
}
