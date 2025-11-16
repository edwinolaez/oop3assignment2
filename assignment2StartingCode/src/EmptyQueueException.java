package exceptions;

/**
 * Exception thrown when attempting to access or remove an element from an empty queue.
 */

public class EmptyQueueException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new EmptyQueueException with no detail message.
	 */
	public EmptyQueueException() {
		super();
	}
	
	/**
	 * Constructs a new EmptyQueueException with the specified detail message.
	 * 
	 * @param
	 */
	public EmptyQueueException(String message) {
		super(message);
	}
}
