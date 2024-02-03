package mancala;

public class NoSuchPlayerException extends Exception {
    private static final long serialVersionUID = -9048534461650095974L;
    public NoSuchPlayerException() {
        super("No such player");
    }
}