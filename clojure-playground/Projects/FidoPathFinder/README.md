# FidoPathFinder

A command-line pathfinding game written in Clojure, featuring a dog named Fido navigating through a maze to find hidden food. This project demonstrates functional programming principles using recursion, immutable data, and core Clojure constructs.

## Overview

- The user selects a map file via a menu
- The map is a plain text file containing open paths (`-`), obstacles (`#`), and a food target (`@`)
- Fido starts at the top-left corner of the grid and recursively searches for a valid path to the food
- Successful paths are marked with `+`, while dead-end paths are marked with `!`
- If no path exists or the map is invalid, the program notifies the user and returns to the main menu

## Features

- Lists available map files (`.txt` only)
- Parses maps into nested vector structures
- Implements recursive depth-first search to explore all possible paths
- Provides terminal menu navigation (clear screen, return, exit)
- Dynamically adapts to maps of any rectangular size

## Technologies Used

- Language: Clojure (compatible with 1.1x+)
- Functional programming: pure functions, no side effects
- Core functions: `slurp`, `file-seq`, `map`, `filter`, `reduce`, `assoc`, `re-matches`
- Uses `let` bindings for clarity and local scoping
- All data is immutable
- Recursive logic used to trace and mark pathfinding outcomes

## File Structure

- `fido.clj` – Main entry point: menu, user input, and control flow
- `food.clj` – Map reading, validation, and pathfinding logic
- Map files (e.g., `map1.txt`) are stored in the project root

## How to Run

```bash
# Set classpath so that food.clj can be referenced
export CLASSPATH=./

# Launch the program
clojure fido.clj

Notes
* No external libraries required — only standard Clojure core
* No build tools needed (e.g., Leiningen); run directly via command line
* Invalid map formats (e.g., uneven rows) will trigger error handling
* If no path to the food is found, the program will still display the traversal history

Educational Value
This project showcases your ability to design recursive algorithms using functional techniques. It emphasizes clean logic, data immutability, and structure — making it ideal for portfolios demonstrating Clojure or functional programming proficiency.




