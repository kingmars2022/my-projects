package Exceptions;

/**
 * Exception thrown when quotes are missing in a quoted field of a movie record.
 */

public class MissingQuotesException extends SyntaxErrorException{

	/**
     * Constructs a new MissingQuotesException with the specified detail message.
     * @param message The detail message.
     */
	
    public MissingQuotesException(String message) {
        super(message);
    }

}
