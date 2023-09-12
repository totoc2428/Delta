package serveur.platform.dic;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import serveur.util.data.DataCSV;

public class Dic {
    HashMap<String, String> dic;

    public Dic(Language l, String dicName) {
        this.dic = DataCSV.toHashMap(Paths.get("." + File.separator + "resources" + File.separator + "serveur"
                + File.separator + "language" + File.separator + l.getResourcesValue() + File.separator + dicName
                + ".csv").toFile());
    }

    public HashMap<String, String> getDic() {
        return dic;
    }

    public String getDicValue(String key) {
        return dic.get(key);
    }

    public String getDicKey(String value) {
        for (Map.Entry<String, String> entry : dic.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
