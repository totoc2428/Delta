package util.exception;

import java.nio.file.Paths;
import java.util.Properties;

import util.data.Prop;

public abstract class DictionaryExceptionApplication extends Exception {
    private static String srcFolder = null;
    private final static Properties INIT_PROPERTIES = Prop.read(Paths.get("./resources/config/init.conf").toFile());
    private static Properties languageConfig;
    private static Properties languageFile;

    protected static void setSrcFolder(String srcFolderEdit) {
        srcFolder = srcFolderEdit;
        languageConfig = Prop
                .read(Paths.get(INIT_PROPERTIES.getProperty("LanguageConfig")).toFile());
        languageFile = Prop
                .read(Paths
                        .get("./resources/application/" + srcFolder + languageConfig.getProperty("DeviceLanguage")
                                + "/error.conf")
                        .toFile());
    }

    public DictionaryExceptionApplication(String messageKey) {
        super(languageFile != null ? languageFile.getProperty(messageKey) : "");
        if (languageFile != null) {
            // new DictionarySystemException("");
        }
    }
}
