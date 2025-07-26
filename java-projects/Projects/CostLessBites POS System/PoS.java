public class PoS {

	private Sales sales;
	private PrePaiCard[] prePaiCards;
	
	// Default Constructor
	public PoS() {
		this.sales = new Sales();
		this.prePaiCards = null;
		
	}
	// Constructor with parameters
	public PoS(Sales meals, PrePaiCard[] prePaiCards) {
		this.sales = new Sales (meals);
		
		if (prePaiCards != null) {
			this.prePaiCards = new PrePaiCard[prePaiCards.length];
			for (int i = 0; i < prePaiCards.length; i++) {
				if (prePaiCards[i] != null) {
					this.prePaiCards[i] = prePaiCards[i];
				}
			}
		}else {
			this.prePaiCards = null;
		}
	}
	
	// Method to add a new PrePaiCard
	public void addPrePaiCard(PrePaiCard newCard) {
		if (prePaiCards == null) {
			prePaiCards = new PrePaiCard[]{newCard};
		}else {
			PrePaiCard[] newCardsArray = new PrePaiCard[prePaiCards.length + 1];
			System.arraycopy(prePaiCards, 0, newCardsArray, 0, prePaiCards.length);
            newCardsArray[prePaiCards.length] = newCard;
            prePaiCards = newCardsArray;
		}
	}
	
	// Method to remove a pre-paid card
	public boolean removePrePaiCard(int index) {
		if (prePaiCards == null || index < 0 || index >= prePaiCards.length) {
			return false;
		}
		PrePaiCard[] newCardsArray = new PrePaiCard[prePaiCards.length - 1];
        System.arraycopy(prePaiCards, 0, newCardsArray, 0, index);
        System.arraycopy(prePaiCards, index + 1, newCardsArray, index, prePaiCards.length - index - 1);
        prePaiCards = newCardsArray;
        return true;
	}
	
	// Method to update expiry date of a prepaid card
	public void updateExpiryDate(int cardIndex, int day, int month) {
		if (cardIndex >= 0 && cardIndex < numberOfPrepaidCards()) {
			prePaiCards[cardIndex].setExpiryDay(day);
            prePaiCards[cardIndex].setExpiryMonth(month);
		}
	}
	
	// Method to add meal sales
	public int addMealSales(int junior, int teen, int medium, int big, int family) {
		sales.addSales(junior, teen, medium, big, family);
		return sales.SalesTotal();
	}
	
	// Method to get the total sales value
	public int totalSales() {
		return sales.SalesTotal();
	}
	
	// Method to get the number of PrePaiCards
	public int numberOfPrepaidCards() {
		return prePaiCards == null ? 0 : prePaiCards.length;
	}
	
	// Method to compare total sales value of two PoS
	public boolean compareTotalSalesValue(PoS other) {
		return this.totalSales() == other.totalSales();
	}
	
	// Method to compare the number of each sales category
	public boolean compareSalesCategories(PoS other) {
		return this.sales.equals(other.sales);
	}
	
	// equals() method
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		PoS pos = (PoS) obj;
		return this.totalSales() == pos.totalSales() &&
				this.numberOfPrepaidCards() == pos.numberOfPrepaidCards();
	}
	
	// toString() method
	public String toString() {
		String result = "Meals: " + sales.toString() + "\n" + "Prepaid Cards: ";
		
		if (prePaiCards == null || prePaiCards.length == 0) {
			result += "No Prepaid Cards\n";
		}else {
			for(PrePaiCard card : prePaiCards) {
				result += card + "\n";
			}
		}
		
		return result;

	}
	
	// Accessor and mutator methods
	public PrePaiCard[] getPrePaiCards() {
		return prePaiCards;
	}
	
	public Sales getMeals() {
		return sales;
	}

}