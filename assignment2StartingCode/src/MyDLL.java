package implementations;

import java.util.NoSuchElementException;
import utilities.Iterator;
import utilities.ListADT;

public class MyDLL<E> implements ListADT<E>
{	
	private static class MyDLLNode<E> {
		E element;
		MyDLLNode<E> next;
		MyDLLNode<E> prev;	
		MyDLLNode(E element) {
			this.element = element;
		}
	}
	
	private MyDLLNode<E> head;
	private MyDLLNode<E> tail;
	private int size;
	
	public MyDLL() {
		head = null;
		tail = null;
		size = 0;
	}
	
	@Override
	public int size()
	{		
		return size;
	}

	@Override
	public void clear()
	{
		head = null;
		tail = null;
		size = 0;
	}

	@Override
	public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException
	{
		if (toAdd == null) throw new NullPointerException();
		if (index < 0 || index > size) throw new IndexOutOfBoundsException();
		MyDLLNode<E> newNode = new MyDLLNode<>(toAdd);
		if (index == 0) {
			if (isEmpty()) {
				head = tail = newNode;	
			} else {
				newNode.next = head;
				head.prev = newNode;
				head = newNode;
			}		
		} else if (index == size) {
			tail.next = newNode;
			newNode.prev = tail;
			tail = newNode;
		} else {
			MyDLLNode<E> current = getNode(index);
			newNode.prev = current.prev;
			newNode.next = current;
			current.prev.next = newNode;
			current.prev = newNode;
		}
		size++;
		return true;
	}

	@Override
	public boolean add(E toAdd) throws NullPointerException
	{
		if (toAdd == null) throw new NullPointerException();
		MyDLLNode<E> newNode = new MyDLLNode<>(toAdd);
		if (isEmpty()) {
			head = tail = newNode;
		} else {
			tail.next = newNode;
			newNode.prev = tail;
			tail = newNode;
		}
		size++;
		return true;
	}

	@Override
	public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException
	{
		if (toAdd ==  null) throw new NullPointerException();
		Iterator<? extends E> iter = toAdd.iterator();
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
		return getNode(index).element;
	}

	private MyDLLNode<E> getNode(int index)
	{
		if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
		MyDLLNode<E> current;
		if (index < size / 2) {
			current = head;
			for (int i = 0; i < index; i++) {
				current = current.next;
			}
		} else {
			current = tail;
			for (int i = size -1; i > index; i--) {
				current = current.prev;
			}
		}
		return current;
	}

	@Override
	public E remove(int index) throws IndexOutOfBoundsException
	{
		MyDLLNode<E> node = getNode(index);
		if (node.prev != null) {
			node.prev.next = node.next;
		} else {
			head = node.next;
		}
		if (node.next != null) {
			node.next.prev = node.prev;
		} else {
			tail = node.prev;
		}
		size--;
		return node.element;
	}

	@Override
	public E remove(E toRemove) throws NullPointerException
	{
		if (toRemove == null) throw new NullPointerException();
		MyDLLNode<E> current = head;
		int index = 0;
		while (current != null) {
			if (toRemove.equals(current.element)) {
				return remove(index);
			}
			current = current.next;
			index++;
		}
		return null;
	}

	@Override
	public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException
	{
		if (toChange == null) throw new NullPointerException();
		MyDLLNode<E> node = getNode(index);
		E old = node.element;
		node.element = toChange;
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
		MyDLLNode<E> node = head;
		while (node !=null) {
			if (toFind.equals(node.element)) {
				return true;
			}
			node = node.next;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E[] toArray(E[] toHold) throws NullPointerException
	{
		if (toHold == null) throw new NullPointerException();
		if (toHold.length < size) {
			toHold = (E[])
		java.lang.reflect.Array.newInstance(toHold.getClass().getComponentType(), size);
		}
		MyDLLNode<E> current = head;
		for (int i = 0; i < size; i++) {
			toHold[i] = current.element;
			current = current.next;
		}
		if (toHold.length > size) {
			toHold[size] = null;
		}
		return toHold;
	}

	@Override
	public Object[] toArray()	{
		Object[] result = new Object[size];
		MyDLLNode<E> current = head;
		for (int i = 0; i < size; i++) {
			result[i] = current.element;
			current = current.next;
		}
		return result;
	}
	

	@Override
	public Iterator<E> iterator(){
		return new MyDLLIterator();
	}
	
	private class MyDLLIterator implements Iterator<E> {
		private MyDLLNode<E> current = head;
		
		@Override
		public boolean hasNext() {
			return current != null;
		}
		
		@Override
		public E next() throws NoSuchElementException {
			if (!hasNext()) throw new NoSuchElementException();
		E element = current.element;
		current = current.next;
		return element;
		}
	}
}
