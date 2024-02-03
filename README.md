# Project Title
Mancala:
A two-player strategy game with the concept of moving stones from different pits to eventually
move stones into your own store to win the game.

## Description
Mancala is a board game with twelve pits, two stores and two players. There are six pits on each side of the game board
and two stores at the right and left end of the board. Each player owns the six pits on their side in this case player 1
owns pits 1-6 and player 2 owns pits 7-12. Each player also owns a store, this store is the store to the right of the player,
A player's turn consists of picking one of the pits on their side, removing all the stones from this pit, distributing all
stones from this pit counter clockwise skipping the opponent's store. If the player places their last stone in their store
they take another turn or if the player places their last stone in an empty pit on their side then they get to capture all
the stones in the pit directly opposite of that pit and place all these stones into their store. Once a player has no stones
on their side of the board the game is finished. The player who still has pieces on their side of the board captures all the
stones on their side and places them into their store. The player with the most stones in their store wins. The version described
is called Kalah, another version is presented in this project called Ayo. Ayo is similar to Kalah except during movement if a
the last stone is placed in a non-empty pit all stones are picked up from that pit and distributed as normal. Additional rules
are that a stone placed in an empty pit stays there during the capture sequence, stone can't be placed in the pit which movement
was chosen from, and there is no free turn for ending in your own store.

## Getting Started

### Dependencies

* Gradle
* Junit
* Swing

### Executing program

* Ensure your in GP4
* Input the command gradle build within a scioer terminal
* Go into a terminal outside of scioer
* In the local terminal get to the directory of GP4 for me I use the command cd Desktop/CIS2430/coursework/GP4
* Input this command to start the game java -jar build/libs/MancalaUI.jar

## Limitations

All Done!

## Author Information

Name: Alex Daniel
UofG Email: adanie02@uoguelph.ca

## Development History

* 0.4
    * Saving and loading implemented
    * Fixed out of bounds error with Ayo
    * Added new game option
    * Cleaned up PMD errors

* 0.3
    * Implemented a clean version of the GUI
    * Saving and loading works
    * Ayo in the GUI flows
    * End game check fixed
    * Capturing from store doesn't occur anymore
    * Taking stones from stores in Ayo during distribution is fixed


* 0.2
    * Added the rough sketch of the GUI
    * Pits and stores have visible incrementation when buttons are pressed
    * Ayo rules is finished
    * Added userprofile and saver class

* 0.1
    * Completed the rough sketch of the game
    * Kalah rules is working

## Acknowledgments

Helped me get Junit working with visual studio https://www.youtube.com/watch?v=Af6Ka0Bmflo
Taught me how to make an exception class https://www.baeldung.com/java-new-custom-exception
Helped me understand how to use swing https://www.youtube.com/watch?v=5o3fMLPY7qY
