package mancala;

import java.io.Serializable;

public class UserProfile implements Serializable {
    private static final long serialVersionUID = 2041953433416022924L;
    private final String name;
    private int kalahGamesPlayed;
    private int ayoGamesPlayed;
    private int kalahGamesWon;
    private int ayoGamesWon;

    public UserProfile(final String userName) {
        name = userName;
    }

    public String getUserName() {
        return name;
    }

    public int getKalahGamesPlayed() {
        return kalahGamesPlayed;
    }

    public void incrementKalahGamesPlayed() {
        kalahGamesPlayed++;
    }

    public int getAyoGamesPlayed() {
        return ayoGamesPlayed;
    }

    public void incrementAyoGamesPlayed() {
        ayoGamesPlayed++;
    }

    public int getKalahGamesWon() {
        return kalahGamesWon;
    }

    public void incrementKalahGamesWon() {
        kalahGamesWon++;
    }

    public int getAyoGamesWon() {
        return ayoGamesWon;
    }

    public void incrementAyoGamesWon() {
        ayoGamesWon++;
    }
}