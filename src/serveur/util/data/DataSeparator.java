package serveur.util.data;

public enum DataSeparator {
    VALUE(';'), LIST('#'), DIC('%');

    private char c;

    private DataSeparator(char c) {
        this.c = c;
    }

    public char getSeparatorChar() {
        return this.c;
    }
}
