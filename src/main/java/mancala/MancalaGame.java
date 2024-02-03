package mancala;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class MancalaGame implements Serializable {
    private static final long serialVersionUID = 8816057423439062283L;
    private static final int PLAYER_NUM_ONE = 1;
    private GameRules board;
    private Player currentPlayer;
    private MancalaDataStructure data;
    private final List<Player> players;

    /**
     * Default constructor for MancalaGame, initializes the game with default rules (KalahRules).
     */
    public MancalaGame() {
        setBoard(new KalahRules());
        data = board.getDataStructure();
        players = new ArrayList();
    }

    /**
     * Constructor for MancalaGame with custom game rules.
     *
     * @param rules The custom game rules to be applied.
     */
    public MancalaGame(final GameRules rules) {
        setBoard(rules);
        data = board.getDataStructure();
        players = new ArrayList<>();
    }

    //Accessor methods
    public GameRules getBoard() {
        return board;
    }
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public int getNumStones(final int pitNum) throws PitNotFoundException {
        if (pitNum > 12 || pitNum < 1) {
            throw new PitNotFoundException();
        }
        return board.getNumStones(pitNum);
    }
    public int getStoreCount(final Player player) throws NoSuchPlayerException {
        for (final Player playerName : players) {
            if (player.equals(playerName)) {
                return player.getStoreCount();
            }
        }
        throw new NoSuchPlayerException();
    }

    //Mutator methods
    public void setBoard(final GameRules theBoard) {
        board = theBoard;
    }
    public void setCurrentPlayer(final Player player) {
        currentPlayer = player;
    }
    public void setPlayers(final Player onePlayer, final Player twoPlayer) {
        players.clear();
        players.add(onePlayer);
        players.add(twoPlayer);
        setCurrentPlayer(onePlayer);
    }
    public void setRules(final GameRules newRules) {
        board = newRules;
    }

    /**
     * Gets the winner of the Mancala game.
     *
     * @return The winning player, or null in case of a tie.
     * @throws GameNotOverException If the game is not yet over, and a winner cannot be determined.
     */
    public Player getWinner() throws GameNotOverException {
        if (!isGameOver()){
            throw new GameNotOverException();
        }
        Player winner;
        int player1Score = 0;
        int player2Score = 0;
        for (int i = 1; i <= 6; i++) {
            player1Score +=  board.getNumStones(i);
        }
        for (int i = 7; i <= 12; i++) {
            player2Score +=  board.getNumStones(i);
        }
        player1Score += data.getStoreCount(1);
        player2Score += data.getStoreCount(2);
        if (player1Score > player2Score) {
            winner = players.get(0);
        } else if (player1Score < player2Score) {
            winner = players.get(1);
        } else {
            winner = null; //tie
        }
        return winner;
    }

    /**
     * Checks if the game is over by determining if one side of the board is empty.
     *
     * @return True if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        return board.isSideEmpty(1) || board.isSideEmpty(7);
    }

    /**
     * Starts a new game of Mancala.
     */
    public void startNewGame() {
        board.resetBoard();
        currentPlayer = players.get(0);
        data = board.getDataStructure();
    }

    /**
     * Moves stones from a selected pit and returns the total number of stones on the player's side.
     *
     * @param startPit The index of the pit from which stones are moved.
     * @return The total number of stones on the player's side after the move.
     * @throws InvalidMoveException If the move is invalid.
     */
    public int move(final int startPit) throws InvalidMoveException {
        // Check if the move is valid, belongs to the current player
        if (startPit < 1 || startPit > 12) {
            throw new InvalidMoveException();
        }
        final int stonesToMove = board.getNumStones(startPit);
        // Check if there are stones to move
        if (stonesToMove == 0) {
            throw new InvalidMoveException();
        }
        final int player = startPit > 6 ? 2 : 1;
        int totalStones = 0;
        // Distribute the stones from the selected pit
        board.moveStones(startPit, player);
        //Calculate the number of stones on the player's side
        //Also add the captured stones to the player's store
        if (player == PLAYER_NUM_ONE) { 
            for (int i = 0; i < 6; i++) {
                totalStones += board.getNumStones(i);
            }
        } else {
            for (int i = 6; i < 12; i++) {
                totalStones += board.getNumStones(i);
            }
        }
        return totalStones;
    }

    /**
     * Returns a string representation of the Mancala game state, including pit and store information.
     *
     * @return A string representing the Mancala game state.
     */
    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Mancala Game State:\n");
        stringBuilder.append("Current Player: ").append(currentPlayer.getName()).append("\n");

        for (int i = 1; i <= 12; i++) {
            stringBuilder.append("Pit ").append(i).append(": ").append(board.getNumStones(i)).append(" stones\n");
        }

        stringBuilder.append("Player 1 Store: ").append(data.getStoreCount(1)).append(" stones\n");
        stringBuilder.append("Player 2 Store: ").append(data.getStoreCount(2)).append(" stones\n");

        return stringBuilder.toString();
    }

    /**
     * Checks if the current player is eligible for an extra turn based on the last move.
     *
     * @param startPit    The index of the pit from which stones were moved.
     * @param totalStones The total number of stones moved in the last turn.
     * @return True if the current player gets an extra turn, false otherwise.
     */
    public boolean extraTurn(final int startPit, final int totalStones) {
        return board instanceof KalahRules && (currentPlayer.equals(players.get(0)) && 6 - (startPit - 1) == totalStones 
        || currentPlayer.equals(players.get(1)) && 6 - (startPit - 7) == totalStones);
    }
}