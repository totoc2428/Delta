package chainobject.person.skill;

import java.nio.file.Paths;
import java.util.ArrayList;

import chainobject.ChainObject;
import util.data.Prop;

public class Level extends ChainObject {
    protected static final String SRC_PATH = ChainObject.SRC_PATH + Prop
            .read(Paths.get(Prop.read(Paths.get("./resources/config/init.conf").toFile())
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("LevelChainObjectSrcFolder");
    private ArrayList<Domain> domains;
    private int value;
    private String name;
    private String content;
    private String valuation;

    public Level(ArrayList<Domain> domains, int value, String name, String content, String valuation) {
        this.domains = domains;
        this.value = value;
        this.name = name;
        this.content = content;
        this.valuation = valuation;

        ChainObject.generate(this);
    }

    public ArrayList<Domain> getDomains() {
        return domains;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getValuation() {
        return valuation;
    }

    @Override
    public String toString() {
        return super.toString() + "Level [domains=" + domains + ", value=" + value + ", name=" + name + ", content="
                + content
                + ", valuation=" + valuation + "]";
    }

}
