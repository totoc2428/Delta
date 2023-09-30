package serveur.platform.dic.show.error;

import serveur.platform.dic.show.DicShow;
import serveur.platform.dic.show.Language;
import serveur.platform.show.style.TerminalStyle;

public class DicShowError extends DicShow {
    private static final TerminalStyle COLOR = TerminalStyle.RED;

    public DicShowError(Language language) {
        super(language, "error");
    }

    public static TerminalStyle getColor() {
        return COLOR;
    }

    public String getValue(String key) {
        return COLOR.getShowValue() + super.getValue(key) + TerminalStyle.RESET.getShowValue() + "\n";
    }
}
