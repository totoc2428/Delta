package serveur.platform.dic.show.information;

import serveur.platform.dic.show.DicShow;
import serveur.platform.dic.show.Language;
import serveur.platform.show.style.TerminalStyle;

public class DicShowInformation extends DicShow {
    private static final TerminalStyle COLOR = TerminalStyle.BLUE;

    public DicShowInformation(Language l) {
        super(l, "information");
    }

    public static TerminalStyle getColor() {
        return COLOR;
    }

    @Override
    public String getValue(String key) {
        return COLOR.getShowValue() + super.getValue(key) + TerminalStyle.RESET.getShowValue() + "\n";
    }
}
