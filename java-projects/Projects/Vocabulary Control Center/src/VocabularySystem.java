import java.io.*;
import java.util.List;
import java.util.Scanner;

public class VocabularySystem {
    private VocabDoublyLinkedList vocabList;
    private Scanner scanner = new Scanner(System.in);

    public VocabularySystem(){
        vocabList = new VocabDoublyLinkedList();
    }

    public void loadData() {
        System.out.print("Enter the name of the input file: "); // input_topics_words.txt
        String fileName = scanner.nextLine();

        String  path= System.getProperty("user.dir")+ "/" +"src/";
        try {
            
            BufferedReader sc = new BufferedReader(new FileReader(path+fileName));

            String line;
            Vocab currentVocab = null;

            while ((line = sc.readLine()) != null) {
                if (line.startsWith("#")) {
                    String topicName = line.substring(1).trim();
                    currentVocab = new Vocab(topicName);
                    vocabList.addVocab(currentVocab);
                }else if (!line.trim().isEmpty() && currentVocab != null) {
                    currentVocab.addWord(line.trim());
                }
            }
            System.out.println("Done loading");
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Error loading data: " + e.getMessage());
        }

    }

    public void browseTopics() {
        if (vocabList.isEmpty()) {
            System.out.println("There are no topics to browse.");
        }else {
            System.out.println("\n-----------------------------");
            System.out.println("        Pick a topic");
            System.out.println("-----------------------------");
            vocabList.printTopics();
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                return;
            }

            if (choice < 1 || choice > vocabList.getSize()) {
                System.out.println("Option out of range, try again!");
                return;
            }

            Vocab chosenTopic = vocabList.getVocabByIndex(choice - 1);
            if (chosenTopic != null){
                System.out.println("Topic: \"" + chosenTopic.getTopicName() + "\"");
                List<String> words = chosenTopic.getWords();
                if (words.isEmpty()) {
                    System.out.println("No words under this topic.");
                } else {
                    final int minWidth = 20;
                    for (int i = 0; i < words.size(); i++) {
                        System.out.print(String.format("%-" + minWidth + "s", (i + 1) + ": " + words.get(i)));
                        if ((i + 1) % 4 == 0) {
                            System.out.println();
                        }
                    }
                    System.out.println();
                }
            }
        }
    }

    public void  insertNewTopicBeforeChosenTopic() {
        if (vocabList.isEmpty()) {
            System.out.println("No existing topics to insert before.");
            return;
        }
        System.out.println("\n-----------------------------");
        System.out.println("        Pick a topic");
        System.out.println("-----------------------------");
        vocabList.printTopics();
        System.out.println("0. Exit");
        System.out.println("\n-----------------------------");
        System.out.print("Enter Your Choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 0) {
            return;
        }

        System.out.print("Enter a topic name: ");
        String topicName = scanner.nextLine();
        Vocab newVocab = new Vocab(topicName);

        vocabList.insertTopicBefore(choice, newVocab);
        System.out.println("Enter words for \"" + topicName + "\" - to quit, press Enter:");
        String word;
        while (!(word = scanner.nextLine()).isEmpty()) {
            newVocab.addWord(word);
        }
    }

    public void insertNewTopicAfterChosenTopic() {
        if (vocabList.isEmpty()) {
            System.out.println("No existing topics to insert before.");
            return;
        }
        System.out.println("\n-----------------------------");
        System.out.println("        Pick a topic");
        System.out.println("-----------------------------");
        vocabList.printTopics();
        System.out.println("0. Exit");
        System.out.println("\n-----------------------------");
        System.out.print("Enter Your Choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 0) {
            return;
        }

        System.out.print("Enter a topic name: ");
        String topicName = scanner.nextLine();
        Vocab newVocab = new Vocab(topicName);

        vocabList.insertTopicAfter(choice, newVocab);
        System.out.println("Enter words for \"" + topicName + "\" - to quit, press Enter:");
        String word;
        while (!(word = scanner.nextLine()).isEmpty()) {
            newVocab.addWord(word);
        }
    }

    public void removeTopic() {
        if (vocabList.isEmpty()) {
            System.out.println("There are no topics to remove.");
            return;
        }
        System.out.println("\n-----------------------------");
        System.out.println("        Pick a topic");
        System.out.println("-----------------------------");
        vocabList.printTopics();
        System.out.println("0. Exit");
        System.out.println("\n-----------------------------");
        System.out.print("Enter Your Choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 0) {
            return;
        }

        vocabList.removeTopicByIndex(choice);
    }

    private void addWord(Vocab topic) {
        System.out.println("Type a word and press Enter, or press Enter to end input ");
        while (true) {
            String word = scanner.nextLine();
            if (word.isEmpty()) {
                break;
            }
            if (topic.containsWord(word)) {
                System.out.println("Sorry, the word: '" + word + "' is already listed.");
            } else {
                topic.addWord(word);
            }
        }
    }

    public void removeWord(Vocab topic) {
        System.out.print("Enter a word: ");
        String word = scanner.nextLine();
        if (topic.containsWord(word)) {
            topic.removeWord(word);
        } else {
            System.out.println("sorry, there is no word: " + word);
        }
    }

    public void changeWord(Vocab topic) {
        System.out.print("Type the word you want to change and press Enter:  ");
        String oldWord = scanner.nextLine();
        if (!topic.containsWord(oldWord)) {
            System.out.println("Sorry, there is no word: '" + oldWord + "' in that topic.");
            return;
        }
        System.out.print("Type the new word and press Enter: ");
        String newWord = scanner.nextLine();
        if (topic.containsWord(newWord)) {
            System.out.println("Word \"" + newWord + "\" already exists in the topic \"" + topic.getTopicName() + "\".");
            return;
        }
        topic.removeWord(oldWord);
        topic.addWord(newWord);
        System.out.println("Word \"" + oldWord + "\" changed to \"" + newWord + "\" in the topic \"" + topic.getTopicName() + "\".");
    }

    public void modifyTopic() {
        System.out.println("\n-----------------------------");
        System.out.println("     Modify Topics Menu");
        System.out.println("-----------------------------");
        vocabList.printTopics();
        System.out.println("0. Exit");

        System.out.print("Enter Your Choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 0) {
            return;
        }

        System.out.println();
        Vocab chosenTopic = vocabList.getVocabByIndex(choice - 1);
        if (chosenTopic != null) {
            System.out.println("Topic: \"" + chosenTopic.getTopicName() + "\"");
            System.out.println("a Add a word");
            System.out.println("r Remove a word");
            System.out.println("c Change a word");
            System.out.println("0. Exit");
            System.out.println();
            System.out.print("Enter Your Choice: ");
            char option = scanner.next().charAt(0);
            scanner.nextLine();

            switch (option) {
                case 'a':
                    addWord(chosenTopic);
                    break;

                case 'r':
                    removeWord(chosenTopic);
                    break;

                case 'c':
                    changeWord(chosenTopic);
                    break;

                case '0':
                    break;

                default:
                    System.out.println("Invalid option. Please choose 'a', 'r', or 'c'.");
            }
        }
    }

    public void searchTopicsForWord() {
        System.out.print("Enter a word to search for across all topics: ");
        String word = scanner.nextLine(); // This ensures that the word is captured from the user input.
        List<Vocab> foundTopics = vocabList.searchTopicsForWord(word);

        if (foundTopics.isEmpty()) {
            System.out.println("Word \"" + word + "\" not found in any topic.");
        } else {
            System.out.println("Word \"" + word + "\" found in the following topics:");
            for (Vocab topic : foundTopics) {
                System.out.println("- " + topic.getTopicName());
            }
        }
    }

    public void showAllWordsStartingWith() {
        System.out.print("Enter a letter: ");
        String input = scanner.nextLine();
        if (input.isEmpty() || input.length() > 1) {
            System.out.println("Invalid input. Please enter a single letter.");
            return;
        }
        char letter = input.charAt(0);
        if (!Character.isLetter(letter)) {
            System.out.println("Invalid input. Please enter a letter.");
            return;
        }

        boolean found = false;

        for (int i = 0; i < vocabList.getSize(); i++) {
            Vocab vocab = vocabList.getVocabByIndex(i);
            List<String> wordsStartingWith = vocab.getWordsStartingWith(letter);
            if (!wordsStartingWith.isEmpty()) {
                System.out.println("\nTopic: " + vocab.getTopicName());
                for (String word : wordsStartingWith) {
                    System.out.println("- " + word);
                }
                found = true;
            }
        }

        if (!found) {
            System.out.println("No words starting with the letter '" + letter + "' were found in any topic.");
        }
    }

    public void saveToFile() {
        System.out.print("Enter the name of the file to save to: ");
        String fileName = scanner.nextLine();
        try (PrintWriter writer = new PrintWriter(new File(fileName))) {
            VocabNode current = vocabList.getHead();
            boolean isFirstTopic = true;
            while (current != null) {
                if (!isFirstTopic) {
                    writer.println();
                } else {
                    isFirstTopic = false;
                }
                writer.println("# " + current.getVocab().getTopicName());
                for (String word : current.getVocab().getWords()) {
                    writer.println(word);
                }
                current = current.getNext();
            }
            System.out.println("Data successfully saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }
}

