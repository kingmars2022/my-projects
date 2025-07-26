# Othello Game (ASCII Version)

This project is a command-line Java implementation of a two-player Othello (Reversi) game, built with a modular and object-oriented architecture. It supports saving/loading games, non-standard board positions, and includes unplayable squares for variant gameplay.

## Features

- ASCII-based board rendering with coordinate labels
- Multiple game start options:
  - Standard Othello layout
  - 4x4 centered start
- Unplayable corner positions to add strategic variation
- Turn-based input and in-game messaging
- Real-time piece flipping logic and move validation
- Full save/load system (text format):
  - Saves player names and board state to file
  - Loads saved game from file and resumes session
- Endgame logic and winner announcement

## Gameplay Options

Upon startup, users are presented with:

1. **Load a Game** – Resume from a saved `.txt` file  
2. **Start a New Game** – Enter player names and choose a starting position  
3. **Quit** – Exit the program  

During gameplay, players can:

- Enter coordinates to place a piece  
- Pass turn if no valid moves exist  
- Save the game to a file  
- Concede (forfeit the match)

## Technical Architecture

- **Classes**:
  - `Game` – Controls game state and turn logic
  - `Board` – Manages grid structure and piece placement
  - `Player` – Represents a player’s name and piece color
  - `Position` *(abstract)* – Base class for board squares
    - `PlayablePosition` – Square that allows legal moves
    - `UnplayablePosition` – Inactive square (e.g., corners)
- **Polymorphism**:
  - `Position` declares `canPlay()` as a pure virtual method
  - Dynamic dispatch used to evaluate position validity
- **UML-Aligned Design**:
  - Object structure aligns with a predefined UML diagram
  - Additional methods added with clear modular roles (e.g., `makeMove()`, `hasValidMoves()`)

## File Format (Save/Load)

Saved game files use a simple text format:
Player1Name
Player2Name
CurrentPlayerName
XXXXXXXXXOOOX... (board as a flat character string)


## How to Run

1. Compile all Java files:
   ```bash
   javac *.java

2.Start the game:
java Game

Notes
* Board is printed with row and column labels (A-H, 1-8)
* All input is handled via keyboard (no mouse/GUI required)
* Program assumes valid input for file names and coordinates

Highlights
* Clean object-oriented design
* Command-line usability with ASCII rendering
* Game state persistence using text files
* Inheritance and polymorphism via Position hierarchy
