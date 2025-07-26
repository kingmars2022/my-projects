import os
import sys
import time
from grid import Grid

def clear_screen():
    """
    Clears the console screen.
    """
    sys.stdout.flush()
    os.system('clear')

def print_grid(grid):
    """
    Displays the game grid, including the title and column/row labels.
    Hidden cells are represented by 'X'.
    """
    # Print title
    print("------------------------")
    print("|     Brain Buster     |")
    print("------------------------\n")

    # Print column headers
    print("     ", end="")
    for i in range(grid.size):
        print(f"[{chr(65 + i)}]  ", end="")  # Use ASCII characters for column labels
    print("\n")

    # Print rows and cell contents
    for i in range(grid.size):
        print(f"[{i}]  ", end="")
        for j in range(grid.size):
            print(f" {grid.get_cell(j, i)}   ", end="")
        print()
    print()

    # Check if the game state is "give up"
    if not grid.is_give_up:
        check_win()

def print_menu():
    """
    Prints the game menu for the player to choose actions.
    """
    print("1. Let me select two elements")
    print("2. Uncover one element for me")
    print("3. I give up - reveal the grid")
    print("4. New game")
    print("5. Exit\n")
    print("Select: ", end="")

def get_cell_input(grid_size):
    """
    Gets and validates cell coordinates input from the player.
    Input format should be letter+number (e.g., 'A0').
    Returns column and row as integer values.
    """
    while True:
        cell = input("Enter cell coordinates (e.g., A0): ").strip().upper()
        if len(cell) < 2:
            print("Invalid input format. Please use letter+number (e.g., A0)")
            continue

        # Convert column letter to an index
        col = ord(cell[0]) - ord('A')
        if not (0 <= col < grid_size):
            print("Input error: column entry is out of range for this grid. Please try again.\n")
            continue

        # Try to convert the row part to an integer
        try:
            row = int(cell[1:])
        except ValueError:
            print("Input error: row entry is out of range for this grid. Please try again.\n")
            continue

        # Validate that row index is within range
        if not (0 <= row < grid_size):
            print("Input error: row entry is out of range for this grid. Please try again.\n")
            continue

        return col, row

def handle_pair_guess(grid):
    """
    Handles the player's choice to select two cells and check if they match.
    If they don't match, hides the cells again after a brief pause.
    """
    # Get the first cell
    col1, row1 = get_cell_input(grid.size)

    # Get the second cell and ensure it's different from the first cell
    while True:
        col2, row2 = get_cell_input(grid.size)
        if (col1, row1) != (col2, row2):
            break
        print("Input error: select a different cell")

    # Reveal both cells and check if they match
    val1 = grid.reveal_cell(col1, row1)
    val2 = grid.reveal_cell(col2, row2)
    grid.add_guess()  # Increment guess count

    clear_screen()
    print_grid(grid)
    print_menu()

    # If they don't match, hide them again after 2 seconds
    if val1 != val2:
        time.sleep(2)
        grid.hide_cell(col1, row1)
        grid.hide_cell(col2, row2)
        clear_screen()
        print_grid(grid)
        print_menu()

def handle_reveal_cell(grid):
    """
    Handles the player's choice to reveal a single cell.
    """
    col, row = get_cell_input(grid.size)
    grid.reveal_cell(col, row)
    grid.add_uncover()  # Increment uncover count

def check_win():
    """
    Checks if the game is complete. If so, displays a win message and score.
    """
    if grid.is_game_complete():
        if grid.guesses > 0:
            print(f"Oh Happy Day. You've won!! Your score is: {grid.get_score():.1f}\n")
        else:
            print("You cheated - Loser! Your score is 0!\n")

def main():
    """
    Main function that runs the game loop, manages grid display, and processes menu choices.
    """
    if len(sys.argv) != 2 or sys.argv[1] not in ['2', '4', '6']:
        print("Usage: python3 game.py <grid_size>")
        print("Grid size must be 2, 4, or 6")
        sys.exit(1)

    grid_size = int(sys.argv[1])
    global grid  # Declare global variable grid to use in other functions
    grid = Grid(grid_size)

    while True:
        clear_screen()
        print_grid(grid)
        print_menu()

        # Get menu choice
        try:
            choice = input()
            if not choice.isdigit() or int(choice) not in range(1, 6):
                raise ValueError
            choice = int(choice)
        except ValueError:
            print("\nInvalid choice. Please enter a number between 1 and 5")
            time.sleep(1)
            continue

        # Perform action based on menu choice
        if choice == 1:
            handle_pair_guess(grid)
        elif choice == 2:
            handle_reveal_cell(grid)
        elif choice == 3:
            # Reveal all cells in the grid
            for i in range(grid_size):
                for j in range(grid_size):
                    grid.reveal_cell(j, i)
            grid.give_up()  # Mark the game as given up
            clear_screen()
            print_grid(grid)
        elif choice == 4:
            grid = Grid(grid_size)  # Reinitialize the game grid
        elif choice == 5:
            print("\nThanks for playing!")
            sys.exit(0)

if __name__ == "__main__":
    main()
