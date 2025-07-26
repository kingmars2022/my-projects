Newly added:
In class Game
   	Game() : just to initialize the game instance with blank
   	NextTurn() : just swap players in current
	AskForPlayerName(Player, Player, Scanner) : as name
	AskNewStartingPosition(Scanner) : as name, ask player to choose in two boards
	AskForPlayerName(Scanner) : as name
	
In Board
	PossibleMoves: List<int[]>, this array list will store all the possible coordinates that current player can move
	offset: int[][], this two dimension array is just for checking adjacent and diagonal squares
	Board(type:int) , this constructor will set up a starting board with two types as descriped in the Assignment
	playMove(...) this function will place the piece on the board and filp the opponents pieces in the way
	isContainsInPossible(row:int,col:int):bool, this function check if the player input is valid
	checkForValidMove(current: Player):bool, check if player can move this turn
	getAllPossibleMoves(Player) :void , as name
	oppsi(color: char):char, this returns a opposite color
	inBounds(row:int,col:int):bool, check for out of bound
	nearbySquaresContain(target:char, row: int, col:int):bool, check if the position has certain color around
	GetBoardString():String, this function write all the boardpieces in a string and used for save()
	Gameover(game: Game):bool, check if the game is over.( only if the board is full)
	Win(winningPlayer:Player):void, print win message

extra File
	saveold.txt: this file is at the final step. Just press Enter and type "G8" to finish
	save.txt: this file needs to be saved each time the game is played.
