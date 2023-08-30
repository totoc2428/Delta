package serveur.network;

public class BlockNotValidException extends Exception {
    public BlockNotValidException() {
        super("Block is not valid !");
    }
}
