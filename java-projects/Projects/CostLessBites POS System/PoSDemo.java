import java.util.Scanner;
public class PoSDemo {
	
	private static PoS[] posArray;
	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		displayWelcome();
		initializePoSInstances();

		boolean running = true;
		
		while (running) {
			
			displayMenu();
			System.out.print("\nPlease enter your choice and press <Enter>:");
			int choice = sc.nextInt();
			switch (choice) {
			case 0:
				running = false;
				System.out.print("Thank you for using Concordia CostLessBites Catering Sales Counter Application!");
				break;
				
			case 1:
				showAllPoSContents();
				break;
			case 2:
				displayOnePoSContent();
				break;
			case 3:
				listPoSsWithSameSalesAmount();
				break;
			case 4:
				listPoSsWithSameSalesCategories();
				break;
			case 5:
				listPoSEquals();
				break;
			case 6:
				addPrepaidCardToPoS();
				break;
			case 7:
				removePrepaidCard();
				break;
			case 8:
				updateExpiryDate();
				break;
			case 9:
				addSalesToPoS();
				break;
				default: 
					System.out.println("Sorry that is not a valid choice. Try again.");
				
			}
			
		}	
		// Close the scanner object
		sc.close();
		
	}
	
	// Display a welcome message
	private static void displayWelcome() {
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("| Welcome to Concordia CostLessBites Catering Sales Counter Application           |");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}
	
	// Display a menu
	private static void displayMenu(){
		System.out.println("| What would you like to do?                                                      |");
		System.out.println("| 1\t>> See the content of all PoSs                                            |");
		System.out.println("| 2\t>> See the content of one PoS                                             |");
		System.out.println("| 3\t>> List PoSs with same $ amount of sales                                  |");
		System.out.println("| 4\t>> List PoS with same number of Sales categories                          |");
		System.out.println("| 5\t>> List PoSs with same $ amount of Sales and same number of Prepaid cards |");
		System.out.println("| 6\t>> Add a PrePaiCard to an existing PoS                                    |");
		System.out.println("| 7\t>> Remove an existing Prepaid card from a PoS                             |");
		System.out.println("| 8\t>> Update the expiry date of an existing Prepaid card                     |");
		System.out.println("| 9\t>> Add sales to a PoS                                                     |");
		System.out.println("| 0\t>> To quit                                                                |");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}
	
	// Creates at least 5 PoS
	private static void initializePoSInstances() {
		// Initializing sales data
		Sales sales1 = new Sales(2, 1, 0, 4, 1); 
	    Sales sales2 = new Sales(0, 1, 5, 2, 0); 
	    Sales sales3 = new Sales(3, 2, 4, 1, 2);
	    
	    // Initializing PrePaiCard data
	    PrePaiCard[] prePaiCards1 = {new PrePaiCard("Vegetarian", "40825164", 25, 12),
	    		new PrePaiCard("Carnivore", "21703195", 3, 12)};
	    
	    PrePaiCard[] prePaiCards2 = {new PrePaiCard("Vigan", "40825164", 7, 12),
	    		new PrePaiCard("Vegetarian", "21596387", 24, 8)};
	    
	    PrePaiCard[] prePaiCards3 = {new PrePaiCard("Pescatarian", "95432806", 1, 6),
	    		new PrePaiCard("Halal", "42087913", 18, 12),new PrePaiCard("Kosher", "40735421", 5, 4)};
	    
	    // Creating PoS instances with the above data
	    posArray = new PoS[5];
	    posArray[0] = new PoS(sales1, prePaiCards1);
	    posArray[1] = new PoS(sales1, prePaiCards2); 
	    posArray[2] = new PoS(sales2, prePaiCards3);
	    posArray[3] = new PoS(sales3, null);
	    posArray[4] = new PoS(sales3, null);
	    
	}
	
	
	// 1. See the content of all PoSs
	private static void showAllPoSContents() {
		System.out.println("Content of each PoS:");
		System.out.println("---------------------");
		for (int i = 0; i < posArray.length; i++) {
			PoS pos = posArray[i];
	        System.out.println("PoS #" + i + ":");
	        System.out.println(pos.getMeals().toString());
	        
	        if (pos.getPrePaiCards() != null && pos.getPrePaiCards().length > 0) {
	        	for (PrePaiCard card : pos.getPrePaiCards()) {
	        		System.out.println(card.toString()); 
	        	}
	        	System.out.println();
	        }else {
	        	System.out.println("No PrePaiCards");
	        	System.out.println();
	        }
		}
	}
	
	// 2. See the content of one PoS  
	private static void displayOnePoSContent() {
		// Start with an invalid index to ensure the loop is entered
		int index = -1;
		while (index < 0 || index >= posArray.length) {
			System.out.print("Which PoS you want to see the content of? (Enter number 0 to 4): ");
	        index = sc.nextInt();
	        
	        // If the entered index is invalid, print an error message and prompt for retry
	        if (index < 0 || index >= posArray.length) {
	        	System.out.println("Sorry but there is no PoS number " + index);
	            System.out.print("--> Try again: (Enter number 0 to 4): ");
	            index = sc.nextInt();
	        }
		}
		// Once a valid index is obtained, display the content of the PoS
		PoS pos = posArray[index];
	    System.out.println(pos.getMeals());
	    
	    // Check and print PrePaiCard information
	    if (pos.getPrePaiCards() == null || pos.getPrePaiCards().length == 0) {
	    	System.out.println("No PrePaiCards");
	    }else {
	    	for(PrePaiCard card : pos.getPrePaiCards()) {
	    		System.out.println(card);
	    	}
	    }

	    System.out.println();
	}
	
	// 3. List PoSs with same $ amount of sales 
	private static void listPoSsWithSameSalesAmount() {
		System.out.println("List of PoSs with same total $ Sales:");
		System.out.println();
		for (int i = 0; i < posArray.length; i++) {
			for(int j = i + 1; j < posArray.length; j++) {
				if (posArray[i] != null && posArray[j] != null && posArray[i].compareTotalSalesValue(posArray[j])) {
					System.out.println("\tPoS " + i + " and " + j + " both have " + posArray[i].totalSales());
				}
			}
		}
		System.out.println();
	}
	
	// 4. List PoS with same number of Sales categories
	private static void listPoSsWithSameSalesCategories() {
		System.out.println("List of PoSs with same Sales categories:");
		System.out.println();
		for (int i = 0; i < posArray.length; i++) {
			for (int j = i + 1; j < posArray.length; j++) {
				if (posArray[i] != null && posArray[j] != null && posArray[i].compareSalesCategories(posArray[j])) {
					System.out.println("\tPoSs " + i + " and " + j + " both have " + posArray[i].getMeals().toString());
				}
			}
		}
		System.out.println();
	}
	
	// 5. List PoSs with same $ amount of Sales and same number of Prepaid cards
	private static void listPoSEquals() {
		System.out.println("List of PoSs with same $ amount of sales and same number of PrePaiCards :");
		System.out.println();
	    boolean foundSame = false;
	    for(int i = 0; i < posArray.length; i++) {
	    	for (int j = i + 1; j < posArray.length; j++) {
	    		if (posArray[i] != null && posArray[j] != null && posArray[i].equals(posArray[j])) {
	    			System.out.println("\tPoSs " + i + " and " + j);
	    			foundSame = true;
	    		}
	    	}
	    }
	    if (! foundSame) {
	    	System.out.println("No equal PoS pairs found.");
	    }
	    System.out.println();
	}
	
	// 6. Add a PrePaiCard to an existing PoS
	private static void addPrepaidCardToPoS() {
		System.out.print("Which PoS to you want to add an PrePaiCard to? (Enter number 0 to 4): ");
	    int posIndex = sc.nextInt();
	    
	    if (posIndex < 0 || posIndex >= posArray.length) {
	    	 System.out.println("Invalid PoS index.");
	    	 return;
	    }
	    System.out.print("Please enter the following information so that we may complete the PrePaiCard-" );
	    System.out.println("\n--> Type of PrePaiCard (Carnivore, Halal, Kosher, Pescatarian, Vegetarian, Vigan): ");
	    String cardType = sc.next();
	    System.out.print("--> Id of the prepaid card owner: ");
	    String cardHolderId = sc.next();
	    System.out.print("--> Expiry day number and month (seperate by a space): ");
	    int expiryDay = sc.nextInt();
	    int expiryMonth = sc.nextInt();

	    PrePaiCard newCard = new PrePaiCard(cardType, cardHolderId, expiryDay, expiryMonth);
	    posArray[posIndex].addPrePaiCard(newCard);
	    int newTotal = posArray[posIndex].getPrePaiCards() != null ? posArray[posIndex].getPrePaiCards().length : 1;
	    System.out.println("You now have " + newTotal + " PrePaiCard" + (newTotal > 1 ? "s" : ""));
	    
	}
	
	// 7. Remove an existing PrePaiCard from a PoS 
	private static void removePrepaidCard() {
		// Prompt for PoS index
		System.out.print("Which PoS you want to remove an PrePaiCard from? (Enter number 0 to 4): ");
		int posIndex = sc.nextInt();
		
		// Check if the selected PoS has PrePaiCards
		if (posArray[posIndex].getPrePaiCards() == null || posArray[posIndex].getPrePaiCards().length == 0) {
			System.out.println("Sorry that PoS has no PrePaiCards");
			return;
		}
		// Prompt for PrePaiCard index within the valid range
		System.out.println("(Enter number 0 to " + (posArray[posIndex].getPrePaiCards().length - 1) + "): ");
	    int cardIndex = sc.nextInt();
	    
	    // Remove the PrePaiCard if the index is valid
	    if (posArray[posIndex].removePrePaiCard(cardIndex)) {
	    	System.out.println("PrePaiCard was removed successfully");
	    }else {
	    	System.out.println("Sorry, could not remove the PrePaiCard. Please check the index.");
	    }
	    
	    System.out.println();
	}
	
	// 8. Update the expiry date of an existing Prepaid card
	private static void updateExpiryDate() {
		System.out.print("Which PoS do you want to update an PrePaiCard from? (Enter number 0 to 4): ");
		int posIndex = sc.nextInt();
	    System.out.println("Which PrePaiCard do you want to update? (Enter number 0 to 0): ");
	    int cardIndex = sc.nextInt();
	    System.out.print(" --> Enter new expiry date day number and month (seperate by a space): ");
	    int day = sc.nextInt();
	    int month = sc.nextInt();

	    posArray[posIndex].updateExpiryDate(cardIndex, day, month);
	    System.out.println("Expiry Date updated.");
	}
	
	// 9. Add sales to a PoS 
	private static void addSalesToPoS() {
		System.out.print("Which PoS do you want to add Sales to? (Enter number 0 to 4): ");
		int posIndex = sc.nextInt();
		
	    System.out.println("How many junior, teen ,medium,big and family meal menu do you want to add?");
	    System.out.print("Enter 5 numbers seperated by a space): ");
	    int junior = sc.nextInt();
	    int teen = sc.nextInt();
	    int medium = sc.nextInt();
	    int big = sc.nextInt();
	    int family = sc.nextInt();
	    
	    double totalSales = posArray[posIndex].addMealSales(junior, teen, medium, big, family);
	    System.out.println("You now have $" + String.format("%.1f", totalSales));
	
	    System.out.println();
	}
	
}
