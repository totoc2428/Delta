package serveur.platform.dic.show.warning;

import serveur.platform.dic.show.DicShow;
import serveur.platform.dic.show.Language;
import serveur.platform.show.style.TerminalStyle;

public class DicShowWarning extends DicShow {
    private static final TerminalStyle COLOR = TerminalStyle.YELLOW;

    public DicShowWarning(Language l) {
        super(l, "warning");
    }

    public static TerminalStyle getColor() {
        return COLOR;
    }

    @Override
    public String getDicValue(String key) {
        return COLOR.getShowValue() + dic.get(key) + TerminalStyle.RESET.getShowValue() + "\n";
    }
}