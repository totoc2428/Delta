package util.terminal;

import java.nio.file.Paths;
import java.util.Properties;

import util.data.Prop;

public enum Value {
    ERROR(), WARNING(), INFORMATION(), DONE(), RESET();

    private String terminal;
    private String logo;

    private Value() {
        Properties properties = Prop
                .read(Paths.get(
                        Prop.read(Paths.get("./resources/config/init.conf").toFile()).getProperty("LanguageConfig"))
                        .toFile());
        this.terminal = properties.getProperty("Terminal" + this.name().toUpperCase() + "Value");
        this.logo = properties.getProperty("logo" + this.name().toUpperCase() + "Value");
    }

    public String getLogo() {
        return logo;
    }

    public String getTerminal() {
        return terminal;
    }

    public static String reset() {
        return Value.RESET.terminal;
    }

    public String show() {
        return terminal + logo;
    }
}
