package mancala;

import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 625942305929771717L;
    private String playerName;
    private Store playerStore;

    //Constructor methods
    public Player() {
        //Default name
        playerName = "Player";
    }
    public Player(final String name) {
        playerName = name;
    }

    //Accessor methods
    public String getName() {
        return playerName;
    }
    public Store getStore() {
        return playerStore;
    }
    public int getStoreCount() {
        return playerStore != null ? playerStore.getStoneCount() : 0;
    }

    //Mutator methods
    public void setName(final String name) {
        playerName = name;
    }
    public void setStore(final Store store) {
        playerStore = store;
    }
    
    //Print the player's name
    @Override
    public String toString() {
        return "Player: " + playerName;
    }
}