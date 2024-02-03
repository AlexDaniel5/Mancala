package ui;

import mancala.AyoRules;
import mancala.GameNotOverException;
import mancala.KalahRules;
import mancala.MancalaGame;
import mancala.Player;
import mancala.UserProfile;
import mancala.GameRules;
import mancala.MancalaDataStructure;
import mancala.InvalidMoveException;
import mancala.Saver;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory; 
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class MancalaUI extends JFrame {
    private JFrame gameFrame;
    private JComboBox<String> gameTypeComboBox;
    private JTextField player1NameField;
    private JTextField player2NameField;
    private JLabel playerLabel;
    private JLabel player1StoreLabel;
    private JLabel player2StoreLabel;
    private JLabel player1StatsLabel;
    private JLabel player2StatsLabel;
    private JLabel player1KalahGamesPlayedLabel;
    private JLabel player1KalahGamesWonLabel;
    private JLabel player2KalahGamesPlayedLabel;
    private JLabel player2KalahGamesWonLabel;
    private JLabel player1AyoGamesPlayedLabel;
    private JLabel player1AyoGamesWonLabel;
    private JLabel player2AyoGamesPlayedLabel;
    private JLabel player2AyoGamesWonLabel;
    private JPanel pitPanel;
    private Player player1;
    private Player player2;
    private UserProfile userProfile1;
    private UserProfile userProfile2;
    private GameRules rules;
    private MancalaDataStructure data = new MancalaDataStructure();
    private MancalaGame mancalaGame;

    //Constructor
    public MancalaUI() {
        //Display the game window title; same as the jframe line for the game window
        super("Mancala Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        //Add the input panel to the north side of the initial window
        JPanel inputPanel = createInputPanel();
        add(inputPanel, BorderLayout.NORTH);
        //Add the start game button to the center of the window with an event
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        add(startButton, BorderLayout.CENTER);
        //Center the frame and pack it to a preferred size
        pack();
        setLocationRelativeTo(null);
    }

    private void resetGame(GameRules newRules) {
        mancalaGame.setRules(newRules);
        rules = newRules;
        mancalaGame.startNewGame();
        mancalaGame.setPlayers(player1, player2);
        data.emptyStores();
        updateUI();
        playerLabel.setText(userProfile1.getUserName() + " vs " + userProfile2.getUserName() + ": " + mancalaGame.getCurrentPlayer().getName() + "'s Turn");
    }

    //Input panel for initial window
    private JPanel createInputPanel() {
        //Create a grid layout with three rows and two columns
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        //First row
        inputPanel.add(new JLabel("Player 1 Name:"));
        player1NameField = new JTextField();
        inputPanel.add(player1NameField);
        //Second row
        inputPanel.add(new JLabel("Player 2 Name:"));
        player2NameField = new JTextField();
        inputPanel.add(player2NameField);
        //Third row
        inputPanel.add(new JLabel("Game Type:"));
        String[] gameTypes = {"Kalah", "Ayo"};
        gameTypeComboBox = new JComboBox<>(gameTypes);
        inputPanel.add(gameTypeComboBox);
        
        return inputPanel;
    }

    //Event when start game button is pressed
    private void startGame() {
        //Obtain player's names and the selected game type
        String player1Name = player1NameField.getText();
        String player2Name = player2NameField.getText();
        String gameType = (String) gameTypeComboBox.getSelectedItem();
        //Default name check
        if (player1Name.isEmpty()) {
            player1Name = "Player 1";
        }
        if (player2Name.isEmpty()) {
            player2Name = "Player 2";
        }    
        //Set the rules in MancalaGame
        if ("Ayo".equals(gameType)) {
            rules = new AyoRules();
        } else if ("Kalah".equals(gameType)) {
            rules = new KalahRules();
        } else {
            //Error check
            JOptionPane.showMessageDialog(this, "Invalid game type selected.");
            return;
        }
        mancalaGame = new MancalaGame(rules);
        //Set the two player's profiles and the game objects
        userProfile1 = new UserProfile(player1Name);
        userProfile2 = new UserProfile(player2Name);
        player1 = new Player(player1Name);
        player2 = new Player(player2Name);
        data = new MancalaDataStructure();
        mancalaGame.setPlayers(player1, player2);
        //Display the game window and dispose of the starting window
        displayGameWindow();
        dispose();
    }

    private void displayGameWindow() {
        //Dispose an old gameframe if we're loading a new game
        if (gameFrame != null) {
            gameFrame.dispose();
        }
        //Set the initial attributes of the game window
        gameFrame = new JFrame("Mancala Game");
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameFrame.setLayout(new BorderLayout());
        //Set Menu
        JMenuBar menuBar = new JMenuBar();
        gameFrame.setJMenuBar(menuBar);
        //Set new game submenu
        JMenu newGameMenu = new JMenu("New Game");
        menuBar.add(newGameMenu);
        JMenuItem ayoOption = new JMenuItem("Ayo");
        ayoOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame(new AyoRules());
            }
        });
        newGameMenu.add(ayoOption);
        JMenuItem kalahOption = new JMenuItem("Kalah");
        kalahOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame(new KalahRules());
            }
        });
        newGameMenu.add(kalahOption);
        //Set settings menu
        JMenu settingsMenu = new JMenu("Settings");
        menuBar.add(settingsMenu);
        //Save submenu
        JMenu saveMenu = new JMenu("Save");
        JMenuItem saveGameOption = new JMenuItem("Game");
        saveGameOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGame();
            }
        });
        saveMenu.add(saveGameOption);
        JMenu savePlayerMenu = new JMenu("Player");
        JMenuItem savePlayer1Option = new JMenuItem("Player 1");
        savePlayer1Option.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePlayer(1);
            }
        });
        savePlayerMenu.add(savePlayer1Option);

        // Save Player2 option
        JMenuItem savePlayer2Option = new JMenuItem("Player 2");
        savePlayer2Option.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePlayer(2);
            }
        });
        savePlayerMenu.add(savePlayer2Option);
        saveMenu.add(savePlayerMenu);
        settingsMenu.add(saveMenu);
        //Load submenu
        JMenu loadMenu = new JMenu("Load");
        JMenuItem loadGameOption = new JMenuItem("Game");
        loadGameOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadGame();
            }
        });
        loadMenu.add(loadGameOption);
        JMenu loadPlayerMenu = new JMenu("Player");
        JMenuItem loadPlayer1Option = new JMenuItem("Player 1");
        loadPlayer1Option.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadPlayer(1);
                updateStats();
                if (!mancalaGame.isGameOver()) {
                    playerLabel.setText(userProfile1.getUserName() + " vs " + userProfile2.getUserName() + ": " + mancalaGame.getCurrentPlayer().getName() + "'s Turn");
                }
            }
        });
        loadPlayerMenu.add(loadPlayer1Option);
        JMenuItem loadPlayer2Option = new JMenuItem("Player 2");
        loadPlayer2Option.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadPlayer(2);
                updateStats();
                if (!mancalaGame.isGameOver()) {
                    playerLabel.setText(userProfile1.getUserName() + " vs " + userProfile2.getUserName() + ": " + mancalaGame.getCurrentPlayer().getName() + "'s Turn");
                }
            }
        });
        loadPlayerMenu.add(loadPlayer2Option);
        loadMenu.add(loadPlayerMenu);
        settingsMenu.add(loadMenu);
        //Vs title
        playerLabel = new JLabel(userProfile1.getUserName() + " vs " + userProfile2.getUserName() + ": " + mancalaGame.getCurrentPlayer().getName() + "'s Turn", SwingConstants.CENTER);
        playerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        //Check if game is already done
        if (mancalaGame.isGameOver()) {
            Player winner = null;
            try {
                winner = mancalaGame.getWinner();
            } catch (GameNotOverException e) {
            e.printStackTrace();
            }
            if (winner != null) {
                playerLabel.setText("Game over! " + winner.getName() + " wins!");
            } else {
                playerLabel.setText("Game over! It's a draw!");
            }
        }
        gameFrame.add(playerLabel, BorderLayout.NORTH);
        //Set player 1's store
        JPanel storePanel1 = new JPanel(new GridLayout(2, 1));
        player1StoreLabel = new JLabel(userProfile1.getUserName() + "'s Store: " + data.getStoreCount(1), SwingConstants.CENTER);
        player1StoreLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        storePanel1.add(player1StoreLabel);
        gameFrame.add(storePanel1, BorderLayout.EAST);
        //Set player 2's store
        JPanel storePanel2 = new JPanel(new GridLayout(2, 1));
        player2StoreLabel = new JLabel(userProfile2.getUserName() + "'s Store: " + data.getStoreCount(2), SwingConstants.CENTER);
        player2StoreLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        storePanel2.add(player2StoreLabel);
        gameFrame.add(storePanel2, BorderLayout.WEST);
        //Setup pits
        pitPanel = new JPanel(new GridLayout(2, 6));
        for (int col = 0; col < 6; col++) {
            final int pitIndex = 12 - col;
            JButton pitButton = createPitButton(pitIndex, mancalaGame);
            pitPanel.add(pitButton);
        }
        for (int col = 0; col < 6; col++) {
            final int pitIndex = col + 1;
            JButton pitButton = createPitButton(pitIndex, mancalaGame);
            pitPanel.add(pitButton);
        }
        gameFrame.add(pitPanel, BorderLayout.CENTER);
        //Setup stats
        JPanel statsPanel = createStatsPanel();
        gameFrame.add(statsPanel, BorderLayout.SOUTH);
        //Pack, center, and make all the panel's visible
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
    }

    //Handles the event part of the pit buttons
    private JButton createPitButton(int pitIndex, MancalaGame mancalaGame) {
        //Create the buttons for the pits
        JButton pitButton = new JButton("Pit " + pitIndex + ": " + rules.getNumStones(pitIndex));
        pitButton.addActionListener(new ActionListener() {
            //If button is clicked cause the following action
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int playerNum = mancalaGame.getCurrentPlayer() == player1 ? 1 : 2;
                    int movingStones = rules.getNumStones(pitIndex);
                    int storeStones = rules.moveStones(pitIndex, playerNum);
                    data.addToStore(playerNum, storeStones);
                    updateUI();
                    //Check if game is over
                    if (mancalaGame.isGameOver()) {
                        updateEndGame();
                    }
                    //Check if the player get's another turn
                    if (mancalaGame.extraTurn(pitIndex, movingStones) && rules instanceof KalahRules) {
                        playerLabel.setText(mancalaGame.getCurrentPlayer().getName() + " gets an Extra Turn!");
                    } else {
                        mancalaGame.setCurrentPlayer(mancalaGame.getCurrentPlayer() == player1 ? player2 : player1);
                        if (!mancalaGame.isGameOver()) {
                            playerLabel.setText(mancalaGame.getCurrentPlayer().getName() + "'s Turn");
                        }
                    }
                //Handles all invalid moves
                } catch (InvalidMoveException ex) {
                    JOptionPane.showMessageDialog(MancalaUI.this, "Invalid Move! Please try again.");
                }
            }
        });
        return pitButton;
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(2, 2));
        //Player 1 stats
        player1StatsLabel = new JLabel(userProfile1.getUserName() + " Stats:", SwingConstants.CENTER);
        player1KalahGamesPlayedLabel = new JLabel("Kalah Games Played: " + userProfile1.getKalahGamesPlayed(), SwingConstants.CENTER);
        player1KalahGamesWonLabel = new JLabel("Kalah Games Won: " + userProfile1.getKalahGamesWon(), SwingConstants.CENTER);
        player1AyoGamesPlayedLabel = new JLabel("Ayo Games Played: " + userProfile1.getAyoGamesPlayed(), SwingConstants.CENTER);
        player1AyoGamesWonLabel = new JLabel("Ayo Games Won: " + userProfile1.getAyoGamesWon(), SwingConstants.CENTER);
        statsPanel.add(player1StatsLabel);
        statsPanel.add(player1KalahGamesPlayedLabel);
        statsPanel.add(player1KalahGamesWonLabel);
        statsPanel.add(player1AyoGamesPlayedLabel);
        statsPanel.add(player1AyoGamesWonLabel);
        //Player 2 stats
        player2StatsLabel = new JLabel(userProfile2.getUserName() + " Stats:", SwingConstants.CENTER);
        player2KalahGamesPlayedLabel = new JLabel("Kalah Games Played: " + userProfile2.getKalahGamesPlayed(), SwingConstants.CENTER);
        player2KalahGamesWonLabel = new JLabel("Kalah Games Won: " + userProfile2.getKalahGamesWon(), SwingConstants.CENTER);
        player2AyoGamesPlayedLabel = new JLabel("Ayo Games Played: " + userProfile2.getAyoGamesPlayed(), SwingConstants.CENTER);
        player2AyoGamesWonLabel = new JLabel("Ayo Games Won: " + userProfile2.getAyoGamesWon(), SwingConstants.CENTER);
        statsPanel.add(player2StatsLabel);
        statsPanel.add(player2KalahGamesPlayedLabel);
        statsPanel.add(player2KalahGamesWonLabel);
        statsPanel.add(player2AyoGamesPlayedLabel);
        statsPanel.add(player2AyoGamesWonLabel);
        return statsPanel;
    }

    private void updateEndGame() {
        Player winner = null;
        try {
            winner = mancalaGame.getWinner();
            if (rules instanceof KalahRules) {
                if (winner.equals(player1)) {
                    userProfile1.incrementKalahGamesWon();
                } else if (winner.equals(player2)) {
                    userProfile2.incrementKalahGamesWon();
                }
                userProfile1.incrementKalahGamesPlayed();
                userProfile2.incrementKalahGamesPlayed();
            } else if (rules instanceof AyoRules) {
                if (winner.equals(player1)) {
                    userProfile1.incrementAyoGamesWon();
                } else if (winner.equals(player2)) {
                    userProfile2.incrementAyoGamesWon();
                }
                userProfile1.incrementAyoGamesPlayed();
                userProfile2.incrementAyoGamesPlayed();
            }
        } catch (GameNotOverException e) {
            e.printStackTrace();
        }

        // Display the winning player in the playerLabel
        if (winner != null) {
            playerLabel.setText("Game over! " + winner.getName() + " wins!");
        } else {
            playerLabel.setText("Game over! It's a draw!");
        }
        updateStats();
    }
    //Reset all the labels to display their updated value
    private void updateStats() {
        player1StatsLabel.setText(userProfile1.getUserName() + " Stats:");
        player1KalahGamesPlayedLabel.setText("Kalah Games Played: " + userProfile1.getKalahGamesPlayed());
        player1KalahGamesWonLabel.setText("Kalah Games Won: " + userProfile1.getKalahGamesWon());
        player1AyoGamesPlayedLabel.setText("Ayo Games Played: " + userProfile1.getAyoGamesPlayed());
        player1AyoGamesWonLabel.setText("Ayo Games Won: " + userProfile1.getAyoGamesWon());
        player2StatsLabel.setText(userProfile2.getUserName() + " Stats:");
        player2KalahGamesPlayedLabel.setText("Kalah Games Played: " + userProfile2.getKalahGamesPlayed());
        player2KalahGamesWonLabel.setText("Kalah Games Won: " + userProfile2.getKalahGamesWon());
        player2AyoGamesPlayedLabel.setText("Ayo Games Played: " + userProfile2.getAyoGamesPlayed());
        player2AyoGamesWonLabel.setText("Ayo Games Won: " + userProfile2.getAyoGamesWon());
    }

    //Updates the text in the pits and stores
    private void updateUI() {
        //Update every pit button
        for (Component component : pitPanel.getComponents()) {
            JButton pitButton = (JButton) component;
            int pitIndex = Integer.parseInt(pitButton.getText().split(":")[0].trim().substring(4));
            pitButton.setText("Pit " + pitIndex + ": " + rules.getNumStones(pitIndex));
        }
        //Update the store labels
        player1StoreLabel.setText(userProfile1.getUserName() + "'s Store: " + data.getStoreCount(1));
        player2StoreLabel.setText(userProfile2.getUserName() + "'s Store: " + data.getStoreCount(2));
    }

    //Event for when we save a game
    private void saveGame() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Mancala Save Files (*.mancala)", "mancala");
        fileChooser.setFileFilter(filter);
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            //Add the extension to the end of the file in case if it doesn't get added
            if (!selectedFile.getName().toLowerCase().endsWith(".mancala")) {
                selectedFile = new File(selectedFile.getParentFile(), selectedFile.getName() + ".mancala");
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(selectedFile))) {
                oos.writeObject(mancalaGame);
                oos.writeObject(player1);
                oos.writeObject(player2);
                oos.writeObject(userProfile1);
                oos.writeObject(userProfile2);
                oos.writeObject(rules);
                oos.writeObject(data);
                JOptionPane.showMessageDialog(this, "Game saved successfully!");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving the game.");
            }
        }
    }

    //Event for when we load a game
    private void loadGame() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Mancala Save Files (*.mancala)", "mancala");
        fileChooser.setFileFilter(filter);
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(selectedFile))) {
                mancalaGame = (MancalaGame) ois.readObject();
                player1 = (Player) ois.readObject();
                player2 = (Player) ois.readObject();
                userProfile1 = (UserProfile) ois.readObject();
                userProfile2 = (UserProfile) ois.readObject();
                rules = (GameRules) ois.readObject();
                data = (MancalaDataStructure) ois.readObject();
                JOptionPane.showMessageDialog(this, "Game loaded successfully!");
                displayGameWindow();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error loading the game.");
            }
        }
    }

    private void savePlayer(int playerNum) {
        JFileChooser fileChooser = new JFileChooser();
        // Add player extension
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Player Save Files (*.player)", "player");
        fileChooser.setFileFilter(filter);
        // Check if the user chose a file
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filename = selectedFile.getName();
            try {
                String filePath = selectedFile.getAbsolutePath();
                //Add extension to file
                if (!filePath.toLowerCase().endsWith(".player")) {
                    filePath += ".player";
                }
                if (playerNum == 1) {
                    Saver.saveObject(userProfile1, filePath);
                } else {
                    Saver.saveObject(userProfile2, filePath);
                }
                JOptionPane.showMessageDialog(this, "Player saved successfully!");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving the player.");
            }
        }
    }
    
    private void loadPlayer(int playerNum) {
        JFileChooser fileChooser = new JFileChooser();
        //Add mancala extension
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Player Save Files (*.player)", "player");
        fileChooser.setFileFilter(filter);
        //Check if the user chose a file
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filename = selectedFile.getName();
            try {
                Serializable loadedPlayer = Saver.loadObject(fileChooser.getSelectedFile().getAbsolutePath());
                // Cast the loaded player to your player class
                if (playerNum == 1) {
                    userProfile1 = (UserProfile) loadedPlayer;
                    player1.setName(userProfile1.getUserName());
                } else {
                    userProfile2 = (UserProfile) loadedPlayer;
                    player2.setName(userProfile2.getUserName());
                }
                mancalaGame.setPlayers(player1, player2);
                JOptionPane.showMessageDialog(this, "Player loaded successfully!");
                // Additional logic to update the player in your application
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error loading the player.");
            }
        }
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MancalaUI mancalaUI = new MancalaUI();
            mancalaUI.setVisible(true);
        });
    }
}