package Exceptions;

/**
 * Exception thrown when the rating of a movie is invalid.
 */

public class BadRatingException extends BadSemanticException {
	
	/**
     * Constructs a new BadRatingException with the specified detail message.
     * @param message The detail message.
     */
	
    public BadRatingException(String message) {
        super(message);
    }

}
