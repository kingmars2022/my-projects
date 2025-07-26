public abstract class Position {
	
	private char piece;
	static final char EMPTY = ' ';
	static final char BLACK = 'B';
	static final char WHITE = 'W';

	// Abstract method to check if the position can be played
	public abstract boolean canPlay();
	
	public void SetPiece(char ch) {
		piece = ch;
	}
	
	public char GetPiece() {
		return piece;
	}
}
