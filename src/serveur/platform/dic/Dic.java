package serveur.platform.dic;

import java.io.File;
import java.util.Properties;

import serveur.util.data.prop.DataProp;

public abstract class Dic {
    protected Properties dic;

    public Dic(File file) {
        if (file.exists()) {
            this.dic = DataProp.read(file);

        } else {
            // exception
        }
    }

    public Object getValue(String propertie) {
        return dic.get(propertie);
    }

}
