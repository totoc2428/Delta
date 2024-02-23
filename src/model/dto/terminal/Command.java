package model.dto.terminal;

import java.util.Properties;

public class Command {
    private Properties properties;

    public Command(Properties properties) {
        this.properties = properties;
    }

    public String getPropertyWithLangueage(String key, String languagePreferences) {
        return this.properties.getProperty(key + languagePreferences);
    }

    public String getProperty(String key) {
        return this.properties.getProperty(key);
    }

    public String getName() {
        return getProperty("name");
    }

    public String getMainOutput(String languagePreferences) {
        return getPropertyWithLangueage("mainOutput_", languagePreferences);
    }

    public String getDescription(String languagePreferences) {
        return getPropertyWithLangueage("description_", languagePreferences);
    }

    public boolean isValid(String languagePreferences) {
        boolean isValid = false;

        if (properties != null) {
            isValid = !getProperty("name").toString().isBlank()
                    && !getProperty("name").toString().isEmpty();

            isValid = isValid && !getProperty("description_" + languagePreferences).toString().isBlank()
                    && !getProperty("description_" + languagePreferences).toString().isEmpty();

            isValid = isValid && !getProperty("mainOutput_" + languagePreferences).toString().isBlank()
                    && !getProperty("mainOutput_" + languagePreferences).toString().isEmpty();
        }

        return isValid;
    }

    @Override
    public String toString() {
        return "Command [properties=" + properties + "]";
    }

}
