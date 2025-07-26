package Exceptions;

public class BadSemanticException extends Exception {
    /**
     * Constructs a new BadSemanticException with the specified detail message.
     * @param message The detail message.
     */
    private String localMessage;

    public BadSemanticException(String message) {
        super("Semantic Error : " + message);
        localMessage = message;
        //throw new RuntimeException();
    }

    public String getLocalMessage(){
        return localMessage;
    }

    public BadSemanticException() {

    }

}

