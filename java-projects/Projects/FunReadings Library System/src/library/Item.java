package library;

import clients.Client;

public abstract class Item {
	
	private static int nextID = 1; // Static variable to keep track of the next ID value.
	protected int ID ;  //ID is unique.
    private String name;
    private String authors;
    private int yearOfPublication;
    private Client leasedBy;
    // Default constructor. Assigns a unique ID and increments the static nextID counter.
    public Item () {
    	this.ID = nextID++;
    }
    
    // Parameterized constructor
    public Item(String name, String authors, int yearOfPublication) {
    	this(); // Calls default constructor to set the ID
    	this.name = name;
        this.authors = authors;
        this.yearOfPublication = yearOfPublication;
    }
    
    // Copy constructor.
    public Item (Item other){
    	this.name = other.name;
        this.authors = other.authors;
        this.yearOfPublication = other.yearOfPublication;
        this.ID = nextID++;
    }
    
    // Getters and Setters
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public int getYearOfPublication() {
		return yearOfPublication;
	}

	public void setYearOfPublication(int yearOfPublication) {
		this.yearOfPublication = yearOfPublication;
	}


	public abstract String getID();

	// Equals method
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false; 
		Item item = (Item) obj;
		return yearOfPublication == item.yearOfPublication && 
				name.equals(item.name) && 
				authors.equals(item.authors);
	}

	// toString method
    public String toString() {
    	return "Item{" +
                "ID='" + ID + '\'' +
                ", name='" + name + '\'' +
                ", authors=" + authors +
                ", yearOfPublication=" + yearOfPublication +
                '}';
    }
//Getter and setter method for leasedby 
	public Client getLeasedBy()
	{
		return leasedBy;
	}

	public void setLeasedBy(Client client)
	{
		this.leasedBy = client;
		
	}

}
