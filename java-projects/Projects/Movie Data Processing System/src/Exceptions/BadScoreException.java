package Exceptions;

/**
 * Exception thrown when the score of a movie is invalid.
 */

public class BadScoreException extends BadSemanticException {

	/**
     * Constructs a new BadScoreException with the specified detail message.
     * @param message The detail message.
     */
	
    public BadScoreException(String message) {
        super(message);
    }
}
