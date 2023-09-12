package serveur.platform.dic.language;

public enum Language {
    DEFAULT("DEFAULT", "default"),
    FRANCAIS("FR", Language.DEFAULT.getResourcesValue()),
    ENGLISH("EN", "english");

    private String writeValue;
    private String ressourcesValue;

    private Language(String writeValue, String ressourcesValue) {
        this.writeValue = writeValue;
        this.ressourcesValue = ressourcesValue;
    }

    public String getWriteValue() {
        return writeValue;
    }

    public String getResourcesValue() {
        return ressourcesValue;
    }

    public static Language valueOfWriteValue(String writeValue) {
        for (Language l : Language.values()) {
            if (l.getWriteValue().equals(writeValue)) {
                return l;
            }
        }

        return Language.DEFAULT;
    }
}
