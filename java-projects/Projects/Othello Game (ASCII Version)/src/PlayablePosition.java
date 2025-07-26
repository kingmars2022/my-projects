public class PlayablePosition extends Position {

	// Constructor initializing the position as empty
	public PlayablePosition() {
		
		super.SetPiece(EMPTY);
	}
	
	@Override
	public boolean canPlay(){
		
		if(super.GetPiece() == EMPTY) {
			return true;//playable
		}
		return false;//otherwise the position is not playable
		
	}
	//public void Play()

}
