package src.serveur.util.repot.format;

import src.serveur.util.repot.Repot;

public class RepotFormat {
    private String message;
    private RepotFormatName format;
    private Repot from;
    private Repot to;

    public RepotFormat(String message, Repot from, Repot to, RepotFormatName format) {
        this.message = message;
        this.from = from;
        this.to = to;
        this.format = format;

    }

    public RepotFormat(String message, Repot from, Repot to) {
        this.message = message;
        this.from = from;
        this.to = to;
        this.format = RepotFormatName.MESSAGE;
    }

    public String getMessage() {
        return message;
    }

    public Repot getFrom() {
        return from;
    }

    public Repot getTo() {
        return to;
    }

    public RepotFormatName getFormat() {
        return format;
    }
}
