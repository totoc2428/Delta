package serveur.platform.show.style;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import serveur.platform.show.ShowObject;

public class ShowStyle implements ShowObject {
    private ArrayList<String> strList;

    public ShowStyle() {
        this.strList = new ArrayList<String>();

    }

    public void clear() {
        strList.clear();
    }

    public void setLastLine(String str) {
        strList.set(strList.size() - 1, str);
    }

    public void addLine(String str) {
        strList.add(str);
    }

    public void show() {
        System.out.print("\033[H\033[2J");
        for (String str : strList) {
            System.out.print(str);
        }
    }

    public ArrayList<String> getStrList() {
        return strList;
    }

    public void waitTime() {
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
