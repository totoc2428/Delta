package serveur.platform.show.style;

public enum TerminalStyle {
    BLUE("\033[34m", "/B/", '#'), BOLD("\033[1m", "~B~", '~'),
    GREEN("\033[32m", "/G/", '&'), ITALIC("\033[2m", "", '~'),
    RED("\033[31m", "/R/", '.'),
    PURPLE("\033[35m", "/P/", '/'),
    WHITE("\033[37m", "/W/", ','),
    YELLOW("\033[33m", "/Y/", ':');

    private String showValue;
    private String writeValue;
    private char charValue;

    private TerminalStyle(String showValue, String writeValue, char charValue) {
        this.showValue = showValue;
        this.writeValue = writeValue;
        this.charValue = charValue;
    }

    public char getCharValue() {
        return charValue;
    }

    public String getShowValue() {
        return showValue;
    }

    public String getWriteValue() {
        return writeValue;
    }

    public static boolean isCharValueValid(char c) {
        return TerminalStyle.valueOfCharValue(c) != null;
    }

    public static TerminalStyle valueOfCharValue(char c) {
        for (TerminalStyle Tr : TerminalStyle.values()) {
            if (Tr.getCharValue() == c) {
                return Tr;
            }
        }
        return null;
    }
}
