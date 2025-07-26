package Exceptions;

public class SyntaxErrorException extends Exception {

    /**
     * Constructs a new SyntaxErrorException with the specified detail message.
     * @param message The detail message.
     */

    public SyntaxErrorException(String message) {
        super("Syntax Error : " + message);
    }
}
