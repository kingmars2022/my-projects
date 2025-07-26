import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Game {
	
	private Board board;
	private Player first,second,current;
	
	public Game() {
		first = new Player();
		current = new Player();
		second = new Player();

	}
	
	public Game(Player p1,Player p2) {//not in use
		first = p1;
		current = p1;
		second = p2;
	}
	
	// start the game, set up the player and the board
	public void Start() {
		
		Scanner keyBoard = new Scanner(System.in);
		
		System.out.println("================================");
		System.out.println("======== Play Othello  =========");
		System.out.println("================================");
		
		System.out.println("\nWelcome to Play Othello");
		
		Player p1 = new Player();
		Player p2 = new Player();
		
		while(true) {
			
			System.out.println("1. Load a Game" + "\n2. Start a New Game" + "\n3. Quit");
			System.out.print("Please enter an option number:");
			
			int playerMenuInput = keyBoard.nextInt();
			keyBoard.nextLine(); // Consume the leftover newline
			
			if(playerMenuInput > 4 || playerMenuInput < 0) {
				
				System.out.println("There is no such option.");
				continue;
			}
			
			switch(playerMenuInput) {
			
			case 1:		
				Game.load(this, AskForPlayerName(keyBoard));
				break;
			
			case 2:
				AskForPlayerName(p1,p2,keyBoard);
				p2.SetColar(Position.WHITE);
				first = p1;
				current = p1;
				second = p2;
				AskNewStartingPosition(keyBoard);//and will set up the board
				break;
			
			case 3:
				System.exit(0);
				break;
			
			default:
				System.out.println("The menu input is not 1,2, or 3.");
			}
			
			break;
			
		}
	}
	
	// Prompt the user for the names of two players and set them
	public void AskForPlayerName(Player p1, Player p2, Scanner keyBoard) {
		
		while(true) {
			
			System.out.print("\nPlease enter The first player's name:");
			String firstNameInput =  keyBoard.nextLine();
			p1.Setname(firstNameInput);
			
			System.out.print("Please enter The second player's name:");
			String secondNameInput =  keyBoard.nextLine();
			p2.Setname(secondNameInput);
			
			if(firstNameInput.equals("") || secondNameInput.equals("")) {
				
				System.out.println("The name can't be empty.");
				continue;
			}
			
			if(firstNameInput.equals(secondNameInput)) {
				
				System.out.println("The names can't be the same.");
				continue;
			}
			
			break;
		}
	}
	
	// Prompt the user to choose a starting position and set up the board accordingly
	public void AskNewStartingPosition(Scanner keyBoard) {
		
		while(true) {
			System.out.println("\n1. Four-by-Four Starting Position");
			System.out.println("2. Standard Starting Positions");
			System.out.print("Please choose a starting position:");
			
			int playerChoise =  keyBoard.nextInt();
			keyBoard.nextLine(); // Consume the leftover newline
			
			if(playerChoise!=1&&playerChoise!=2) {
				
				System.out.println("Please choose 1 or 2.");
				continue;
				
			}
			
			this.SetBoard(new Board(playerChoise));
			break;
		}
	}
	
	// Prompt the user for a file name and return it
	public String AskForPlayerName(Scanner keyBoard) {
		
		System.out.print("\nPlease enter a file name:");
		String playerEnteredSaveName = keyBoard.nextLine();
		return playerEnteredSaveName;
		
	}
	
	// Load a saved game from a file
	static public Board load(Game game, String save_file_path) {
		
		try (BufferedReader br = new BufferedReader(new FileReader(save_file_path))) {
			
            String line;
            
            if((line = br.readLine()) != null) {
            	
            	game.first.Setname(line);//first line
            	
            }else {//first line empty
            	
            	System.out.println("Save file corrupted, loading a random new board.");
            	Random rand = new Random();
            	
            	game.board = new Board(rand.nextInt(1));
            }
            
            if((line = br.readLine()) != null) {
            	
            	game.second.Setname(line);//second line
            	game.second.SetColar(Position.WHITE);
            }
            
            if((line = br.readLine()) != null) {
            	
            	game.current.Setname(line);//third line
            }
            
            if((line = br.readLine()) != null) {
            	
            	game.board = new Board(line);//last line
            }
            
        } catch (IOException e) {
        	
            e.printStackTrace();
            System.out.println("Somthing wrong with IO, in Game load(); Maybe you type the file name wrong.");
       
        }
		
		return game.board;
		
	}
	
	// Start the gameplay loop
	public void play() {
		
		Scanner keyboard = new Scanner(System.in);
		
		while(true) {
			
			board.drawboard();
			String wOrB = "Black";
			
			if(!current.equals(first)){
				wOrB = "White";
			}
			System.out.println("\nIt's "+ wOrB+"'s turn.");
			
			if(board.checkForValidMove(current)) {
				System.out.println("Player "+current.GetName()+", please choose a coordinate to make a move, "
						+ "or enter \"SAVE\" to save the game, or \"CONCEDE\" to concede.");
			}else {
				NextTurn();
				continue;
			}
			
			System.out.println("Do you want to save or concede?(enter any other things to make a move): ");
			String playerMove = keyboard.nextLine();
			
			if(playerMove.equals("SAVE")) {
				save();
				break;
			}
			
			if(playerMove.equals("CONCEDE")) {
				String winingPlayer="";
				if(current.equals(first)) {
					board.Win(second);
				}else {
					board.Win(first);
				}
				break;
			}
			
			board.takeTurn(current);
			
			if(board.GameOver(this)) {
				break;
			}
			NextTurn();
		}
		
	}
	
	// Switch to the next player's turn
	private void NextTurn() {
		
		if(current.equals(first)) {
			current = second;
		}else {
			current = first;
		}
	}
	
	// Save the current game state to a file
	private void save() {
		System.out.println("\n\nSaving....");
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("save.txt"))) {
			
            writer.write(first.GetName());
            writer.newLine();
            
            writer.write(second.GetName());
            writer.newLine();
            
            writer.write(current.GetName());
            writer.newLine();//last line
            
            writer.write(board.GetBoardString());
            writer.close();
            
            System.out.println("\nSaved!  Terminating...");
            
        } catch (IOException e) {
        	
            e.printStackTrace();
            System.out.println("Somthing wrong with IO, in Game Save();");
            
        }
	}
	
	public void SetBoard(Board _board) {
		board = _board;
	}
	
	public Player GetFirst() {
		return first;
	}
	
	public Player GetSecond() {
		return second;
	}
	
	public Player GetCurrent() {
		return current;
	}

}
