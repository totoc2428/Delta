package src.platform.terminal;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import src.data.DataText;

public class Logo {
    static ArrayList<String> lines;
    public static final String GREEN = "\033[0;32m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String RESET = "\033[0m";

    public Logo(int size) {
        Path p = Paths
                .get("resources" + File.separator + "logo" + File.separator + "logo" + size + "x" + size + ".txt");
        lines = DataText.totable(p.toFile());
    }

    public void show() {
        for (String line : lines) {
            if (line != null) {
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == '&') {
                        System.out.print(GREEN + line.charAt(i));
                    } else if (line.charAt(i) == '#') {
                        System.out.print(BLUE + line.charAt(i));
                    } else if (line.charAt(i) == '/') {
                        System.out.print(PURPLE + line.charAt(i));
                    } else {
                        System.out.print(RESET + ' ');
                    }
                }
                System.out.print(RESET + "\n");
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
