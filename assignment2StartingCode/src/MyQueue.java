package implementations;

import exceptions.EmptyQueueException;
import utilities.Iterator;
import utilities.QueueADT;

public class MyQueue<E> implements QueueADT<E>
{
	private MyDLL<E> list;
	
	public MyQueue() {
		list = new MyDLL<>();
	}
	
	@Override 
	public void enqueue (E toAdd) throws NullPointerException {
		if (toAdd == null) {
			throw new NullPointerException();
		}
		list.add(toAdd);
	}
	
	@Override
	public E dequeue() throws EmptyQueueException {
		if (isEmpty()) {
			throw new EmptyQueueException();
		}
		return list.remove(0);
	}
	
	@Override
	public E peek() throws EmptyQueueException {
		if (isEmpty()) {
			throw new EmptyQueueException();
		}
		return list.get(0);
	}
	
	@Override
	public void dequeueAll() {
		list.clear();
	}
	
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	@Override
	public boolean contains(E toFind) throws NullPointerException {
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
		
		for(int i=0; i<list.size();i++) {
			if (toFind.equals(list.get(i))) {
				return i + 1;
			}
		}
		return -1;
	}
	
	@Override
	public Iterator<E> iterator() {
		return list.iterator();
	}
	
	@Override
	public boolean equals(QueueADT<E> that) {
		if (that == null || this.size() != that.size()) {
			return false;
		}
		
		Iterator<E>thisIter = this.iterator();
		Iterator<E>thatIter = that.iterator();
		
		while (thisIter.hasNext() && thatIter.hasNext()) {
			E thisElem = thisIter.next();
			E thatElem = thatIter.next();
			
			if (thisElem == null && thatElem == null) {
				continue;
			}
			if (thisElem == null || thisElem.equals(thatElem)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public Object[] toArray() {
		return list.toArray();
	}
	
	@Override
	public E[] toArray(E[] holder) throws  NullPointerException {
		if (holder == null) {
			throw new NullPointerException();
		}
		return list.toArray(holder);
	}
	
	@Override
	public boolean isFull() {
		return false;
	}
	
	@Override
	public int size() {
		return list.size();
	}
}
