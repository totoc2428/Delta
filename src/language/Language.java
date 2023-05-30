package src.language;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;

import src.data.DataCSV;

public class Language {
    private String key, languageValue;
    private HashMap<String, String> data;

    public Language(String key, String languageValue) {
        this.key = key;
        this.languageValue = languageValue;
        this.data = DataCSV.toHashMap(Paths.get("resources" + File.separator + "language" + File.separator
                + languageValue + File.separator + key + ".csv").toFile());
    }

    public String getLanguageValue() {
        return languageValue;
    }

    public String getKey() {
        return key;
    }

    public HashMap<String, String> getData() {
        return data;
    }
}
