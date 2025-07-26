public class Sales {
	
	// Static constants for the values of each sale
	private static final int JUNIOR_PRICE = 5;
	private static final int TEEN_PRICE = 10;
	private static final int MEDIUM_PRICE = 12;
	private static final int BIG_PRICE = 15;
	private static final int FAMILY_PRICE = 20;
	
	// Attributes to track the number of sales for each category
	private int juniorSales;
	private int teenSales;
	private int mediumSales;
	private int bigSales;
	private int familySales;
	
	// Constructors:
	
	// Default constructor
	public Sales() {
		this.juniorSales = 0;
		this.teenSales = 0;
		this.mediumSales = 0;
		this.bigSales = 0;
		this.familySales = 0;
	}
	
	// Constructor with 5 integer parameters
	public Sales(int juniorSales, int teenSales, int mediumSales, int bigSales, int familySales) {
		this.juniorSales = juniorSales;
		this.teenSales = teenSales;
		this.mediumSales = mediumSales;
		this.bigSales = bigSales;
		this.familySales = familySales;
	}
	
	// Copy constructor
	public Sales(Sales other) {
		this(other.juniorSales, other.teenSales, other.mediumSales, other.bigSales, other.familySales);
	}
	
	// Accessor and mutator methods for all attributes
	public int getJuniorSales() {
		return juniorSales;
	}

	public void setJuniorSales(int juniorSales) {
		this.juniorSales = juniorSales;
	}

	public int getTeenSales() {
		return teenSales;
	}

	public void setTeenSales(int teenSales) {
		this.teenSales = teenSales;
	}

	public int getMediumSales() {
		return mediumSales;
	}

	public void setMediumSales(int mediumSales) {
		this.mediumSales = mediumSales;
	}

	public int getBigSales() {
		return bigSales;
	}

	public void setBigSales(int bigSales) {
		this.bigSales = bigSales;
	}

	public int getFamilySales() {
		return familySales;
	}

	public void setFamilySales(int familySales) {
		this.familySales = familySales;
	}
	
	// addSales() method
	public void addSales(int junior, int teen, int medium, int big, int family) {
		this.juniorSales += junior;
		this.teenSales += teen;
		this.mediumSales += medium;
		this.bigSales += big;
		this.familySales += family;
	}

	// SalesTotal() method
	public int SalesTotal() {
		return this.juniorSales * JUNIOR_PRICE + 
				this.teenSales * TEEN_PRICE +
				this.mediumSales * MEDIUM_PRICE +
				this.bigSales * BIG_PRICE +
				this.familySales * FAMILY_PRICE;
		
	}
	
	// toString() method
	public String toString() {
		return juniorSales + " x $" + JUNIOR_PRICE + " + " +
				teenSales + " x $" + TEEN_PRICE + " + " +
				mediumSales + " x $" + MEDIUM_PRICE + " + " +
				bigSales + " x $" + BIG_PRICE + " + " +
				familySales + " x $" + FAMILY_PRICE;
				
	}
	
	// equals() method
	public boolean equals(Object obj) {
		
		// Check if the same reference or null/type mismatch
		if (this == obj) 
			return true;
		// Cast to Sales class after confirming same type
		if (obj == null || getClass() != obj.getClass())
			return false;
		// Compare all relevant properties
		Sales other = (Sales) obj;
		return juniorSales == other.juniorSales && 
				teenSales == other.teenSales && 
				mediumSales == other.mediumSales && 
				bigSales == other.bigSales && 
				familySales == other.familySales;
	}
	
}
