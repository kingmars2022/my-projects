import java.util.Scanner;
public class BookstoreDriver {
	
	private static final String PASSWORD = "249"; // Constant for password
    private static final int MAX_PASSWORD_ATTEMPTS = 3; // Maximum attempts for password per operation
    private static final int MAX_TOTAL_ATTEMPTS = 4; // Maximum total attempts for password
    private static int passwordAttemptCounter = 0; // Track password attempts for each operation
    private static Book[] inventory; // Array to store books
    private static Scanner scanner = new Scanner(System.in); // Scanner for input

	public static void main(String[] args) {
		
		// Display a welcome message
		System.out.println("Welcome to the Bookstore Application!");

		// Initialize the inventory
		System.out.print("Enter the maximum number of books: ");
        int maxBooks = scanner.nextInt();
        inventory = new Book[maxBooks];
        
        boolean running = true;
        while (running) {
        	
        	displayMainMenu();
        	int choice = scanner.nextInt();
        	switch(choice) {
        	case 1:
        		enterNewBooks();
        		break;
        	case 2:
        		changeBookInfo();
        		break;
        	case 3:
        		displayBooksByAuthor();
        		break;
        	case 4:
        		displayBooksUnderPrice();
        		break;
        	case 5:
        		running = false;
        		break;
        		
        		default:
        			System.out.println("Invalid choice. Please enter a number between 1 and 5.");	
        	}
        }
        
        System.out.println("Thank you for using the Bookstore Application!");
		
	}
	
	// Display a main menu
	private static void displayMainMenu() {
		System.out.println("\nMain menu:");
        System.out.println("————————————————");
		System.out.println("What do you want to do?");
		System.out.println("1.\tEnter new books (password required)");
		System.out.println("2.\tChange information of a book (password required))");
		System.out.println("3.\tDisplay all books by a specific author))");
		System.out.println("4.\tDisplay all books under a certain a price.))");
		System.out.println("5.\tQuit))");
		System.out.println("Please enter your choice >");
	}
	
	// Method to add new books
	private static void enterNewBooks() {
		
		if (!checkPassword()) {
			return; // Exit if password check fails
		}
		
		System.out.print("How many books do you want to enter?");
	    int numOfBooksToAdd = scanner.nextInt();
	    scanner.nextLine(); // Clear the buffer
	    
	    // Count the number of available spaces in the inventory
	    int availableSpaces = 0;
	    for (Book book : inventory) {
	    	if (book == null) {
	    		availableSpaces++;
	    	}
	    }
	    
	    //  Determine the actual number of books that can be added
	    int booksToAdd = Math.min(numOfBooksToAdd, availableSpaces);
	    
	    for (int i = 0, addedBooks = 0; i < inventory.length && addedBooks < booksToAdd; i++) {
	    	if (inventory[i] == null) {
	    		System.out.println("Enter details for book " + (addedBooks + 1) + ":");

	            System.out.print("Title: ");
	            String title = scanner.nextLine();

	            System.out.print("Author: ");
	            String author = scanner.nextLine();

	            System.out.print("ISBN: ");
	            long isbn = scanner.nextLong();
	            scanner.nextLine(); // Clear the buffer

	            System.out.print("Price: ");
	            double price = scanner.nextDouble();
	            scanner.nextLine(); // Clear the buffer

	            inventory[i] = new Book(title, author, isbn, price);
	            addedBooks++;
	    	}
	    }
	    
	    if (booksToAdd < numOfBooksToAdd) {
	    	System.out.println("Only " + booksToAdd + " books were added due to limited space.");
	    }else {
	    	System.out.println("Books added successfully.");
	    }
	    
	    }
	    
	    // Method to change book information
	    private static void changeBookInfo() {
	    	
			if (!checkPassword()) {
				return; // Exit if password check fails
			}
			
			System.out.print("Enter the book index you wish to update (0 to " + (inventory.length - 1) + "): ");
		    int bookIndex = scanner.nextInt();
		    scanner.nextLine(); // Clear the buffer

		    if (bookIndex < 0 || bookIndex >= inventory.length || inventory[bookIndex] == null) {
		    	System.out.println("Invalid book index or no book at this index.");
		        return;
		    }
		    
		    Book book = inventory[bookIndex];
		    System.out.println("Current information of the book:");
		    System.out.println(book);
		    
		    boolean keepUpdating = true;
		    while (keepUpdating) {
		    	System.out.println("What information would you like to change?");
		        System.out.println("1. author");
		        System.out.println("2. title");
		        System.out.println("3. ISBN");
		        System.out.println("4. price");
		        System.out.println("5. Quit");
		        System.out.print("Enter your choice > ");
		        
		        int choice = scanner.nextInt();
		        scanner.nextLine(); // Clear the buffer
		        
		        switch (choice) {
		        case 1:
		        	System.out.print("New Author: ");
	                book.setAuthor(scanner.nextLine());
	                break;
		        case 2:
		        	System.out.print("New Title: ");
	                book.setTitle(scanner.nextLine());
	                break;
		        case 3:
		        	System.out.print("New ISBN: ");
	                book.setISBN(scanner.nextLong());
	                scanner.nextLine(); // Clear the buffer
	                break;
		        case 4:
		        	System.out.print("New Price: $");
	                book.setPrice(scanner.nextDouble());
	                scanner.nextLine(); // Clear the buffer
	                break;
		        case 5: 
		        	keepUpdating = false;
	                break;
	                default:
	                	System.out.println("Invalid choice. Please enter a number between 1 and 5.");
	                    break;
		        }
		        
		        System.out.println("Updated book information:");
		        System.out.println(book);
		    }	
		}
	
	    // Method to display books by a specific author
	    private static void displayBooksByAuthor() {
	    	scanner.nextLine(); // Clear the buffer
	        System.out.print("Enter author's name: ");
	        String authorName = scanner.nextLine();
	        boolean found = false;
	        
	        // Iterate through the inventory and print books by the specified author
	        for (Book book : inventory) {
	        	if (book != null && book.getAuthor().equalsIgnoreCase(authorName)) {
	        		System.out.println(book);
	                found = true;
	        	}
	        }
	        
		    // If no books are found by the author, inform the user
	        if (!found) {
	        	System.out.println("No books found by author: " + authorName);
	        }
	    }
	    
	    // Method to display books under a certain price
	    private static void displayBooksUnderPrice() {
	    	System.out.print("Enter the price limit: $");
	        double priceLimit = scanner.nextDouble();
	        boolean found = false;
	        
	        // Iterate through the inventory and print books under the specified price
	        for (Book book : inventory) {
	        	
	        	if (book != null && book.getPrice() < priceLimit) {
	        		System.out.println(book);
	                found = true;
	        	}
	        	
	        }
	        
	        // If no books are found under the specified price, inform the user
	        if (!found) {
	        	System.out.println("No books found under price: $" + priceLimit);
	        }
	    }
	    
	    // Method to check the password
	    private static boolean checkPassword() {
	    	for (int attempts = 0; attempts < MAX_PASSWORD_ATTEMPTS; attempts++) {
	    		System.out.print("Enter password: ");
	            String input = scanner.next();
	            if (PASSWORD.equals(input)) {
	            	return true;
	            }else {
	            	System.out.println("Incorrect password. Try again.");
	            }
	    	}
	    	return false;
	    }

}
