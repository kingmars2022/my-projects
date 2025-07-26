package a3;

import java.util.Arrays;

public class UniqueWords {
	
	private String[] words;
    private int size;

    // Constructor initializes the words array with an initial size of 4 and sets size to 0
    public UniqueWords() {
        this.words = new String[4];
        this.size = 0;
    }

    // Adds a word to the array if it is not already present
    public void addWord(String word) {
        if (word == null || word.trim().isEmpty()) {
            return; // Ignore null or empty words
        }

        //word = word.toLowerCase(); // Convert the word to lowercase for case-insensitive comparison

        if (contains(word)) {
            return; // Ignore the word if it is already in the array
        }

        if (size == words.length) {
            resize(); // Resize the array if it is full
        }

        words[size] = word; // Add the new word to the array
        size++; // Increment the size
    }

    // Prints all unique words in the array in the order of first occurrence
    public void printWords() {
        for (int i = 0; i < size; i++) {
            System.out.print(words[i]);
            if (i < size - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }

    // Returns the current size of the words array
    public int maxSize() {
        return words.length;
    }

    // Returns the number of unique words in the array
    public int size() {
        return size;
    }

    // Helper method to check if a word is already present in the array
    private boolean contains(String word) {
        for (int i = 0; i < size; i++) {
            if (words[i].equalsIgnoreCase(word)) {
                return true; // Return true if the word is found
            }
        }
        return false; // Return false if the word is not found
    }

    // Helper method to resize the array when it is full
    private void resize() {
        words = Arrays.copyOf(words, words.length * 2); // Create a new array with twice the size and copy the old values
    }
}


