package main.model.dto.terminal;

import main.util.style.TerminalStyle;

public class TerminalMessage {
    private TerminalStyle style;
    private String content;

    public TerminalMessage(String content, TerminalStyle style) {
        this.content = content;
        this.style = style;
    }

    public String getContent() {
        return content;
    }

    public TerminalStyle getStyle() {
        return style;
    }

    public void show() {
        switch (style) {
            case DONE:
                TerminalStyle.showDone(content);
                break;
            case ERROR:
                TerminalStyle.showError(content);
                break;
            case WARNING:
                TerminalStyle.showWarning(content);
                break;
            case INFORMATION:
                TerminalStyle.showInformation(content);
                break;
            default:
                TerminalStyle.showNeutral(content);
                break;
        }
    }
}
