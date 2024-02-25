package model.dto.terminal;

import util.style.TerminalStyle;

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

    }
}
