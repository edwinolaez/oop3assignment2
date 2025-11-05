package utilities;

/**
 * The {@code QueueADT} interface defines the basic operations of a First In First Out (FIFO) queue.
 *
 * @param <E> the type of elements in the queue
 */
public interface QueueADT<E> {

    /**
     * Adds an element to the back of the queue
     *
     * @param element the element to add
     * @throws NullPointerException if the element is null
     * @postcondition the element is added to the back of the queue
     */
    void addToQueue(E element) throws NullPointerException;

    /**
     * Removes and returns the element at the front of the queue.
     *
     * @return the element removed from the front
     * @throws IllegalStateException if the queue is empty
     * @postcondition the front element is removed, queue size decreases by one
     */
    E removeFromQueue() throws IllegalStateException;

    /**
     * Returns the element at the front but does not remove it 
     *
     * @return the element at the front
     * @throws IllegalStateException if the queue is empty
     * @postcondition the queue remains unchanged
     */
    E checkFrontOfQueue() throws IllegalStateException;

    /**
     * Checks if the queue is empty.
     *
     * @return true if the queue contains no elements, false if queue is not empty
     */
    boolean isEmpty();

    /**
     * Returns the number of elements in the queue.
     *
     * @return the queue size
     */
    int size();
}
