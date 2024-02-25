package util.style;

public enum TerminalColor {
    GREEN("\033[;32m"),
    BLUE("\033[0;34m"),
    RED("\033[0;31m"),
    YELLOW("\033[0;33m"),
    PURPLE("\033[0;35m"),
    RESET("\033[0m");

    private String terminalCode;

    private TerminalColor(String terminalCode) {
        this.terminalCode = terminalCode;
    }

    public String getTerminalCode() {
        return terminalCode;
    }
}
