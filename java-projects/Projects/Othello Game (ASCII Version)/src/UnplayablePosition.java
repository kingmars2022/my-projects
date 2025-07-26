public class UnplayablePosition extends Position {
	
	static final char UNPLAYABLE ='*';
	
	// Constructor initializing the position as unplayable
	public UnplayablePosition() {
		super.SetPiece(UNPLAYABLE);
	}
	
	@Override
	public boolean canPlay() {
		
		// Unplayable positions can never be played
		return false;
	}

}
