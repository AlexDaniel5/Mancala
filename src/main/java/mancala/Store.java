package mancala;

import java.io.Serializable;

public class Store implements Countable, Serializable{
    private static final long serialVersionUID = 625942305929771717L;
    private int totalStones;
    private Player owner;

    //Constructor method
    public Store() {
        totalStones = 0;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(final Player player) {
        owner = player;
    }

    @Override
    public void addStone() {
        totalStones++;
    }
    //Add stones to the store
    @Override
    public void addStones(final int amount) {
        totalStones += amount;
    }

    @Override
    public int getStoneCount() {
        return totalStones;
    }

    //Reset stones to 0 and return the number of stones that were in the store
    @Override
    public int removeStones() {
        final int stonesInStore = totalStones;
        totalStones = 0;
        return stonesInStore;
    }
}