package driver;

import library.*;
import clients.Client;

import java.util.Scanner;

public class Driver {
	
	// Initialize library with capacity
	private static Library library = new Library( 1000);
    private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		//Set boolean condition
		boolean exit = false;
		//Using while loop to keep system running
		while (!exit) {
			
			displayMenu();
			int choice = scanner.nextInt();
            scanner.nextLine(); // 
            //Using switch to point different result base on input
            switch (choice) {
            case 1:
            	addItem();
            	break;
            case 2:
            	removeItem();
            	break;
            case 3:
            	changeItemInformation();
            	break;
            case 4:
            	listItemsByCategory();
            	break;
            case 5:
            	printAllItems();
            	break;
            case 6:
            	addClient();
            	break;
            case 7:
            	editClient();
            	break;
            case 8:
            	deleteClient();
            	break;
            case 9:
            	leaseReturnItem();
            	break;
            case 10:
            	showItemsLeasedByClient();
            	break;
            case 11:
            	showAllLeasedItems();
            	break;
            case 12:
            	displayBiggestBook();
            	break;
            case 13:
            	makeCopyOfBooksArray();
            	break;
            case 14:
            	exit = true;
            	System.out.println("\nThank you for using Library Management System!");
            	break;
            	default:
            		System.out.println("Invalid choice. Please try again.");
            } 

          }		
		}
	//Display menu
	private static void displayMenu() {
		System.out.println("\nLibrary Management System");
        System.out.println("1. Add Item");
        System.out.println("2. Remove Item");
        System.out.println("3. Change Item Information");
        System.out.println("4. List Items by Category");
        System.out.println("5. Print All Items");
        System.out.println("6. Add Client");
        System.out.println("7. Edit Client");
        System.out.println("8. Delete Client");
        System.out.println("9. Lease/Return Item");
        System.out.println("10. Show Items Leased by Client");
        System.out.println("11. Show All Leased Items");
        System.out.println("12. Display Biggest Book");
        System.out.println("13. Make Copy of Books Array");
        System.out.println("14. Exit");
        System.out.print("Enter your choice: ");
	}
	//Method to add item
	private static void addItem() {
		
		System.out.println("Select the type of item to add:");
	    System.out.println("1. Book");
	    System.out.println("2. Journal");
	    System.out.println("3. Media");
	    System.out.print("Enter your choice: ");
	    
	    int itemType = scanner.nextInt();
	    scanner.nextLine();
	    switch (itemType) {
	    case 1: 
	    	addBook();
	    	break;
	    case 2: 
	    	addJournal();
	    	break;
	    case 3:
	    	addMedia();
	    	break;
	    	default:
	    		System.out.println("Invalid choice. Please enter a number between 1 and 3.");
	    }
		
	}
	
	// Method to add a new book to the library
	private static void addBook() {
		System.out.print("Enter book name: ");
	    String name = scanner.nextLine();
	    System.out.print("Enter author name: ");
	    String author = scanner.nextLine();
	    System.out.print("Enter year of publication: ");
	    int year = scanner.nextInt();
	    System.out.print("Enter number of pages: ");
	    int pages = scanner.nextInt();
	    
	    // Creating a new book object with the provided details
	    Book book = new Book(name, author, year, pages);
	    
	    // Attempting to add the book to the library's collection
	    if (library.addItem(book)) {
	    	// If book is successfully added, print confirmation message
	    	System.out.println("Book added successfully: " + book.toString());
	    }else {
	    	// If book could not be added, print error message
	    	System.out.println("Failed to add book.");
	    }
	}
	//Method to add journal
	private static void addJournal() {
		System.out.print("Enter journal name: ");
	    String name = scanner.nextLine();
	    System.out.print("Enter author name: ");
	    String author = scanner.nextLine();
	    System.out.print("Enter year of publication: ");
	    int year = scanner.nextInt();
	    System.out.print("Enter volume number: ");
	    int volume = scanner.nextInt();
	    Journal journal = new Journal(name, author, year, volume);
	    
	    if (library.addItem(journal)) {
	    	System.out.println("Journal added successfully: " + journal.toString());
	    }else {
	    	System.out.println("Failed to add journal.");
	    }
	}
	//Method to add media
	private static void addMedia() {
		System.out.print("Enter media title: ");
	    String title = scanner.nextLine();
	    System.out.print("Enter author name: ");
	    String author = scanner.nextLine();
	    System.out.print("Enter year of publication: ");
	    int year = scanner.nextInt();
	    scanner.nextLine(); 
	    System.out.print("Enter media type (audio/video/interactive): ");
	    String type = scanner.nextLine();
	    Media media = new Media(title, author, year, type);
	    
	    if (library.addItem(media)) {
	    	System.out.println("Media added successfully: " + media.toString());
	    }else {
	    	System.out.println("Failed to add media.");
	    }
	}
	//Method to remove item
	private static void removeItem() {
	    System.out.print("Enter item ID: ");
		String id = scanner.next();

		if(library.removeItem(id)) {
			
		    	System.out.println("Item remove successfully" );
		}else {
		    	System.out.println("Failed to remove item.");
		    }
		
	}
	//Method to change info 
	private static void changeItemInformation() {
		System.out.println("Select the type of item to change information:");
	    System.out.println("1. Book");
	    System.out.println("2. Journal");
	    System.out.println("3. Media");
	    System.out.print("Enter your choice: ");

	    int itemType = scanner.nextInt();
	    scanner.nextLine();
	    //Using switch method point to different result base on user's choice
	    switch(itemType) {
	    case 1:
	    	changeBookInformation();
	    	break;
	    case 2:
	    	changeJournalInformation();
	    	break;
	    case 3:
	    	changeMediaInformation();
	    	break;
	    	default: System.out.println("Invalid choice. Please enter a number between 1 and 3.");
	    		
	    }	
	}
	//Method to change book info 
	private static void changeBookInformation() {
		System.out.print("Enter the ID of the book to modify: ");
	    String bookId = scanner.next();
	    Book book = (Book) library.findItem(bookId);
	    
	    if (book == null) {
	    	System.out.println("Book not found.");
	        return;
	    }
	    System.out.print("Enter new name (leave blank to keep current): ");
	    String newName = scanner.nextLine();
	    if (!newName.isEmpty()) {
	    	book.setName(newName);
	    }
	    System.out.print("Enter new author name (leave blank to keep current): ");
	    String newAuthor = scanner.nextLine();
	    if (!newAuthor.isEmpty()) {
	    	book.setAuthors(newAuthor);
	    }
	    
	    System.out.print("Enter new year of publication (0 to keep current): ");
	    int newYear = scanner.nextInt();
	    if (newYear > 0) {
	    	book.setYearOfPublication(newYear);
	    }
	    scanner.nextLine();
	    
	    System.out.print("Enter new number of pages (0 to keep current): ");
	    int newPages = scanner.nextInt();
	    if (newPages > 0) {
	    	book.setNumberOfPages(newPages);
	    }
	    scanner.nextLine();
	    System.out.println("Book information updated: " + book.toString());
	    
	}
	//Method to change journal info 
	private static void changeJournalInformation() {
		System.out.print("Enter the ID of the journal to modify: ");
	    String journalId = scanner.nextLine();
	    Journal journal = (Journal) library.findItem(journalId);
	    if (journal == null) {
	    	System.out.println("Journal not found.");
	        return;
	    }
	    
	    System.out.print("Enter new name (leave blank to keep current): ");
	    String newName = scanner.nextLine();
	    if (!newName.isEmpty()) {
	    	journal.setName(newName);
	    }
	    
	    System.out.print("Enter new author name (leave blank to keep current): ");
	    String newAuthor = scanner.nextLine();
	    if (!newAuthor.isEmpty()) {
	    	journal.setAuthors(newAuthor);
	    }
	    
	    System.out.print("Enter new year of publication (0 to keep current): ");
	    int newYear = scanner.nextInt();
	    if (newYear > 0) {
	    	journal.setYearOfPublication(newYear);
	    }
	    scanner.nextLine();
	    
	    System.out.print("Enter new volume number (0 to keep current): ");
	    int newVolume = scanner.nextInt();
	    if (newVolume > 0) {
	    	journal.setVolumeNumber(newVolume);
	    }
	    
	    scanner.nextLine();
	    System.out.println("Journal information updated: " + journal.toString());
	    
	}
	//Method to change media info 
	private static void changeMediaInformation() {
		System.out.print("Enter the ID of the media to modify: ");
	    String mediaId = scanner.nextLine();
	    Media media =(Media) library.findItem(mediaId);
	    if (media == null) {
	    	System.out.println("Media not found.");
	        return;
	    }
	    
	    System.out.print("Enter new title (leave blank to keep current): ");
	    String newTitle = scanner.nextLine();
	    if (!newTitle.isEmpty()) {
	        media.setName(newTitle);
	    }
	    
	    System.out.print("Enter new author name (leave blank to keep current): ");
	    String newAuthor = scanner.nextLine();
	    if (!newAuthor.isEmpty()) {
	        media.setAuthors(newAuthor);
	    }

	    System.out.print("Enter new year of publication (0 to keep current): ");
	    int newYear = scanner.nextInt();
	    if (newYear > 0) {
	        media.setYearOfPublication(newYear);
	    }
	    scanner.nextLine();
	    
	    System.out.print("Enter new media type (audio/video/interactive, leave blank to keep current): ");
	    String newType = scanner.nextLine();
	    if (!newType.isEmpty()) {
	        media.setType(newType);
	    }

	    System.out.println("Media information updated: " + media.toString());
	}
	//Method to show item by different category
	private static void listItemsByCategory() {
		System.out.println("Select the category of items to list:");
	    System.out.println("1. Books");
	    System.out.println("2. Journals");
	    System.out.println("3. Media");
	    System.out.print("Enter your choice: ");

	    int category = scanner.nextInt();
	    scanner.nextLine();
	  //Using switch method point to different result base on user's choice
	    switch (category) {
	    case 1:
			library.getAllItemsByCategory('B');
			printBooks();
            break;
	    case 2:
			library.getAllItemsByCategory('J');
			printJournals();
            break;
	    case 3:
			library.getAllItemsByCategory('M');
			printMedia();
            break;
            default: 
            	System.out.println("Invalid choice. Please enter a number between 1 and 3.");
	    }
	}
	
	private static void listBooks() {
		Item[] books = library.getAllBooks();

		if (books !=null) {
			if (books.length == 0) {
				System.out.println("No books available.");
				return;
			}
			for (Item book : books) {
				if (book != null) {
					System.out.println(book);
				}
			}
		}

	}
	
	private static void listJournals() {
		Item[] journals = library.getAllJournals();
		if (journals.length == 0) {
			System.out.println("No journals available.");
	        return;
		}
		for (Item journal : journals) {
			if (journal != null) {
				System.out.println(journal);
			}
		}
	}
	
	private static void listMedia() {
		Item[] mediaItems = library.getAllMedia();
		if (mediaItems.length == 0) {
			System.out.println("No media items available.");
	        return;
		}
		for (Item media : mediaItems) {
			if (media != null) {
				System.out.println(media);
			}
		}
	}
	//Method to display all item 
	private static void printAllItems() {
		System.out.println("Listing all items in the library:");
		System.out.println("\nBooks:");
	    printBooks();
	    
	    System.out.println("\nJournals:");
	    printJournals();
	    
	    System.out.println("\nMedia:");
	    printMedia();
	}
	//Display book
	private static void printBooks() {
		Item[] books = library.getAllBooks();
		if (books == null || books.length == 0) {
			System.out.println("No books available.");
		}else {
			for (Item book : books) {
				if (book != null) {
					System.out.println(book);
				}
			}
		}
	}
	//Display journal
	private static void printJournals() {
		Item[] journals = library.getAllJournals();
		if (journals == null || journals.length == 0) {
			System.out.println("No journals available.");
		}else {
			for (Item journal : journals) {
				if (journal != null) {
					System.out.println(journal);
				}
			}
		}
	}
	//Display media
	private static void printMedia() {
		Item[] mediaItems = library.getAllMedia();
		if (mediaItems == null || mediaItems.length == 0) {
			System.out.println("No media items available.");
		}else {
			for (Item media : mediaItems) {
				if (media != null) {
					System.out.println(media);
				}
			}
		}
	}
//method to add new client
	private static void addClient() {
		System.out.print("Enter client ID: ");
	    String id = scanner.nextLine();
	    System.out.print("Enter client name: ");
	    String name = scanner.nextLine();
	    System.out.print("Enter client phone number: ");
	    String phoneNumber = scanner.nextLine();
	    System.out.print("Enter client email: ");
	    String email = scanner.nextLine();
	    // Create a new client object with the provided details
	    Client client = new Client(id, name, phoneNumber, email);
	    // Add the new client to the library
	    if (library.addClient(client)) {
	    	System.out.println("Client added successfully: " + client.toString());
	    }else {
	    	System.out.println("Failed to add client.");
	    }
		
	}
	//Method to edit the client
	private static void editClient() {
		
		System.out.print("Enter the ID of the client to modify: ");
	    String clientId = scanner.nextLine();
	 // Find the client with the provided ID
	    Client client = library.findClient(clientId); 
	    if (client == null) {
	    	System.out.println("Client not found.");
	        return;
	    }
	    
	    System.out.print("Enter new name (leave blank to keep current): ");
	    String newName = scanner.nextLine();
	    if (!newName.isEmpty()) {
	        client.setName(newName);
	    }

	    System.out.print("Enter new phone number (leave blank to keep current): ");
	    String newPhone = scanner.nextLine();
	 // Update the client's name if new name is provided
	    if (!newPhone.isEmpty()) {
	        client.setPhoneNumber(newPhone);
	    }

	    System.out.print("Enter new email (leave blank to keep current): ");
	    String newEmail = scanner.nextLine();
	    if (!newEmail.isEmpty()) {
	        client.setEmail(newEmail);
	    }

	    System.out.println("Client information updated: " + client.toString());
		
	}
	//method to delete  the client
	private static void deleteClient() {
		System.out.print("Enter the ID of the client to delete: ");
	    String clientId = scanner.nextLine();
	 // Attempt to remove the client with the provided ID from the library
	    boolean success = library.removeClient(clientId); 

	    if (success) {
	    	System.out.println("Client deleted successfully.");
	    }else {
	    	System.out.println("Client not found or could not be deleted.");
	    }
		
	}
	//Method for the leased/return item
	private static void leaseReturnItem() {
		
		System.out.println("Select an operation:");
	    System.out.println("1. Lease an item");
	    System.out.println("2. Return an item");
	    System.out.print("Enter your choice: ");

	    int operation = scanner.nextInt();
	    scanner.nextLine();
	    //Using the switch method to show different case base on usr's choice
	    switch (operation) {
	    case 1:
	    	leaseItem();
            break;
	    case 2:
	    	returnItem();
            break;
	    	default:
	    		System.out.println("Invalid choice. Please enter 1 or 2.");
	    }	
	}
	//Method for lease item
	private static void leaseItem() {
		System.out.print("Enter client ID: ");
	    String clientId = scanner.nextLine();
	    System.out.print("Enter item ID: ");
	    String itemId = scanner.nextLine();
	 // Attempt to lease the item to the client
	    boolean success = library.leaseItem(clientId, itemId); 

	    if (success) {
	    	System.out.println("Item leased successfully.");
	    }else {
	    	System.out.println("Item could not be leased. Check client or item ID.");
	    }
	}
	//Method for return item
	private static void returnItem() {
		System.out.print("Enter client ID: ");
	    String clientId = scanner.nextLine();
	    System.out.print("Enter item ID: ");
	    String itemId = scanner.nextLine();
	 // Attempt to return the item from the client
	    boolean success = library.returnItem(clientId, itemId);
	    
	    if (success) {
	    	System.out.println("Item returned successfully.");
	    }else {
	    	System.out.println("Item could not be returned. Check client or item ID.");
	    }
	}
	//display the items leased by that client.
	private static void showItemsLeasedByClient() {
		System.out.print("Enter the client ID to show their leased items: ");
	    String clientId = scanner.nextLine();
	 // Get all items leased by the client
	    Item[] leasedItems = library.getItemsLeasedByClient(clientId); 

	    if (leasedItems == null || leasedItems.length == 0) {
	    	System.out.println("No items leased by this client.");
	        return;
	    }
	    
	    System.out.println("Items leased by client ID " + clientId + ":");
	    for (Item item : leasedItems) {
	    	if (item != null) {
	    		System.out.println(item);
	    	}
	    }
	}
	//Display all items currently leased in the library.
	private static void showAllLeasedItems() {
		
		System.out.println("Listing all leased items in the library:");
		 // Get all leased items
	    Item[] allLeasedItems = library.getAllLeasedItems(); 
	    if (allLeasedItems == null || allLeasedItems.length == 0) {
	    	System.out.println("No leased items currently.");
	        return;
	    }
	    
	    for (Item item : allLeasedItems) {
	    	if (item != null) {
	    		System.out.println(item);
	    	}
	    }
		
	}
	//Method to display the biggest book  in the system base on the pages
	private static void displayBiggestBook() {
		 // Get all books from the library
		Item[] books = library.getAllBooks();
	    if (books == null || books.length == 0) {
	    	System.out.println("No books available in the library.");
	        return ;
	    }
	 // Find the biggest book in the library based on the number of pages
		Book biggestBook = (Book) books[0];

		if (biggestBook!=null){

			for (Item book : books) {

				if (book != null) {

					Book tempBook = (Book) book;

					if( tempBook.getNumberOfPages() > biggestBook.getNumberOfPages()) {
						biggestBook = tempBook;
					}
					
				}
			}

		}

	    System.out.println("The biggest book in the library is:");
	    System.out.println(biggestBook);
		
	}
	//Create a copy of the array containing all books in the library.
	private static void makeCopyOfBooksArray() {
		// Get all books from the library
		Item[] originalBooks = library.getAllBooks();
	    if (originalBooks == null || originalBooks.length == 0) {
	    	System.out.println("No books available to copy.");
	        return;
	    }
	 // Create a new array to store copied books
	    Book[] copiedBooks = new Book[originalBooks.length];
	 // Copy each book from the original array to the copied array
	    for (int i = 0; i < originalBooks.length; i++) {
	    	if (originalBooks[i] != null) {
	    		copiedBooks[i] = new Book((Book) originalBooks[i]);
	    	}
	    }
	    
	    System.out.println("A copy of the books array has been made.");
	    for (Book book : copiedBooks) {
	    	if (book != null) {
	    		System.out.println(book);
	    	}
	    }
	}

}
