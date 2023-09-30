package serveur.platform.show.logo;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import serveur.platform.show.ShowObject;
import serveur.platform.show.style.ShowStyle;
import serveur.platform.show.style.TerminalStyle;
import serveur.util.data.text.DataText;

public class Logo implements ShowObject {
    private int size;
    public ArrayList<String> strList;

    public Logo(int size) {
        this.size = size;
        this.strList = DataText
                .TextToArrayListOfString(Paths.get("." + File.separator + "resources" + File.separator + "serveur"
                        + File.separator + "logo" + File.separator + "logo" + size + "x" + size + ".txt").toFile());
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
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public ShowStyle savedShow(ShowStyle showStyle) {
        for (String str : strList) {
            str = str == null ? "" : str;
            String strAdd = "";
            for (int j = 0; j < str.length(); j++) {
                if (str.charAt(j) == '0') {
                    strAdd += " ";
                }
                if (TerminalStyle.isCharValueValid(str.charAt(j))) {
                    strAdd += TerminalStyle.valueOfCharValue(str.charAt(j)).getShowValue() + str.charAt(j)
                            + TerminalStyle.WHITE.getShowValue();
                }
            }
            showStyle.addLine(strAdd + "\n");
        }
        return showStyle;
    }

    public int getSize() {
        return size;
    }
}
