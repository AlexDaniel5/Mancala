package mancala;

public class KalahRules extends GameRules {
    private static final long serialVersionUID = -5201804264702268819L;
    private static final int PLAYER_ONE = 6;
    private final MancalaDataStructure data;

    /**
     * Constructs a new instance of the KalahRules class.
     * It initializes the game data structure.
     */
    public KalahRules() {
        super();
        data = getDataStructure();
    }

    /**
     * Moves stones according to the Kalah game rules.
     *
     * @param startPit   The index of the pit from which the stones are moved.
     * @param playerNum  The number representing the current player (1 or 2).
     * @return The number of stones moved.
     * @throws InvalidMoveException if the move is not valid.
     */
    @Override
    public int moveStones(final int startPit, final int playerNum) throws InvalidMoveException {
        if (data.getNumStones(startPit) == 0 || startPit < 1 || startPit > 12
        || startPit <= PLAYER_ONE  && playerNum == 2 || startPit > PLAYER_ONE && playerNum == 1) {
            throw new InvalidMoveException();
        }
        final int initialStore = data.getStoreCount(playerNum);
        distributeStones(startPit);
        return data.getStoreCount(playerNum) - initialStore;
    }

    /**
     * Distributes stones in pits according to the Kalah game rules.
     *
     * @param startPit The index of the pit from which the stones are distributed.
     * @return The number of stones distributed.
     */
    @Override
    public int distributeStones(final int startPit) {
        final int playerTurn = whoseTurn(startPit);
        int movingStones = data.removeStones(startPit);
        final int totalStones = movingStones;
        Countable currentSpot = null;
        data.setIterator(startPit, playerTurn, false);
        while (movingStones > 0) {
            currentSpot = data.next();
            currentSpot.addStone();
            movingStones--;
        }
        final int stoppingPoint = (startPit + totalStones) % 13;
        if (currentSpot.getStoneCount() == 1 
        && (playerTurn == 1 && stoppingPoint <= PLAYER_ONE
        || playerTurn == 2 && stoppingPoint > PLAYER_ONE)) {
            data.addToStore(playerTurn, captureStones(stoppingPoint));
        }
        return totalStones;
    }

    /**
     * Captures stones in pits according to the Kalah game rules.
     *
     * @param stoppingPoint The index of the pit where the stones are stopped.
     * @return The number of stones captured.
     */
    @Override
    public int captureStones(final int stoppingPoint) {
        final int capturedPit = 13 - stoppingPoint;
        data.removeStones(stoppingPoint);
        return data.removeStones(capturedPit) + 1;
    }

    /**
     * Determines whose turn it is based on the starting pit index.
     *
     * @param startPit The index of the pit from which the stones are moved.
     * @return The player number (1 or 2) whose turn it is.
     */
    private int whoseTurn (final int startPit) {
        return startPit > PLAYER_ONE ? 2 : 1;
    }
}