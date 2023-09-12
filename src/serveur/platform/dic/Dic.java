package serveur.platform.dic;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import serveur.util.data.DataCSV;

public abstract class Dic {
    protected HashMap<String, String> dic;

    public Dic(File file) {
        if (file.exists()) {
            this.dic = DataCSV.toHashMap(file);
        } else {
            // exception
        }
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
