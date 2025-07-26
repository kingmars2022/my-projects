package Exceptions;

/**
 * Exception thrown when there are missing fields in a movie record.
 */

public class MissingFieldsException extends SyntaxErrorException{

	/**
     * Constructs a new MissingFieldsException with the specified detail message.
     * @param message The detail message.
     */
    public MissingFieldsException(String message) {
        super(message);
    }
    
}
