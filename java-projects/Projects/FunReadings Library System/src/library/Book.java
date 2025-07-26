package library;

import library.Item;

public class Book extends Item{
	
	private int numberOfPages;
	private String ID; // Unique ID for Book, starting with 'B'
    private static int nextBookID = 1; // Static counter for Book IDs
    
    // Default constructor
    public Book () {
    	super(); // Calls the constructor of Item
    	this.ID =  "B" + nextBookID; // Sets a unique ID for Book
        nextBookID++;
    }
    
    // Parameterized constructor
    public Book(String name, String authors, int yearOfPublication, int numberOfPages) {
    	super(name, authors, yearOfPublication); // Calls the parameterized constructor of Item
        this.numberOfPages = numberOfPages;
        this.ID = "B" + nextBookID; // Sets a unique ID for Book
        nextBookID++;
    }
    
    public Book(Book book) {
    	
    	super(book); // Call the copy constructor of the superclass (Item) to copy common fields
    	this.numberOfPages = book.numberOfPages; // Copy the numberOfPages field from the given book
        this.ID = book.ID; // Copy the ID field from the given book
		
	}

	// Getters and Setters
    public int getNumberOfPages() {
    	return numberOfPages;
    }
    
    public  void setNumberOfPages(int numberOfPages) {
    	if (numberOfPages > 0) {
    		this.numberOfPages = numberOfPages;
    	}
    }
    

    public String getID() {
		return this.ID;
	}


	// toString method
    public String toString() {
    	return "Book{" + "ID='" + ID + '\'' +
    			", name='" + getName() + '\'' +
    			", authors=" + getAuthors() +
    			", yearOfPublication=" + getYearOfPublication() +
    			", numberOfPages=" + numberOfPages +
    			'}';
    }
    
    // equals method 
    public boolean equals(Object obj) {
    	if (!super.equals(obj)) return false;
    	if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return this.numberOfPages == book.numberOfPages;
    }
  
}
