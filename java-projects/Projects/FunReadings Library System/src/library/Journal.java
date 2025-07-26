package library;

import library.Item;

public class Journal extends Item {
	
	private int volumeNumber; // The volume number of the Journal.
	private static int nextJournalID = 1; // Static counter for Journal IDs.
    private String ID; // Unique ID for the Journal, starting with 'J'.
	
	// Default constructor
	public Journal(){
		super(); // Calls the constructor of the Item class.
		this.ID =  "J"+nextJournalID; // Sets a unique ID for the Journal.
        nextJournalID++; // Increments the ID for the next Journal.
	}

	@Override
	public String getID() {
		return this.ID;
	}

	// Parameterized constructor
	public Journal(String name, String authors, int yearOfPublication, int volumeNumber) {
		super(name, authors, yearOfPublication);
        this.volumeNumber = volumeNumber;
        this.ID =  "J"+nextJournalID; // Sets a unique ID for the Journal.
        nextJournalID++; // Increments the ID for the next Journal.
	}

	// Getters and Setters
	public int getVolumeNumber() {
		return volumeNumber;
	}

	public void setVolumeNumber(int volumeNumber) {
		
		if (volumeNumber > 0) {
			this.volumeNumber = volumeNumber;
		}
		
	}
	
	// toString method 
	public String toString() {
		return "Journal{" + "ID='" + ID + '\'' +
				", name='" + getName() + '\'' +
				", authors='" + getAuthors() + '\'' +
				", yearOfPublication=" + getYearOfPublication() +
				", volumeNumber=" + volumeNumber +
				'}';
	}
	
	// equals method 
	public boolean equals(Object obj) {
		if (!super.equals(obj)) return false; // First check equality based on Item class attributes.
        if (obj == null || this.getClass() != obj.getClass()) return false; // Check if the object is an instance of Journal.
        Journal journal = (Journal) obj; // Cast the object to Journal.
        return this.volumeNumber == journal.volumeNumber; // Compare volume numbers for equality.
	}
	
}
