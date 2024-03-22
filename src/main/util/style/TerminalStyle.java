package main.util.style;

public enum TerminalStyle {
    INFORMATION(TerminalColor.BLUE
            .getTerminalCode(), "[i]"),
    WARNING(TerminalColor.YELLOW
            .getTerminalCode(), "/!\\"),
    ERROR(TerminalColor.RED
            .getTerminalCode(), "[x]"),
    DONE(TerminalColor.GREEN
            .getTerminalCode(), "[v]"),
    RESET(TerminalColor.RESET.getTerminalCode(), "");

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
        System.out.println(
                terminalStyle.terminalStyle + terminalStyle.icon + " " + message.replace("\"", "") + getResetValue());
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
