package model.controleurs.node.terminal.style;

public enum TerminalStyle {
    INFORMATION("\033[0;34m", "[i]"),
    WARNING("\033[0;33m'", "/!\\"),
    ERROR("\033[0;31m", "[x]"),
    DONE("\033[;32m", "[v]"),
    RESET("\033[0m", "");

    private String terminalStyle;
    private String icon;

    private TerminalStyle(String terminalStyle, String icon) {
        this.terminalStyle = terminalStyle;
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public String getTerminalStyle() {
        return terminalStyle;
    }

    public String getAllValue() {
        return this.terminalStyle + this.icon;
    }

    public static String getResetValue() {
        return TerminalStyle.RESET.getTerminalStyle();
    }

    private static void show(TerminalStyle terminalStyle, String message) {
        System.out.println(terminalStyle.terminalStyle + terminalStyle.icon + " " + message + getResetValue());
    }

    public static void showInformation(String message) {
        show(INFORMATION, message);

    }

    public static void showWarning(String message) {
        show(WARNING, message);

    }

    public static void showDone(String message) {
        show(DONE, message);

    }

    public static void showError(String message) {
        show(ERROR, message);

    }

    public static void showNeutral(String message) {
        show(RESET, message);
    }
}
