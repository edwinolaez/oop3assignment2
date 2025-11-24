package implementations;

import exceptions.EmptyQueueException;
import utilities.QueueADT;
import utilities.Iterator;

public class MyQueue<E> implements QueueADT<E> {
	// instantiate the DLL
    private MyDLL<E> list;

    // constructor
    public MyQueue() { 
        list = new MyDLL<>();
    }
    
    @Override 
    public void enqueue(E toAdd) throws NullPointerException { 
        // validate if item is null before adding
        if (toAdd == null) { 
            throw new NullPointerException("Cannot add null to queue");
        }

        list.add(toAdd);

    }

    @Override 
    public E dequeue() throws EmptyQueueException {
        if (isEmpty()) { 
            throw new EmptyQueueException("Queue is empty");
        }

        return list.remove(0);
    }
    
	@Override 
	public void dequeueAll() { 
		list.clear();
	}
	
	@Override
	public E peek() throws EmptyQueueException { 
	    if (list.isEmpty()) {
	        throw new EmptyQueueException("Queue is empty");
	    }
	    return list.get(0); // return the head element but dont remove
	}
	
	@Override 
	public boolean isEmpty() { 
		return list.isEmpty(); // will return true if size is zero
	}

    @Override 
    public boolean contains( E toFind ) throws NullPointerException { 
        // validate if input is null before checking
        if (toFind == null) { 
            throw new NullPointerException("Cannot find a null value");
        } 

        return list.contains(toFind);
    }
    
	@Override 
	public int search( E toFind ) { 
		if (toFind == null) { 
			return -1; // if null, cant be found
		} 
		
		Iterator<E> it = list.iterator();
	    int position = 1;

	    while (it.hasNext()) {
	        if (it.next().equals(toFind)) {
	            return position;   // found
	        }
	        position++;
	    }
	    
		return -1; // not found
	}

    @Override 
    public Iterator<E> iterator() { 
        return list.iterator();
    }
    
	@Override 
    public boolean equals(QueueADT<E> that) { 
		// return false if empty or if the size of the two queues are not the same
		if (that == null || this.size() != that.size()) { 
			return false;
		}
		
		Iterator<E> iterator1 = this.iterator();
		Iterator<E> iterator2 = that.iterator();
		
		while (iterator1.hasNext() && iterator2.hasNext()) { 
			if(!iterator1.next().equals(iterator2.next())) { 
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
    public E[] toArray(E[] holder) throws NullPointerException { 
		if (holder == null) {
	        throw new NullPointerException("The holder array cannot be null.");
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

    
} // class
