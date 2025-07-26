public class Book {
	
	// Attributes
	private String title;
	private String author;
	private long ISBN;
	private double price;
	private static int bookCount = 0;
	
	// Constructor to initialize the book object
	public Book(String title, String author, long ISBN, double price) {
		
		this.title = title;
		this.author =  author;
		this.ISBN = ISBN;
		this.price = price;
		bookCount++; // Increment the count of books created
	}
	
	// Copy constructor
	public Book(Book otherBook) {
		this.title = otherBook.title;
        this.author = otherBook.author;
        this.ISBN = otherBook.ISBN;
        this.price = otherBook.price;
	}

	// Getters and Setters for each attribute
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public long getISBN() {
		return ISBN;
	}

	public void setISBN(long iSBN) {
		ISBN = iSBN;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	// Static method to find the number of created books
	public static int findNumberOfCreatedBooks() {
			
			return bookCount;
	}

	// equals() method
	public boolean equals(Object obj) {
		
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		
		Book other = (Book) obj;
		
		return Double.compare(other.price, price) == 0;
			
	}
	
	// toSting() method
	public String toString() {
		
		return "Book # " + bookCount +
				"Author: " + this.author +
				"Title: " + this.title +
				"ISBN: isbn " + this.ISBN +
				"Price: $" +this.price;

	}

}
