package Exceptions;

/**
 * Exception thrown when there are excess fields in a movie record.
 */

public class ExcessFieldsException extends SyntaxErrorException {
	
	/**
     * Constructs a new ExcessFieldsException with the specified detail message.
     * @param message The detail message.
     */
	
    public ExcessFieldsException(String message) {
        super(message);
    }

}
