package Exceptions;

/**
 * Exception thrown when the duration of a movie is invalid.
 */

public class BadDurationException extends BadSemanticException {

	/**
     * Constructs a new BadDurationException with the specified detail message.
     * @param message The detail message.
     */

    public BadDurationException(String message) {
        super(message);
    }
}
