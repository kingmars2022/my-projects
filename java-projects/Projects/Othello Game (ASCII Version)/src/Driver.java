public class Driver {

	public static void main(String[] args) {
		
		// Create a new game instance
		Game game = new Game();
		
		// Initialize the game settings and start menu
		game.Start();
		
		// Start the gameplay loop
		game.play();
		
	}
}
