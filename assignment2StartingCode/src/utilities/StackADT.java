package utilities;

import exceptions.EmptyStackException;

/**
 * <p>
 * The <code>StackADT</code> interface defines the abstract data type for a
 * Last-In-First-Out (LIFO) stack data structure. This interface specifies
 * the standard operations that all stack implementations must provide.
 * </p>
 *
 * @param <E> The type of elements this stack holds.
 */
public interface StackADT<E> {

    /**
     * Pushes an element onto the top of this stack.
     *
     * @param toAdd The element to be pushed onto the stack.
     * @throws NullPointerException If the specified element is <code>null</code>
     *                              and the stack implementation does not support
     *                              having <code>null</code> elements.
     * @postcondition The element is added to the top of the stack and the size
     *                is increased by one.
     */
    public void push(E toAdd) throws NullPointerException;

    /**
     * Removes and returns the element at the top of this stack.
     *
     * @return The element at the top of the stack.
     * @throws EmptyStackException if the stack is empty
     * @postcondition The top element is removed from the stack and the size
     *                is decreased by one.
     */
    public E pop() throws EmptyStackException;

    /**
     * Returns the element at the top of this stack without removing it.
     *
     * @return The element at the top of the stack.
     * @throws InvalidCounterException If the stack is empty.
     * @throws EmptyStackException 
     * @postcondition The stack remains unchanged.
     */
    public E peek() throws EmptyStackException;

    /**
     * Tests if this stack is empty.
     *
     * @return <code>true</code> if the stack contains no elements,
     *         <code>false</code> otherwise.
     */
    public boolean isEmpty();

    /**
     * Returns the number of elements in this stack.
     *
     * @return The current element count in the stack.
     */
    public int size();

    /**
     * Removes all elements from this stack. The stack will be empty after
     * this call returns.
     *
     * @postcondition The stack is empty and the size is zero.
     */
    public void clear();

	/**
	 * returns an array containing all elements in the stack with the top element first.
	 *
	 * @param holder the array into which the elements are to be stored
	 * @return an array of stack elements
	 * @throws NullPointerException if holder is null
	 */
	E[] toArray(E[] holder) throws NullPointerException;

	/**
	 * checks if the stack contains the given element.
	 *
	 * @param toFind the element to search for
	 * @return true if found, false otherwise
	 * @throws NullPointerException if toFind is null
	 */
	public boolean contains(E toFind) throws NullPointerException;

	/**
	 * returns the 1-based position from the top of the stack of the element.
	 *
	 * @param toFind the element to search for
	 * @return the position from the top, or -1 if not found
	 */
	public int search(E toFind);
	
	/**
	 * returns an iterator over the elements in this stack with the top element first.
	 * 
	 * @return an iterator over the stack elements.
	 */
	public Iterator<E> iterator();

	/**
	 * returns an array containing all elements in the stack with the top element first.
	 *
	 * @return an array of stack elements
	 */
	public Object[] toArray();

	/**
	 * compares this stack to another StackADT for equality.
	 *
	 * @param that the stack to compare with
	 * @return true if stacks are equal
	 */
	public boolean equals(StackADT<E> that);

	/**
	 * placeholder method; stack never overflows in this implementation.
	 *
	 * @return false
	 */
	public boolean stackOverflow();

	
}