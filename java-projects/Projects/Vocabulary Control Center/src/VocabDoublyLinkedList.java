import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A doubly linked list implementation to manage Vocab objects.
 */
public class VocabDoublyLinkedList implements Serializable {

	// Points to the first node of the list
	private VocabNode head;
	
	// Points to the last node of the list
    private VocabNode tail;
    
    // Tracks the number of elements in the list
    private int size;
    
    /**
     * Constructor for creating an empty VocabDoublyLinkedList.
     */
    public VocabDoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    
    // Getters and Setters for head, tail, and size
	public VocabNode getHead() {
		return this.head;
	}
	public void setHead(VocabNode head) {
		this.head = head;
	}
	public VocabNode getTail() {
		return this.tail;
	}
	public void setTail(VocabNode tail) {
		this.tail = tail;
	}
	public int getSize() {
		return this.size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	/**
     * Adds a new Vocab object to the end of the list.
     * 
     * @param vocab The Vocab object to add.
     */
	public void addVocab(Vocab vocab) {
        VocabNode newNode = new VocabNode(vocab);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }
        // Increase the size of the list
        size++;
    }
	
	/**
     * Prints all topics in the list.
     */
	public void printTopics() {
        VocabNode current = head;
        int index = 1;
        while (current != null) {
            System.out.println(index + ". " + current.getVocab().getTopicName());
            current = current.getNext();
            index++;
        }
    }
	
	/**
     * Checks if the list is empty.
     * 
     * @return true if the list is empty, false otherwise.
     */
	public boolean isEmpty(){
        return head == null;
    }
	
	/**
     * Retrieves the Vocab object at a specific index.
     * 
     * @param index The index of the Vocab object.
     * @return The Vocab object at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
	public Vocab getVocabByIndex(int index) {
    
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        VocabNode current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.vocab;
    }
    
	/**
     * Inserts a new topic before a specified index.
     * 
     * @param index The index before which the new topic will be inserted.
     * @param newVocab The new Vocab object to insert.
     */
	public void insertTopicBefore(int index, Vocab newVocab) {
        VocabNode newNode = new VocabNode(newVocab);
        if (head == null || index == 1) {
            newNode.next = head;
            if (head != null) {
                head.prev = newNode;
            }
            head = newNode;
            if (tail == null) {
                tail = newNode;
            }
            return;
        }
        VocabNode current = head;
        int count = 1;
        while (current != null && count < index - 1) {
            current = current.next;
            count++;
        }
        if (current == null || count != index - 1) {
            System.out.println("Invalid index.");
            return;
        }
        
        newNode.next = current.next;
        if (current.next != null) {
            current.next.prev = newNode;
        }
        current.next = newNode;
        newNode.prev = current;

        if (newNode.next == null) {
            tail = newNode;
        }
	
	}
	
	/**
     * Inserts a new topic after a specified index.
     * 
     * @param index The index after which the new topic will be inserted.
     * @param newVocab The new Vocab object to insert.
     */
	public void insertTopicAfter(int index, Vocab newVocab) {
        VocabNode newNode = new VocabNode(newVocab);
        VocabNode current = head;

        int count = 1;
        while (current != null && count < index) {
            current = current.getNext();
            count++;
        }

        if (current == null) {
            System.out.println("Invalid choice. Cannot insert topic.");
            return;
        }

        newNode.setNext(current.getNext());
        newNode.setPrev(current);
        current.setNext(newNode);

        if (newNode.getNext() != null) {
            newNode.getNext().setPrev(newNode);
        } else {
            tail = newNode;
        }
    }
	
	/**
     * Removes a topic at a specified index.
     * 
     * @param index The index of the topic to remove.
     * @return true if the topic is successfully removed, false otherwise.
     */
	public boolean removeTopicByIndex(int index) {
		if (index < 1 || index > size) {
            return false;
        }

        VocabNode current = head;
        int currentIndex = 1;

        while (current != null && currentIndex < index) {
            current = current.getNext();
            currentIndex++;
        }

        if (current == null && currentIndex > index) {
            return false;
        }

        if (current == head) {
            head = current.getNext();
            if (head != null) {
                head.setPrev(null);
            }
            size--;
        } else {
            current.getPrev().setNext(current.getNext());
            if (current.getNext() != null) {
                current.getNext().setPrev(current.getPrev());
            } else {
                tail = current.getPrev();
            }
        }
        return true;
    }
	
	/**
     * Searches for topics that contain a specific word.
     * 
     * @param word The word to search for.
     * @return A list of Vocab objects that contain the specified word.
     */
	public List<Vocab> searchTopicsForWord(String word) {
        List<Vocab> foundTopics = new ArrayList<>();
        VocabNode current = head;

        while (current != null) {
            if (current.getVocab().containsWord(word)) {
                foundTopics.add(current.getVocab());
            }
            current = current.getNext();
        }

        return foundTopics;
    }
}
