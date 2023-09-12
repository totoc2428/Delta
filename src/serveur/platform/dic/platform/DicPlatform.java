package serveur.platform.dic.platform;

import java.io.File;
import java.nio.file.Paths;

import serveur.platform.dic.Dic;

public class DicPlatform extends Dic {
    public DicPlatform(String name) {
        super(Paths.get("." + File.separator + "resources" + File.separator + "serveur"
                + File.separator + "platform" + File.separator + name + ".csv").toFile());
    }
}
