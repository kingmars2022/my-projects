import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a node in a doubly linked list where each node contains a Vocab object.
 * It is designed to be used in a linked list structure for managing vocabularies.
 */
public class VocabNode implements Serializable{

	// The Vocab object stored in this node
	Vocab vocab;
	
	// Reference to the previous node in the doubly linked list
    VocabNode prev;
    
    // Reference to the next node in the doubly linked list
    VocabNode next;
    
    /**
     * Constructs a new VocabNode with the specified Vocab object.
     * Initially, both the previous and next nodes are set to null.
     *
     * @param vocab The Vocab object to be stored in this node.
     */
    public VocabNode(Vocab vocab) {
    	this.vocab = vocab;
        this.prev = null;
        this.next = null;
    }

    /**
     * Gets the Vocab object stored in this node.
     *
     * @return The Vocab object in this node.
     */
	public Vocab getVocab() {
		return vocab;
	}

	/**
     * Sets the Vocab object for this node.
     *
     * @param vocab The new Vocab object to be stored in this node.
     */
	public void setVocab(Vocab vocab) {
		this.vocab = vocab;
	}

	/**
     * Gets the previous node in the doubly linked list.
     *
     * @return The previous VocabNode in the list, or null if this is the first node.
     */
	public VocabNode getPrev() {
		return prev;
	}

	/**
     * Sets the previous node in the doubly linked list.
     *
     * @param prev The VocabNode to be set as the previous node.
     */
	public void setPrev(VocabNode prev) {
		this.prev = prev;
	}

	/**
     * Gets the next node in the doubly linked list.
     *
     * @return The next VocabNode in the list, or null if this is the last node.
     */
	public VocabNode getNext() {
		return next;
	}

	/**
     * Sets the next node in the doubly linked list.
     *
     * @param next The VocabNode to be set as the next node.
     */
	public void setNext(VocabNode next) {
		this.next = next;
	}
    
}
