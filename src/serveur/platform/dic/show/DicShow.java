package serveur.platform.dic.show;

import java.io.File;
import java.nio.file.Paths;

import serveur.platform.dic.Dic;

public class DicShow extends Dic {
    private Language dicLanguage;

    public DicShow(Language l, String dicName) {
        super(Paths.get("." + File.separator + "resources" + File.separator + "serveur"
                + File.separator + "show" + File.separator + l.getResourcesValue() + File.separator + dicName
                + ".csv").toFile());
    }

    public Language getDicLanguage() {
        return dicLanguage;
    }
}
