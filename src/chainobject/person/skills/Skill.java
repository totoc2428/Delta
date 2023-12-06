package chainobject.person.skills;

import java.util.HashMap;
import java.util.logging.Level;

import chainobject.ChainObject;

public class Skill extends ChainObject {
    private String name;
    private HashMap<Level, Double> levels;
    private boolean isCheck;

    public Skill(String name, HashMap<Level, Double> levels, boolean isCheck) {
        this.name = name;
        this.levels = levels;
        this.isCheck = isCheck;

        ChainObject.generate(this);
    }

    public String getName() {
        return name;
    }

    public HashMap<Level, Double> getLevels() {
        return levels;
    }

    public boolean getIsCheck() {
        return isCheck;
    }

    @Override
    public String toString() {
        return super.toString() + "Skill [name=" + name + ", levels=" + levels + ", isCheck=" + isCheck + "]";
    }

}
