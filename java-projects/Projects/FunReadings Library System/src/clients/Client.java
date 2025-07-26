package clients;

public class Client {
	
	private String id;
    private String name;
    private String phoneNumber;
    private String email;
    
    // Default Constructor
    public Client() {
    	this.id = "";
    	this.name = "";
    	this.phoneNumber = "";
    	this.email = "";
    }
    
    // Parameterized Constructor
    public Client(String id, String name, String phoneNumber, String email) {
    	this.id = id;
    	this.name = name;
    	this.phoneNumber = phoneNumber;
    	this.email = email;
    }
    
    // Copy Constructor, exception of the ID
    public Client(Client other) {
    	this.id = other.id;
    	this.name = other.name;
        this.phoneNumber = other.phoneNumber;
        this.email = other.email;
    }

    // accessors, mutators
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		
		// Simple validation example
		if (email.contains("@")) {
			this.email = email;
		}else {
			System.out.println("Invalid email format");
		}
		
	}
	
	// toString Method
	public String toString() {
		return "Client: " + 
				"\nI.D.: " + id +
				"\nName: " + name + 
				"\nPhoneNumber: " + phoneNumber +
				"\nEmail: " + email;
	}
    
    // equals Method
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Client client = (Client) obj;
		return name.equals(client.name) && phoneNumber.equals(client.phoneNumber) && email.equals(client.email);
	}
    
}
