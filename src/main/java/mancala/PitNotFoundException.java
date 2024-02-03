package mancala;

public class PitNotFoundException extends Exception {
    private static final long serialVersionUID = -7493299591901498662L;
    public PitNotFoundException() {
        super("Pit not found");
    }
}