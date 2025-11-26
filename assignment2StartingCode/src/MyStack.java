package implementations;

import java.util.EmptyStackException;
import utilities.Iterator;
import utilities.StackADT;

/**
 * MyStack is an implementation of the StackADT interface using MyArrayList
 * as the underlying data structure
 * 
 * @param <E> 
 */


public class MyStack<E> implements StackADT<E>
{
	private MyArrayList<E> list;
	
	public MyStack() {
		list = new MyArrayList<>();
	}
	
	@Override
	public void push(E toAdd) throws NullPointerException {
		if (toAdd == null) {
			throw new NullPointerException();
		}
		list.add(toAdd);
	}
	
	@Override
	public E pop() throws EmptyStackException {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		return list.remove(list.size() -1);
	}
	
	@Override
	public E peek() throws EmptyStackException {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		return list.get(list.size() -1);
	}
	
	@Override
	public void clear() {
		list.clear();
	}
	
	@Override 
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	@Override 
	public int size() {
		return list.size();
	}
	
	@Override
	public Object[] toArray() {
		Object[] arr = list.toArray();
		Object[] result = new Object[arr.length];
		for (int i =0; i < arr.length; i++) {
			result [i] = arr[arr.length -1 - i];
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public E[] toArray(E[] holder) throws NullPointerException {
		if (holder == null) {
			throw new NullPointerException();
		}
		
		E[] arr = list.toArray(holder);
		int stackSize =list.size();
		
		E[] result;
		if (holder.length < stackSize) {
			result = (E[]) java.lang.reflect.Array.newInstance(holder.getClass().getComponentType(), stackSize);
		} else {
			result = holder;
		}
		
		for (int i = 0; i < stackSize; i++) {
			result[i] = arr[stackSize -1 -i];
		}
		
		if(result.length > stackSize) {
			result[stackSize] = null;
		}
		
		return result;
	}

    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null) {
            throw new NullPointerException("Search element cannot be null");
        }
        return list.contains(toFind);
    }

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

    @Override
    public Iterator<E> iterator() {
        return new StackIterator();
    }

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
