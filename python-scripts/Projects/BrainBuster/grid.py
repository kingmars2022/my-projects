import random

class Grid:
    """
    Represents the game grid for Brain Buster, including cell content,
    visibility, guesses, and scoring.
    """

    def __init__(self, size):
        """
        Initializes grid size and game state variables.
        """
        self.size = size
        self.grid = [[None for _ in range(size)] for _ in range(size)]
        self.visible = [[False for _ in range(size)] for _ in range(size)]
        self.guesses = 0
        self.uncovers = 0
        self.min_guesses = (size * size) // 2
        self.initialize_grid()
        self.is_give_up = False

    def initialize_grid(self):
        """
        Randomly initializes pairs of numbers in the grid, each number appearing twice.
        """
        pairs = []
        for i in range(self.size * self.size // 2):
            pairs.extend([i, i])
        random.shuffle(pairs)
        for i in range(self.size):
            for j in range(self.size):
                self.grid[i][j] = pairs[i * self.size + j]

    def is_valid_cell(self, col, row):
        """
        Checks if a cell is within the valid range of the grid.
        """
        return 0 <= row < self.size and 0 <= col < self.size

    def reveal_cell(self, col, row):
        """
        Marks a cell as visible and returns its value.
        """
        if not self.is_valid_cell(col, row):
            return None
        self.visible[row][col] = True
        return self.grid[row][col]

    def hide_cell(self, col, row):
        """
        Hides the specified cell by marking it as not visible.
        """
        if self.is_valid_cell(col, row):
            self.visible[row][col] = False

    def get_cell(self, col, row):
        """
        Returns the cell's value if visible; otherwise, returns 'X'.
        """
        if not self.is_valid_cell(col, row):
            return None
        return str(self.grid[row][col]) if self.visible[row][col] else 'X'

    def is_cell_visible(self, col, row):
        """
        Checks if a cell is visible.
        """
        return self.is_valid_cell(col, row) and self.visible[row][col]

    def give_up(self):
        """
        Sets the game state to "give up".
        """
        self.is_give_up = True

    def add_guess(self):
        """
        Increments the guess count.
        """
        self.guesses += 1

    def add_uncover(self):
        """
        Increments the uncover count.
        """
        self.uncovers += 1

    def get_score(self):
        """
        Calculates the player's score based on minimum possible guesses and actual guesses/uncover actions.
        """
        if self.guesses == 0:
            return 0
        return (self.min_guesses / (self.guesses + 2 * self.uncovers)) * 100

    def is_game_complete(self):
        """
        Checks if all cells have been revealed.
        """
        return all(all(row) for row in self.visible)
