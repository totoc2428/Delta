package chainobject.person.skills;

import java.util.ArrayList;

import chainobject.ChainObject;

public class Level extends ChainObject {
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
