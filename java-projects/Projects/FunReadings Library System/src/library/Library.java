package library;

import clients.Client;

public class Library {
	private Item[] items;
	private Client[] clients;
	
	
	private int capacity;

	private int itemsIndex;
	private int clentsIndex;
	
    // Constructor to initialize the library
    public Library(int capacity) {
		items =  new Item[capacity]; // Initialize the items array with the given capacity
		clients= new Client[capacity]; // Initialize the clients array with the same capacity
		this.capacity = capacity; // Set the capacity field of the class to the provided value
    }

	public boolean addItem(Item item){
		if (itemsIndex < capacity) {
			items[itemsIndex++] = item;
			return true;
		}else {
			// Handle the case where the books array is full
			return false;
		}
	}

    public boolean removeItem(String id) {
    	for (int i = 0; i < itemsIndex; i++) {
    		if (items[i].getID().equals(id)) {
				items[i] = items[itemsIndex - 1]; // Replace the removed book with the last book in the array
				items[itemsIndex - 1] = null; // Set the last position to null
				itemsIndex--; // Decrement the book count

				return true; // Book removed successfully
    			
    		}
    	}
    	return false; // Book not found
    }
    
    public Item findItem(String id) {
    	for (int i = 0; i < itemsIndex; i++) {
    		if (items[i].getID().equals(id)) {
    			return items[i];
    		}
    	}
    	return null;
    }

	public Item[] getAllItemsByCategory(char init) {

		 Item[] result = new Item[capacity];
		    int resultIndex = 0;
		    for (int i = 0; i < itemsIndex; i++) {
		        if (items[i].getID().charAt(0) == init) {
		            result[resultIndex++] = items[i];
		        }
		    }
		    // set null for rest
		    for (int i = resultIndex; i < capacity; i++) {
		        result[i] = null;
		    }
		    return result;
	}

	public Item[] getAllBooks() {
		   return  getAllItemsByCategory('B');
	}

	public Item[] getAllJournals() {
		return getAllItemsByCategory('J');
	}

	public Item[] getAllMedia() {
		return getAllItemsByCategory('M');
	}

	public boolean addClient(Client client) {
		if (clentsIndex < capacity) {
			clients[clentsIndex++] = client;
			return true;
		}else {
			
			return false;
		}
	}

	public Client findClient(String clientId) {
		for (int i = 0; i < clentsIndex; i++) {
    		if (clients[i].getId().equals(clientId)) {
    			return clients[i];
    		}
    	}
    	return null;
	}

	public boolean removeClient(String clientId) {
		for (int i = 0; i < clentsIndex; i++) {
    		if (clients[i].getId().equals(clientId)) {
    			clients[i] = clients[clentsIndex - 1]; // Replace the removed client with the last one in the array
    			clients[clentsIndex - 1] = null; // Set the last position to null
    			clentsIndex--; // Decrement the client count
    		
				return true; // client removed successfully
    			
    		}
		}
    		return false; // Book not found
		
	}

	public boolean leaseItem(String clientId, String itemId) {
		 Client client = findClient(clientId);
		    Item item = findItem(itemId);
		    
		    if (client != null && item != null) {
		        if (item.getLeasedBy() == null) {
		            item.setLeasedBy(client);
		            return true; // Item leased successfully
		        } else {
		            System.out.println("Item is already leased");
		        }
		    } else {
		        System.out.println("Client or Item not found");
		    }
		    return false; // Leasing failed
	}

	public boolean returnItem(String clientId, String itemId) {
		 Client client = findClient(clientId);
		    Item item = findItem(itemId);
		    
		    if (client != null && item != null && item.getLeasedBy() == client) {
		        item.setLeasedBy(null);
		        System.out.println(clientId + " has returned " + itemId);
		        return true; // Item returned successfully
		    } else {
		        System.out.println("Client or Item not found, or item not leased by client");
		        return false; // Returning failed
		    }
	}

	public Item[] getItemsLeasedByClient(String clientId) {
		Client client = findClient(clientId);
	    if (client != null) {
	        Item[] leasedItems = new Item[itemsIndex];
	        int count = 0;
	        for (int i = 0; i < itemsIndex; i++) {
	            if (items[i].getLeasedBy() == client) {
	                leasedItems[count++] = items[i];
	            }
	        }
	        // Manually resize the array to remove null values
	        Item[] result = new Item[count];
	        for (int i = 0; i < count; i++) {
	            result[i] = leasedItems[i];
	        }
	        return result;
	    } else {
	        System.out.println("Client not found");
	        return new Item[0]; // Return an empty array if client not found
	    }
}

	

	public Item[] getAllLeasedItems() {
		Item[] leasedItems = new Item[itemsIndex];
	    int count = 0;
	    for (int i = 0; i < itemsIndex; i++) {
	        if (items[i].getLeasedBy() != null) {
	            leasedItems[count++] = items[i];
	        }
	    }
	    // Create a new array with the exact size and copy elements
	    Item[] result = new Item[count];
	    for (int i = 0; i < count; i++) {
	        result[i] = leasedItems[i];
	    }
	    return result;
	}
}
