package chainobject.person.skill;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.logging.Level;

import chainobject.ChainObject;
import util.data.DataProp;

public class Skill extends ChainObject {
    protected static final String SRC_PATH = ChainObject.SRC_PATH + DataProp
            .read(Paths.get(DataProp.read(Paths.get("./resources/config/init.conf").toFile())
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("SkillChainObjectSrcFolder");
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
