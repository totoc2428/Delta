package chainobject.person.skills;

import java.nio.file.Paths;
import java.util.Properties;

import util.data.DataProp;

public enum Domain {
    AGRICULTURE, ARCHITECTURE,
    BIOLOGY,
    CHEMISTRY, COMPUTER, COOK, CRAFT,
    ECONOMY, ENERGY,
    GEOGRAPHY, GEOLOGY,
    HISTORY,
    JOURNALISM,
    INDUSTRY,
    MATHEMATIC,
    MEDECINE, MILITARY,
    LANGUAGE, LETTER,
    PHILOSOPHY, PHYSIC, POLITIC,
    SOCIOLOGY;

    private String name;
    private String badge;

    private final Properties INIT_PROPERTIES = DataProp.read(Paths.get("./resources/config/init.conf").toFile());
    private final Properties DOMAIN_PROPERTIES = DataProp
            .read(Paths.get(INIT_PROPERTIES.getProperty("DomainConfig")).toFile());

    private Domain() {
        this.name = this.toString().toUpperCase();
        this.badge = DOMAIN_PROPERTIES.getProperty(name);

    }

    public String getBadge() {
        return badge;
    }

    public String getName() {
        return name;
    }
}
