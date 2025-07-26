public class Player {
	
	private String playerName;
	private char color;
	
	public Player() {
		playerName = "DefaultPlayerName";
		color= 'B';
	}
	
	public Player(String name) {
		playerName = name;
		color= 'B';
	}
	
	public void Setname(String name) {
		playerName = name;
	}
	
	public String GetName() {
		return playerName;
	}
	
	// Check if two players are equal based on their names
	public boolean equals(Player other) {
		
		if(other.GetName()==playerName) {
			
			return true;
			
		}
		
		return false;
		
	}
	
	public char GetColor() {
		return color;
	}
	
	public void SetColar(char wOrB) {
		color = wOrB;
	}
}
