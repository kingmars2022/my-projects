package Exceptions;

/**
 * Exception thrown when the year of a movie is invalid.
 */

public class BadYearException extends BadSemanticException {

	/**
     * Constructs a new BadYearException with the specified detail message.
     * @param message The detail message.
     */
	
    public BadYearException(String message) {
        super(message);
    }

}
