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
    public boolean contains( E toFind ) throws NullPointerException { 
        // validate if input is null before checking
        if (toFind == null) { 
            throw new NullPointerException("Cannot find a null value");
        } 

        return list.contains(toFind);
    }

    @Override 
    public Iterator<E> iterator() { 
        return list.iterator();
    }

    @Override 
    public Object[] toArray() { 
        return list.toArray();
    }

    @Override
    public int size() {
        return list.size();
    }

    
} // class
