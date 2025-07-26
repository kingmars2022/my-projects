import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Vocab class represents a vocabulary list associated with a specific topic.
 * It allows for storing, modifying, and querying words in this list.
 */
public class Vocab implements Serializable{
	
	// Name of the topic associated with this vocabulary
	private String topicName;
	
	// List to store words
    private List<String> words;

    /**
     * Constructor to initialize the Vocab with a topic name.
     * 
     * @param topicName The name of the topic.
     */
    public Vocab(String topicName) {
        this.topicName = topicName;
        this.words = new ArrayList<>();
    }

    /**
     * Gets the topic name.
     * 
     * @return The name of the topic.
     */
	public String getTopicName() {
		return topicName;
	}

	/**
     * Sets the topic name.
     * 
     * @param topicName The new name of the topic.
     */
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	/**
     * Gets the list of words.
     * 
     * @return The list of words in the vocabulary.
     */
	public List<String> getWords() {
		return words;
	}

	/**
     * Sets the list of words.
     * 
     * @param words The new list of words for the vocabulary.
     */
	public void setWords(List<String> words) {
		this.words = words;
	}
	
	/**
     * Adds a word to the vocabulary if it's not already present.
     * 
     * @param word The word to be added.
     */
	public void addWord(String word) {
        if (!words.contains(word)) {
            words.add(word);
        }
    }
	
	/**
     * Removes a word from the vocabulary.
     * 
     * @param word The word to be removed.
     * @return true if the word was successfully removed, false otherwise.
     */
	public boolean removeWord(String word){
        return words.remove(word);
    }
	
	/**
     * Checks if a word is in the vocabulary.
     * 
     * @param word The word to check for.
     * @return true if the word exists in the vocabulary, false otherwise.
     */
	public boolean containsWord(String word) {
        return words.contains(word);
    }
	
	/**
     * Retrieves a list of all words starting with a specific letter.
     * 
     * @param letter The starting letter.
     * @return A list of words starting with the given letter.
     */
	public List<String> getWordsStartingWith(char letter){
		List<String> filteredWords = new ArrayList<>();
		for (String word : this.words) {
			 if (!word.isEmpty() && Character.toLowerCase(word.charAt(0)) == Character.toLowerCase(letter)) {
				 filteredWords.add(word);
			 }
		}
		return filteredWords;
	}
}
