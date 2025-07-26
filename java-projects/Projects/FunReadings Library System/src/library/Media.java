package library;

import library.Item;

public class Media extends Item {
	
	private String type; // audio, video, or interactive
	private static int nextMediaID = 1; // Static counter for Media IDs.
    private String ID; // Unique ID for the Media, starting with 'M'.
	
	// Default Constructor
	public Media () {
		super(); // Invoking the default constructor of the superclass
        this.type = ""; 
        this.ID = "M" + nextMediaID; // Sets a unique ID for the Media.
        nextMediaID++; // Increments the ID for the next Media.
	}

	public String getID() {
		return this.ID;
	}

	// Parameterized Constructor
	public Media(String name, String author, int yearOfPublication, String type) {
		super(name, author, yearOfPublication); // Invoking the superclass constructor
        this.type = type;
        this.ID =  "M" + nextMediaID; // Sets a unique ID for the Media.
        nextMediaID++; // Increments the ID for the next Media.
	}

	// Getter and Setter for type
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	// toString method
	public String toString() {
		return "Media{" + "ID='M" + ID + '\'' +
				", name='" + getName() + '\'' +
				", authors='" + getAuthors() + '\'' +
				", yearOfPublication=" + getYearOfPublication() + 
				", type='" + type + '\'' + 
				'}';
	}
	
	// equals method
	public boolean equals(Object obj) {
		if (!super.equals(obj)) return false; // First check equality based on Item class attributes.
        if (obj == null || this.getClass() != obj.getClass()) return false; // Check if the object is an instance of Media.
        Media media = (Media) obj; // Cast the object to Media.
        return this.type.equals(media.type); // Compare type for equality.
	}
	
}
