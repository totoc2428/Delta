package serveur.platform.dic.show.error;

import serveur.platform.dic.show.DicShow;
import serveur.platform.dic.show.Language;
import serveur.platform.show.style.TerminalStyle;

public class DicShowError extends DicShow {
    private static final TerminalStyle COLOR = TerminalStyle.RED;

    public DicShowError(Language l) {
        super(l, "error");
    }

    public static TerminalStyle getColor() {
        return COLOR;
    }

    @Override
    public String getDicValue(String key) {
        return COLOR.getShowValue() + dic.get(key) + TerminalStyle.RESET.getShowValue() + "\n";
    }
}
