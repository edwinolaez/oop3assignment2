package implementations;

import java.util.NoSuchElementException;
import utilities.Iterator;
import utilities.ListADT;

public class MyArrayList<E> implements ListADT<E>
{
	//constants
	private final int DEFAULT_CAPACITY = 5;
	private final int MULTIPLIER = 2;
	
	//attributes
	private E[] array;
	private int size;
	
	@SuppressWarnings("unchecked")
	public MyArrayList() 
	{
		array = (E[]) new Object[DEFAULT_CAPACITY];
	}
	@Override
	public int size()
	{	
		return size;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void clear()
	{
		size = 0;
		array = (E[]) new Object[DEFAULT_CAPACITY];
	}

	@Override
	public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException
	{
		if(toAdd == null)
			throw new NullPointerException();
		if(index < 0 || index > size)
			throw new IndexOutOfBoundsException();
		ensureCapacity();
		if(index == size)
			return add(toAdd);
		shiftToRight(index);
		array[index] = toAdd;
		size++;
		return false;
	}

	private void shiftToRight(int index)
	{
		for(int i = size; i > index; i--)
		{
			array[i] = array[i-1];
		}
		
	}
	@Override
	public boolean add(E toAdd) throws NullPointerException
	{
		if(toAdd == null)
			throw new NullPointerException();
		ensureCapacity();
		array[size] = toAdd;
		size++;
		return true;
	}

	@SuppressWarnings("unchecked")
	private void ensureCapacity()
	{
		if(size == array.length)
		{
			E[] newArray = (E[]) new Object[array.length * MULTIPLIER];
			for(int i =0; i < array.length; i++)
			{
				newArray[i] = array[i];
			}
			array = newArray;
		}
		
	}
	@Override
	public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException
	{
		if ( toAdd == null) throw new NullPointerException();
		Iterator <? extends E> iter = toAdd.iterator();
		boolean added = false;
		while (iter.hasNext()) {
			add(iter.next());
			added = true;
		}
		return added;
	}

	@Override
	public E get(int index) throws IndexOutOfBoundsException
	{
		if (index < 0 || index >= size ) throw new IndexOutOfBoundsException();
		return array[index];
	}

	@Override
	public E remove(int index) throws IndexOutOfBoundsException
	{
		if ( index < 0 || index >= size) throw new IndexOutOfBoundsException();
		E removed = array[index];
		for (int i = index; i < size -1; i++) {
			array[i] = array[i + 1];
		}	
		array[size -1 ] = null;
		size--;
		return removed;
	}

	@Override
	public E remove(E toRemove) throws NullPointerException
	{
		if (toRemove == null) throw new NullPointerException();
		for (int i = 0; i < size; i++) {
			if (toRemove.equals(array[i])) {
				return remove(i);
			}	
		}
		return null;
	}

	@Override
	public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException
	{
		if (toChange == null) throw new NullPointerException();
		if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
		E old = array[index];
		array[index] = toChange;
		return old;
	}

	@Override
	public boolean isEmpty()
	{		
		return size == 0;
	}

	@Override
	public boolean contains(E toFind) throws NullPointerException
	{
		if (toFind == null) throw new NullPointerException();
		for(int i = 0; i < size; i++) {
			if(toFind.equals(array[i])) return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override	
	public E[] toArray(E[] toHold) throws NullPointerException
	{
		if (toHold == null) throw new NullPointerException();
		if (toHold.length < size) 
		{
			toHold = (E[]) java.lang.reflect.Array.newInstance(toHold.getClass().getComponentType(), size);
		}
		System.arraycopy(array, 0, toHold, 0, size);
		if(toHold.length > size) toHold[size] = null;
		return toHold;
	}

	@Override
	public Object[] toArray()
	{
		Object[] result = new Object[size];
		System.arraycopy(array, 0, result, 0 , size);
		return result;
	}

	@Override
	public Iterator<E> iterator()
	{		
		return new MyIterator();
	}
	private class MyIterator implements Iterator<E>
	{
		private int pos = 0;
		@Override
		public boolean hasNext() {
			return pos < size;
		}
		@Override
		public E next() throws NoSuchElementException {
			if (!hasNext()) throw new NoSuchElementException();
			return array[pos++];
		}
	}

}
