public class PrePaiCard {
	
	private String cardType;
	private String cardHolderId;
	private int expiryDay;
	private int expiryMonth;
	
	// Default constructor
		public PrePaiCard() {
			
			this.cardType = "";
			this.cardHolderId = "";
			this.expiryDay = 0;
			this.expiryMonth = 0;
		}
	
	// limit the card type
	private boolean isValidCardType(String cardType) {
		return cardType.equals("Carnivore") || cardType.equals("Halal") ||
				cardType.equals("Kosher") || cardType.equals("Pescatarian") ||
				cardType.equals("Vegetarian") || cardType.equals("Vegan");
	}
	
	// Constructor with 4 parameters
	public PrePaiCard (String cardType, String cardHolderId, int expiryDay, int expiryMonth) {

		this.cardType = cardType;
		
		this.cardHolderId = cardHolderId;
		
		if (expiryDay >= 1 && expiryDay <= 31) {
			this.expiryDay = expiryDay;
		}else {
			this.expiryDay = 0;
		}
		
		if (expiryMonth >= 1 && expiryMonth <= 12) {
			this.expiryMonth = expiryMonth;
		}else {
			this.expiryMonth = 0;
		}
	
	}
	
	// Copy constructor
	public PrePaiCard (PrePaiCard other) {
		this.cardType = other.cardType;
		this.cardHolderId = other.cardHolderId;
		this.expiryDay = other.expiryDay;
		this.expiryMonth = other.expiryMonth;
	}
	
	// Accessor methods for all attributes
	public String getCardType () {
		return cardType;
	}
	
	public String getCardHolderId() {
		return cardHolderId;
	}
	
	public int getExpiryDay() {
		return expiryDay;
	}
	
	public int getExpiryMonth() {
		return expiryMonth;
	}
	
	// Mutator methods
	public void setExpiryDay(int expiryDay) {
		if (expiryDay >= 1 && expiryDay <= 31) {
			this.expiryDay = expiryDay;
		}else {
			this.expiryDay = 0;
		}
	}
	
	public void setExpiryMonth(int expiryMonth) {
		if (expiryMonth >= 1 && expiryMonth <= 12) {
			this.expiryMonth = expiryMonth;
		}else {
			this.expiryMonth = 0;
		}
	}
	
	// toString()
	public String toString() {
		
		String formattedDay = (expiryDay < 10) ? "0" + expiryDay : Integer.toString(expiryDay);
		String formattedMonth = (expiryMonth < 10) ? "0" + expiryMonth : Integer.toString(expiryMonth);
		return cardType + " - " + cardHolderId + " - " + formattedDay + "/" + formattedMonth + ".";
		
	}

	// equals()
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		
		PrePaiCard other = (PrePaiCard) obj;
		
		return cardType.equals(other.cardType) && 
				cardHolderId.equals(other.cardHolderId)  && 
				expiryDay == other.expiryDay &&
				expiryMonth == other.expiryMonth;
	}

}
