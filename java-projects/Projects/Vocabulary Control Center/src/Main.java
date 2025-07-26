import java.util.Scanner;

/**
 * The Main class serves as the entry point for the Vocabulary Control Center application.
 * It provides a menu-driven interface for managing vocabulary topics.
 */
public class Main {

	/**
     * The main method of the application.
     * It provides a loop for user interaction until the user chooses to exit.
     * 
     * @param args Command-line arguments (not used in this application).
     */
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
        int choice;
        VocabularySystem vocabSystem = new VocabularySystem();

        while (true) {
        	
        	// Display the main menu options to the user
        	displayMainMenu();
        	
        	// Reading the user's choice from the menu
            choice = scanner.nextInt();
            scanner.nextLine();

            // Handling the user's choice based on the menu selection
            switch (choice) {
                case 1:
                    vocabSystem.browseTopics();
                    break;
                case 2:
                	vocabSystem.insertNewTopicBeforeChosenTopic();
                    break;
                case 3:
                	vocabSystem.insertNewTopicAfterChosenTopic();
                    break;
                case 4:
                	vocabSystem.removeTopic();
                    break;
                case 5:
                	vocabSystem.modifyTopic();
                    break;
                case 6:
                	vocabSystem.searchTopicsForWord();
                    break;
                case 7:
                	vocabSystem.loadData();
                    break;
                case 8:
                	vocabSystem.showAllWordsStartingWith();
                    break;
                case 9:
                	vocabSystem.saveToFile();
                    break;
                case 0:
                   
                    System.out.println("Exiting program...");
                    return; // Exiting the main method, which ends the program
                    
                	default:
                		System.out.println("Invalid choice. Please try again.");
            }
        }

	}
	
	/**
     * Displays the main menu options for the application.
     * Lists all available actions the user can perform.
     */
	private static void displayMainMenu() {
		System.out.println("\n-----------------------------");
        System.out.println("  Vocabulary Control Center");
        System.out.println("-----------------------------");
        System.out.println("1. browse a topic");
        System.out.println("2. insert a new topic before another one");
        System.out.println("3. insert a new topic after another one");
        System.out.println("4. remove a topic");
        System.out.println("5. modify a topic");
        System.out.println("6. search topics for a word");
        System.out.println("7. load from a file");
        System.out.println("8. show all words starting with a given letter");
        System.out.println("9. save to file");
        System.out.println("0. exit");
        System.out.println("-----------------------------\n");
        System.out.print("Enter your choice: ");
	}

}
