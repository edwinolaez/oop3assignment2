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
	public boolean contains (E toFind) throws NullPointerException {
		if (toFind == null) {
			throw new NullPointerException();
		}
		return list.contains(toFind);
	}
	
	@Override 
	public int search(E toFind) {
		if (toFind == null) {
			return -1;
		}
		
		for (int i= list.size() -1; i >= 0; i--) {
			if (toFind.equals(list.get(i))) {
				return list.size() -i;
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
		
		Iterator<E> thisIter = this.iterator();
		Iterator<E> thatIter = that.iterator();
		
		while (thisIter.hasNext() && thatIter.hasNext()) {
			E thisElem = thisIter.next();
			E thatElem = thatIter.next();
			
			if(thisElem == null && thatIter.hasNext()) {
				continue;
			}
			
			if (thisElem == null || !thisElem.equals(thatElem) ) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean stackOverflow() {
		return false;
	}
	
	private class StackIterator implements Iterator<E> {
		private int position;
		
		public StackIterator() {
			position = list.size() -1;
		}
		
		@Override
		public boolean hasNext() {
			return position >= 0;
		}
	
		
		@Override
		public E next() throws java.util.NoSuchElementException {
			if (!hasNext()) {
				throw new java.util.NoSuchElementException();
			}
			return list.get(position--);
		}
	}
} 
