import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board {
	
	private Position[][] boardPieces = new Position[8][8];
	private List<int[]> possibleMoves = new ArrayList<int[]>();
	
	private int[][] offsets = {
			
			{0, 1}, {1, 0}, {0, -1}, {-1, 0},   // Adjacent Squares
	        {1, 1}, {1, -1}, {-1, -1}, {-1, 1}  // Diagonal Squares
			
	};
			
	// Initialize the board with a given type
	public Board(int type) {
		
		if(type==1) {// Non-standard starting board, i.e., Four-by-Four Starting Position
			
			for(int i =0; i<boardPieces.length;i++) {
				for(int j =0; j<boardPieces[i].length;j++)  {
					if(i==0||i==7) {
						if(j==0||j==7) {
							boardPieces[i][j] = new UnplayablePosition();
							continue;
						}
					}
					boardPieces[i][j] = new PlayablePosition();
					
					if(i==2||i==3) {
						
						if(j==2||j==3) {
							boardPieces[i][j].SetPiece(Position.WHITE);
							continue;
						}
						
						if(j==4||j==5) {
							boardPieces[i][j].SetPiece(Position.BLACK);
							continue;
						}
					}
					
					if(i==4||i==5) {
						
						if(j==2||j==3) {
							boardPieces[i][j].SetPiece(Position.BLACK);
							continue;
						}
						
						if(j==4||j==5) {
							boardPieces[i][j].SetPiece(Position.WHITE);
							continue;
						}
					}
				}
			}
		}else {// Standard board, i.e., Standard Starting Positions
			for(int i =0; i<boardPieces.length;i++) {
				for(int j =0; j<boardPieces[i].length;j++)  {
					if(i==0||i==7) {
						if(j==0||j==7) {
							boardPieces[i][j] = new UnplayablePosition();
							continue;
						}
					}
					boardPieces[i][j] = new PlayablePosition();
					if(i==3) {
						if(j==3) {
							boardPieces[i][j].SetPiece(Position.WHITE);
							continue;
						}
						if(j==4) {
							boardPieces[i][j].SetPiece(Position.BLACK);
							continue;
						}
					}
					if(i==4) {
						if(j==3) {
							boardPieces[i][j].SetPiece(Position.BLACK);
							continue;
						}
						if(j==4) {
							boardPieces[i][j].SetPiece(Position.WHITE);
							continue;
						}
					}
				}
			}
		}

	}
	
	// Initialize the board from a saved file
	public Board(String save_file) {
		
		char[] positioChars = save_file.toCharArray();
		
		if(positioChars.length!=boardPieces.length*boardPieces[0].length) {
			
			System.out.println("Load board failed, because the length of loading string is not the same lenth of board position");
			return;
		}
		
		for(int i=0;i<positioChars.length;i++) {
			
			if(positioChars[i]==UnplayablePosition.UNPLAYABLE) {
				
				boardPieces[i/8][i%8] = new UnplayablePosition();
				continue;
			}
			
			boardPieces[i/8][i%8] = new PlayablePosition();
			boardPieces[i/8][i%8].SetPiece(positioChars[i]);
			
		}

	}
	
	// Draw the board on the console
	public void drawboard() {
		
		char boardLetter = 'A';
		System.out.print(' ');
		
		for(int i=0;i<8;i++) {
			System.out.print((char)(boardLetter+i));
		}

		for(int i=0;i<boardPieces.length;i++) {
			System.out.println();
			System.out.print(i+1);
			
			for(int j=0;j<boardPieces[i].length;j++) {
				
				if(boardPieces[i]==null) {//print null if null
					System.out.print("null");
					continue;
				}
				
				System.out.print(boardPieces[i][j].GetPiece());
			}


		}
	}
	
	// Allow the current player to take a turn
	public void takeTurn(Player current) {
		
		Scanner keyboard = new Scanner(System.in);
		
		while(true) {
			System.out.println("Enter your choose of position(e.g.: B2): ");
			String playerMove = keyboard.nextLine();
			
			if(playerMove.length()>2||playerMove.length()==0) {
				System.out.println("Can't read the input(wrong length), please try again.");
				continue;
			}
			
			if(playerMove.charAt(0)<65||playerMove.charAt(0)>90) {
				System.out.println("Can't read the input(wrong letter), please try again. Maybe Capitalize your letter.");
				continue;
			}
			
			if(playerMove.charAt(1)<48||playerMove.charAt(1)>57) {
				System.out.println("Can't read the input(wrong number), please try again.");
				continue;
			}
			
			int col=0;
			int row=0;
			
			if(playerMove.charAt(0)>64&&playerMove.charAt(0)<91) {
				col = playerMove.charAt(0)-65;
			}else {
				System.out.println("Wrong letter!");
				continue;
			}
			
			row = playerMove.charAt(1)-49;

			if(isContainsInPossible(row, col)) {
				playMove(row,col,current.GetColor());
			}else {
				System.out.println("Seems you entered the wrong choose.");
				continue;
			}
			
			break;
		}
	}
	
	// Execute the move and flip opponent's pieces as needed
	public void playMove(int row, int col, char currColor){
		
        if (isContainsInPossible(row, col)){
        	
            boardPieces[row][col].SetPiece(currColor);
            
            // Identify in what squares pieces need to be flipped
            for(int[] offset: offsets){
            	
                int newRow = row + offset[0];
                int newCol = col + offset[1];
                int count = 1;
                
                while (inBounds(newRow, newCol) && boardPieces[newRow][newCol].GetPiece() == oppsi(currColor)){
                    newRow += offset[0];
                    newCol += offset[1];
                    count++;
                }
                
                if (inBounds(newRow, newCol) && boardPieces[newRow][newCol].GetPiece() == currColor){

                		// Flippable line identified, now backtrack and flip all opponent pieces
                        for (int i = 1; i < count; i++){
                        	boardPieces[newRow - offset[0]*i][newCol - offset[1]*i].SetPiece(oppsi(boardPieces[newRow - offset[0]*i][newCol - offset[1]*i].GetPiece()));;
                        }
                }
            }
            //System.out.println(this);
        }
    }
	
	// Check if a position is in the list of possible moves
	private boolean isContainsInPossible(int X, int Y) {
		
		for(int[] pair : possibleMoves) {
			if(pair[0]==X&&pair[1]==Y) {
				return true;
			}
		}
		return false;
	}
	
	// Check if the current player has any valid moves
	public boolean checkForValidMove(Player current) {
		
		getAllPossibleMoves(current);
		if(possibleMoves.size()==0) {
			System.out.println(current.GetName()+" has no where to go, forfeit the turn");
			return false;
		}else {
			System.out.println(current.GetName()+" Can go:");
			for(int[] cord: possibleMoves){
				System.out.print("["+(char)(cord[1]+65)+","+(cord[0]+1)+"] ");
			}
			System.out.println();
		}
		return true;
	}

	// Get all possible moves for the current player
	private void getAllPossibleMoves(Player current) {
		possibleMoves = new ArrayList<>();
		for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (boardPieces[i][j].GetPiece()==Position.EMPTY && nearbySquaresContain(oppsi(current.GetColor()), i, j)){
                    
                	// Possible square identified, now iterate through rows, cols, and diagonals to check if its legal
                    for (int[] offset: offsets){
                        int newRow = i + offset[0];
                        int newCol = j + offset[1];
                        
                        // Continue in the direction of the offset until a tile of the same color is reached.
                        while (inBounds(newRow, newCol) && boardPieces[newRow][newCol].GetPiece() == oppsi(current.GetColor())){
                            newRow += offset[0];
                            newCol += offset[1];
                        }
                        if (inBounds(newRow, newCol) && boardPieces[newRow][newCol].GetPiece() == current.GetColor()){
                            if ((newRow == i && Math.abs(newCol - j) > 1) || // In the same row?
                                (newCol == j && Math.abs(newRow - i) > 1) || // In the same col?
                                (Math.abs(newCol - j) > 1 && Math.abs(newRow - i) > 1)){ //Diagonal?
                            	possibleMoves.add(new int[]{i, j});
                                    break;  // Now consider the next square.
                            } 
                        }
                    }
                }
            }
        }
	}
	// Get the opposite color
	private char oppsi(char color) {
		if(color==Position.BLACK) {
			return Position.WHITE;
		}
		return Position.BLACK;
	}
	
	// Check if the given row and column are within the board boundaries
	private boolean inBounds(int row, int col){
        return (row >= 0 && row <= 7) && (col >= 0 && col <= 7);
    }
	
    //Check if any of the directly adjacent or diagonal squares of a specified square contains a specified color
	private boolean nearbySquaresContain(char target, int row, int col){
        for (int[] offset: offsets){
            int newRow = row + offset[0];
            int newCol = col + offset[1];
            if (inBounds(newRow, newCol) && boardPieces[newRow][newCol].GetPiece() == target){
                return true;
            }
        }
        return false;
    }
	
	// Get the current board state as a string
	public String GetBoardString() {
		
		String boardCharcters="";
		
		for(int i=0; i <boardPieces.length;i++) {
			for(int j=0;j<boardPieces[i].length;j++) {
				boardCharcters+=boardPieces[i][j].GetPiece();
			}
			
		}
		return boardCharcters;
	}
	
	//Check if the game is over
	public boolean GameOver(Game game) {
		
		int counter =0;
		
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				if(boardPieces[i][j].GetPiece()==Position.EMPTY) {
					return false;
				}
				if(boardPieces[i][j].GetPiece()==Position.BLACK) {
					counter++;
				}
				if(boardPieces[i][j].GetPiece()==Position.WHITE) {
					counter--;
				}
			}
		}
		if(counter>0) {
			if(game.GetCurrent().GetColor()==Position.BLACK) {
				
				System.out.println("Black Wins by more pieces of: "+counter);
				Win(game.GetCurrent());
				
			}else {
				
				System.out.println("White Wins by more pieces of: "+-counter);
				Win(game.GetSecond());
				
			}
			
		}
		
		return true;
	}
	
	// Display the winning message
	public void Win(Player winningPlayer) {
		
		System.out.println("Congrats "+winningPlayer.GetName()+"!");
		System.out.println("====================================");
		System.out.println("====================================");
		System.out.println("======        YOU WIN         ======");
		System.out.println("====================================");
		System.out.println("====================================");
		System.out.println("Thanks for playing!! Terminating...");
		
	}

}
