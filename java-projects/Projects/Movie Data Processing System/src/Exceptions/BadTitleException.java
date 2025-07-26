package Exceptions;

/**
 * Exception thrown when the title of a movie is invalid.
 */

public class BadTitleException extends BadSemanticException {

	/**
     * Constructs a new BadTitleException with the specified detail message.
     * @param message The detail message.
     */
	
    public BadTitleException(String message) {
        super(message);
    }
    
}
