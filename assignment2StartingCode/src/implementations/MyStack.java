package implementations;

import exceptions.EmptyStackException;
import utilities.Iterator;
import utilities.StackADT;

/**
 * MyStack is an implementation of the StackADT interface using MyArrayList
 * as the underlying data structure
 *
 * @param <E> the type of elements held in this stack
 */	


public class MyStack<E> implements StackADT<E>
{
    // underlying list to store stack elements
	private MyArrayList<E> list;

	
	/**
     * constructor initializes the underlying MyArrayList
     */
    public MyStack() {
        list = new MyArrayList<>();
    }
    
    /**
     * pushes an element onto the top of this stack.
     *
     * @param toAdd the element to add
     * @throws NullPointerException if the element is null
     */
    @Override
    public void push(E toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException();
        }
        list.add(toAdd);
    }
    
    /**
     * removes and returns top element of the stack.
     *
     * @return the element at the top of the stack
     * @throws EmptyStackException if the stack is empty
     */
    @Override
    public E pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException("Stack is empty");
        }
        return list.remove(list.size() - 1);
    }
    
    /**
     * returns top element without removing it.
     *
     * @return the element at the top of the stack
     * @throws EmptyStackException if the stack is empty
     */
    @Override
    public E peek() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException("Stack is empty");
        }
        return list.get(list.size() - 1);
    }


    /**
     * removes all elements from the stack.
     */
    @Override
    public void clear() {
        list.clear();
    }

    /**
     * checks if stack is empty.
     *
     * @return true if the stack has no elements, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
    
    /**
     * returns number of elements in the stack.
     *
     * @return the stack size
     */
    @Override
    public int size() {
        return list.size();
    }

    /**
     * returns an array containing all elements in the stack with the top element first.
     *
     * @return an array of stack elements
     */
    @Override
    public Object[] toArray() {
        Object[] arr = list.toArray();
        Object[] result = new Object[arr.length];
        for (int i =0; i < arr.length; i++) {
            result [i] = arr[arr.length -1 - i];
        }
        return result;
    }
    
    /**
     * returns an array containing all elements in the stack with the top element first.
     *
     * @param holder the array into which the elements are to be stored
     * @return an array of stack elements
     * @throws NullPointerException if holder is null
     */
    @SuppressWarnings("unchecked")
    @Override
    public E[] toArray(E[] holder) throws NullPointerException {
        if (holder == null) {
            throw new NullPointerException();
        }

        E[] arr = list.toArray(holder);
        int stackSize =list.size();

        E[] result;
        
        // create new array if holder is too small
        if (holder.length < stackSize) {
            result = (E[]) java.lang.reflect.Array.newInstance(holder.getClass().getComponentType(), stackSize);
        } else {
            result = holder;
        }
        
        // copy elements in reverse order for stack
        for (int i = 0; i < stackSize; i++) {
            result[i] = arr[stackSize -1 -i];
        }

        if(result.length > stackSize) {
            result[stackSize] = null;
        }

        return result;
    }
    
    /**
     * checks if the stack contains the given element.
     *
     * @param toFind the element to search for
     * @return true if found, false otherwise
     * @throws NullPointerException if toFind is null
     */
    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null) {
            throw new NullPointerException("Search element cannot be null");
        }
        return list.contains(toFind);
    }
    
    /**
     * returns the 1-based position from the top of the stack of the element.
     *
     * @param toFind the element to search for
     * @return the position from the top, or -1 if not found
     */
    @Override
    public int search(E toFind) {
        if (toFind == null) {
            return -1;
        }

        int stackSize = list.size();
        for (int i = stackSize - 1; i >= 0; i--) {
            if (toFind.equals(list.get(i))) {
                return stackSize - i;
            }
        }
        return -1;
    }
    
    /**
     * returns an iterator for the stack (top element first).
     *
     * @return a stack iterator
     */
    @Override
    public Iterator<E> iterator() {
        return new StackIterator();
    }
    
    /**
     * compares this stack to another StackADT for equality.
     *
     * @param that the stack to compare with
     * @return true if stacks are equal
     */
    @Override
    public boolean equals(StackADT<E> that) {
        if (that == null || this.size() != that.size()) {
            return false;
        }

        Iterator<E> currentIter = this.iterator();
        Iterator<E> otherIter = that.iterator();

        while (currentIter.hasNext() && otherIter.hasNext()) {
            E currentElement = currentIter.next();
            E otherElement = otherIter.next();

            if (currentElement == null && otherIter.hasNext()) {
                continue;
            }

            if (currentElement == null || !currentElement.equals(otherElement)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * placeholder method; stack never overflows in this implementation.
     *
     * @return false
     */
    @Override
    public boolean stackOverflow() {
        return false;
    }

    /**
     * Iterator for traversing stack elements from top to bottom.
     */
    private class StackIterator implements Iterator<E> {
        private int currentPosition;

        public StackIterator() {
            currentPosition = list.size() - 1;
        }

        @Override
        public boolean hasNext() {
            return currentPosition >= 0;
        }

        @Override
        public E next() throws java.util.NoSuchElementException {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            return list.get(currentPosition--);
        }
    }
}
