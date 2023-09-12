package serveur.platform.dic.language;

import java.io.File;
import java.nio.file.Paths;

import serveur.platform.dic.Dic;

public class DicLanguage extends Dic {
    private Language dicLanguage;

    public DicLanguage(Language l, String dicName) {
        super(Paths.get("." + File.separator + "resources" + File.separator + "serveur"
                + File.separator + "language" + File.separator + l.getResourcesValue() + File.separator + dicName
                + ".csv").toFile());
    }

    public Language getDicLanguage() {
        return dicLanguage;
    }
}
