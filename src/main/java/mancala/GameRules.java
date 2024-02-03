package mancala;

import java.io.Serializable;

/**
 * Abstract class representing the rules of a Mancala game.
 * KalahRules and AyoRules will subclass this class.
 */
public abstract class GameRules implements Serializable {
    private static final long serialVersionUID = -7510294783110181913L;
    final private MancalaDataStructure gameBoard;
    private int currentPlayer = 1; // Player number (1 or 2)
    private static final int PLAYER_ONE = 6;

    /**
     * Constructor to initialize the game board.
     */
    public GameRules() {
        gameBoard = new MancalaDataStructure();
        gameBoard.setUpPits();
    }

    /**
     * Get the number of stones in a pit.
     *
     * @param pitNum The number of the pit.
     * @return The number of stones in the pit.
     */
    public int getNumStones(final int pitNum) {
        return gameBoard.getNumStones(pitNum);
    }

    /**
     * Get the game data structure.
     *
     * @return The MancalaDataStructure.
     */
    //CHANGED TO PUBLIC FOR PMD
    public MancalaDataStructure getDataStructure() {
        return gameBoard;
    }

    /**
     * Check if a side (player's pits) is empty.
     *
     * @param pitNum The number of a pit in the side.
     * @return True if the side is empty, false otherwise.
     */
    //CHANGED TO PUBLIC FOR PMD
    public boolean isSideEmpty(final int pitNum) {
        boolean sideEmpty = true;
        int maxPit = 6;
        int minPit = 1;
        if (pitNum > PLAYER_ONE ) {
            maxPit = 12;
            minPit = 7;
        }
        for (int i = minPit; i <= maxPit; i++) {
            if (getNumStones(i) > 0) {
                sideEmpty = false;
            }
        }
        return sideEmpty;
    }

    /**
     * Set the current player.
     *
     * @param playerNum The player number (1 or 2).
     */
    public void setPlayer(final int playerNum) {
        currentPlayer = playerNum;
    }

    /**
     * Perform a move and return the number of stones added to the player's store.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player making the move.
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException If the move is invalid.
     */
    public abstract int moveStones(int startPit, int playerNum) throws InvalidMoveException;

    /**
     * Distribute stones from a pit and return the number distributed.
     *
     * @param startPit The starting pit for distribution.
     * @return The number of stones distributed.
     */
    //CHANGED TO PUBLIC FOR PMD
    public abstract int distributeStones(int startPit);

    /**
     * Capture stones from the opponent's pit and return the number captured.
     *
     * @param stoppingPoint The stopping point for capturing stones.
     * @return The number of stones captured.
     */
    //CHANGED TO PUBLIC FOR PMD
    public abstract int captureStones(int stoppingPoint);

    /**
     * Register two players and set their stores on the board.
     *
     * @param one The first player.
     * @param two The second player.
     */
    public void registerPlayers(final Player one, final Player two) {
        // Create new stores for each player
        final Store storeOne = new Store();
        final Store storeTwo = new Store();

         // Set the stores for each player using the data structure's method
        gameBoard.setStore(storeOne, 1);
        gameBoard.setStore(storeTwo, 2);

        //Set the stores to their player class
        one.setStore(storeOne);
        two.setStore(storeTwo);

        // Set the owner of the stores
        storeOne.setOwner(one);
        storeTwo.setOwner(two);
    }

    /**
     * Reset the game board by setting up pits and emptying stores.
     */
    public void resetBoard() {
        for (int i = 1; i <= 12; i++) {
            gameBoard.removeStones(i);
        }
        gameBoard.setUpPits();
        gameBoard.emptyStores();
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        //Append stone counts for each pit
        for (int i = 1; i <= 12; i++) {
            result.append("Pit ").append(i).append(": ").append(getNumStones(i)).append("\n");
        }
        //Append store counts
        result.append("Store Counts: ");
        result.append("Player 1: ").append(gameBoard.getStoreCount(1)).append(", ");
        result.append("Player 2: ").append(gameBoard.getStoreCount(2)).append("\n");
        return result.toString();
    }
}
