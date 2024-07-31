# Minesweeper

This project was developed for the Multimedia Technology course. It leverages **Java** to implement all core functionalities and **JavaFX** for the Graphical User Interface (GUI).

## Features

There are 2 levels of difficulty, each with specific features:
| Difficulty Level | Board Size | Number of Mines | Available time (seconds) | Super-mine |
|------------------|------------|-----------------|--------------------------|------------|
| 1                | 9 x 9      | [9 - 11]        | [120 - 180]              | No         |
| 2                | 16 x 16    | [35 - 45]       | [240 - 360]              | Yes        |


**Super-mine:** This is a special Mine category. If the player marks the square containing the Super-mine with a 
flag within the first 4 attempts, the contents of all tiles in the same row and column (31 squares in total) are revealed. 
If any of these tiles contain a Mine, that Mine is disabled, and the player can no longer click on it.


## Instructions

### Menu Bar
The player can navigate through the Menu Bar with the following options: “Application” > “Create”, “Load”, “Start”, “Exit” and “Details” > “Round”, “Solution”. 

- **“Create”:** Opens a popup window with fields for SCENARIO-ID, LEVEL, NUMBER OF MINES, SUPERMINE, and MAX TIME. This 
option creates a gameboard initialization file named “SCENARIO-ID.txt” and saves it in the  *medialab* folder.

- **“Load”:** Displays a popup window with a field to fill in (SCENARIO-ID). It checks if the file “SCENARIO-ID.txt” exists 
and verifies that its description and values are valid. If valid, it initializes a new board based on these values and saves 
the file “mines.txt” in the *medialab* folder, which includes the current positions of the mines. If the file is not found or the values 
are invalid, a popup window displays an appropriate error/exception message.

- **“Start”:** Initializes the game by creating graphical representations of the number of mines, the number of flags used, 
and the available playing time, based on the configuration loaded. The countdown timer starts from the total available seconds 
set during the load. Additionally, “Start” adjusts the game window dimensions according to the board size and creates the graphics for the closed squares.

**Note:** The player must select a game from “Load” first and then select “Start” to initialize the game correctly, even if the same game is to be selected.

- **“Exit”:** Terminates the game and closes the game window.

- **“Round”:** Displays a popup window showing the characteristics of the last 5 completed games, retrieved from the queue of the PopupWindow class.

- **“Solution”:** Ends the current game, reveals all Mine locations, and registers the computer as the winner.

### Clicking
- **Left Click:** Reveals the contents of the clicked square.
- **Right Click:** Adds or removes a flag on the clicked square.

If the game ends due to a player winning or losing, mouse clicks are *disabled*.

### How to win 
The player wins the game if they reveal all tiles except those containing Mines before time runs out.

## Running the Game
1. Download the `Minesweeper` folder
2. Open folder at **IntelliJ IDEA** (or other IDE)
3. Execute `Main.java`

