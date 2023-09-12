package serveur.platform.show.logo;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

import serveur.platform.show.ShowObject;
import serveur.platform.show.style.TerminalStyle;
import serveur.util.data.DataText;

public class Logo implements ShowObject {
    private int size;
    public ArrayList<String> strList;

    public Logo(int size) {
        this.size = size;
        this.strList = DataText
                .TextToArrayListOfString(Paths.get("." + File.separator + "resources" + File.separator + "serveur"
                        + File.separator + "logo" + File.separator + "logo" + size + "x" + size + ".txt").toFile());
        System.out.println(Paths.get("." + File.separator + "resources" + File.separator + "serveur"
                + File.separator + "logo" + File.separator + "logo" + size + "x" + size + ".txt").toFile().exists());
    }

    public void show() {
        for (String str : strList) {
            str = str == null ? "" : str;
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == '0') {
                    System.out.print(' ');
                }
                if (TerminalStyle.isCharValueValid(str.charAt(i))) {
                    System.out.print(TerminalStyle.valueOfCharValue(str.charAt(i)).getShowValue() + "" + str.charAt(i));
                }
            }
            System.out.print("\n" + TerminalStyle.WHITE.getShowValue());
        }
    }

    public int getSize() {
        return size;
    }
}
