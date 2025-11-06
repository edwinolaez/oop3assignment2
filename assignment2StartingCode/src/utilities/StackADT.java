package utilities;

/**
 * <p>
 * 
 * </p>
 * 
 * @param 
 */
public interface StackADT<E> {
	
	/**
	 * 
	 * 
	 * @param 
	 * @throws 
	 * @postcondition 
	 */
	public void push(E toAdd) throws NullPointerException;

	/**
	 * 
	 * 
	 * @return 
	 * @throws 
	 * @postcondition 
	 */
	public E pop() throws InvalidCounterException;

	/**
	 * 
	 * 
	 * @return 
	 * @throws 
	 * @postcondition
	 */
	public E peek() throws InvalidCounterException;

	/**
	 * 
	 * 
	 * @return 
	 */
	public boolean isEmpty();

	/**
	 * 
	 * 
	 * @return 
	 */
	public int size();

	/**
	 * 
	 * 
	 * @postcondition 
	 */
	public void clear();
}
