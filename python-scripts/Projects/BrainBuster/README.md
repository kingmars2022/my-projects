# BrainBuster

A console-based memory matching game written in Python, where players uncover pairs of hidden numbers on a grid as efficiently as possible.

## Overview

When launched, the game displays a square grid with all cells hidden behind 'X' symbols. Players select pairs of coordinates to reveal and try to match numbers. The fewer guesses it takes to complete the grid, the higher the score. The grid is randomly generated for each new game session, ensuring replayability.

## Features

- Interactive menu-based gameplay in the terminal
- Grid sizes: 2x2, 4x4, or 6x6 (passed via command-line argument)
- Matching game with randomized hidden number pairs
- Score calculated based on efficiency:  
  `score = (minimum_possible_guesses / actual_guesses) * 100`
- Option to reveal any cell or the entire grid
- Automatically ends with a win message and score summary
- Input validation for menu choices and cell coordinates

## Technologies Used

- Language: Python 3.x
- Modules: `os`, `time`, `random`, `sys`, `string`

## File Structure

- `game.py` – User interface, game loop, prompts, input handling, scoring
- `grid.py` – Grid class, logic for storing and revealing cells, win condition tracking

## How to Run

```bash
python3 game.py 4
(Replace 4 with 2, 4, or 6 to choose the grid size.)

Example
=== Brain Buster ===
    A   B   C   D
  +---+---+---+---+
0 | X | X | X | X |
1 | X | X | X | X |
2 | X | X | X | X |
3 | X | X | X | X |

1. Make a guess
2. Reveal a cell
3. Reveal entire grid
4. Start new game
5. Quit

Game Logic Notes
* A 4x4 grid hides 8 matching pairs (0–7), randomly placed
* Guessing correctly keeps cells visible; wrong guesses are hidden again after 2 seconds
* Revealing a single cell (option 2) counts as two guesses (to discourage overuse)
* The goal is to match all pairs with as few guesses as possible
Error Handling
* Invalid grid size on launch will halt the program
* Invalid menu or cell inputs will prompt retry
* Duplicate cell selections in one guess are not allowed
Dependencies
* Python 3.x (no external libraries)
* Runs on Linux/macOS terminal (uses os.system("clear") to refresh display)
