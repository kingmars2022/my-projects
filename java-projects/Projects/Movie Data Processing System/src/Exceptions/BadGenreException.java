package Exceptions;

/**
 * Exception thrown when the Genre of a movie is invalid.
 */

public class BadGenreException extends BadSemanticException{
	
	/**
     * Constructs a new BadGenreException with the specified detail message.
     * @param message The detail message.
     */
	
    public BadGenreException(String message) {
        super(message);
    }

}
