package mancala;

public class AyoRules extends GameRules {
    private static final long serialVersionUID = 7743800825656180523L;
    private static final int PLAYER_ONE = 6;
    private final MancalaDataStructure data;
    
    /**
     * Constructs a new instance of the AyoRules class.
     * It initializes the game data structure.
     */
    public AyoRules() {
        super();
        data = getDataStructure();
    }

    /**
     * Moves stones according to the Ayo game rules.
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
     * Distributes stones in pits according to the Ayo game rules.
     *
     * @param startPit The index of the pit from which the stones are distributed.
     * @return The number of stones distributed.
     */
    @Override
    public int distributeStones(final int startPit) {
        final int playerTurn = whoseTurn(startPit);
        int movingStones = data.removeStones(startPit);
        int distributedStones = 0;
        Countable currentSpot = null;
        int currentPosition = startPit;
        final int skipPit = startPit > PLAYER_ONE ? startPit + 1 : startPit;
        data.setIterator(startPit, playerTurn, true);
        while (movingStones > 0) {
            currentPosition = incrementPosition(currentPosition, skipPit, playerTurn);
            currentSpot = data.next();
            currentSpot.addStone();
            movingStones--;
            distributedStones++;
            if (movingStones == 0 && currentSpot.getStoneCount() > 1 && currentPosition != 7 && currentPosition != 14) {    
                movingStones += currentSpot.removeStones();
            }
        }
        //Must be a nested if statement to avoid out of bounds pit error
        if (currentPosition != 14 && currentPosition != 7) {
            if (currentSpot.getStoneCount() == 1
            && playerTurn == 1 && currentPosition <= PLAYER_ONE
            || playerTurn == 2 && currentPosition > PLAYER_ONE) {
                data.addToStore(playerTurn, captureStones(currentPosition));
            }
        }
        return distributedStones;
    }

    /**
     * Captures stones in pits according to the Ayo game rules.
     *
     * @param stoppingPoint The index of the pit where the stones are stopped.
     * @return The number of stones captured.
     */
    @Override
    public int captureStones(final int stoppingPoint) {
        int capturedPit = 0;
        if (stoppingPoint > 6) {
            capturedPit++;
        }
        capturedPit += 13 - stoppingPoint;
        return data.removeStones(capturedPit);
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

    /**
     * Increments the current position based on the skipping pit and player turn.
     *
     * @param currentPosition The current position of the stones.
     * @param skipPit         The pit index to be skipped during stone distribution.
     * @param playerTurn      The player whose turn it is (1 or 2).
     * @return The updated position after incrementing.
     */
    private int incrementPosition (final int currentPosition, final int skipPit, final int playerTurn) {
        int move = currentPosition % 14 + 1;
        if (move == skipPit) {
           move++;
        }
        if (playerTurn == 2 && move == 7) {
            move++;
        } else if (playerTurn == 1 && move == 14) {
            move = 1;
        }
        return move;
    }
}