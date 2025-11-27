package exceptions;

/**
 * exception thrown when attempting to access or remove an element from an empty stack.
 */
public class EmptyStackException extends Exception
{
    private static final long serialVersionUID = 1L;
    
    /**
     * constructs a new EmptyStackException with no detail message.
     */
    public EmptyStackException() {
        super();
    }
    
    /**
     * constructs a new EmptyStackException with the specified detail message.
     * 
     * @param message the detail message
     */
    public EmptyStackException(String message) {
        super(message);
    }
}
