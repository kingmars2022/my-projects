package Exceptions;

/**
 * Exception thrown when the name of a person associated with a movie is invalid.
 */

public class BadNameException extends BadSemanticException{

	/**
     * Constructs a new BadNameException with the specified detail message.
     * @param message The detail message.
     */
	
    public BadNameException(String message) {
        super(message);
    }
}
