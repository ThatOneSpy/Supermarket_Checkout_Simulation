package selfcheckstart;

import java.util.NoSuchElementException;

public class Queue<E> {
	private LinkedList<Customer> queueList;

	// constructor, empty
	public Queue() {
		queueList = new LinkedList<Customer>();
	}

	// enqueue
	public void enqueue(Customer cus) {
		queueList.add(cus);
	}

	// dequeue
	public boolean dequeue(Customer c) throws NoSuchElementException {
		System.out.println("Customer " + c.getCustomerId() + " has been removed.");
		queueList.remove(c);

		if (queueList.isEmpty()) {
			return true;
		}

		Customer b = queueList.whoIsInFront();
		System.out.println("Customer " + b.getCustomerId() + " is now at the front of the queue.");

		return false;
	}

	// isEmpty
	public boolean isEmpty() {
		return queueList.isEmpty();
	}

	// Determines if the Queue is open, meaning it could have one person being
	// checked out or no one
	public boolean isOpen() {
		if (isEmpty()) {
			return true;
		} else {
			if (queueList.hasOneOpen()) {
				return true;
			} else {
				return false;
			}
		}
	}

	public Customer peekFirst() throws NoSuchElementException {
		Customer a = queueList.whoIsInFront();
		return a;
	}

	public Customer peekPrevious() throws NoSuchElementException {
		Customer c = queueList.remove(size() - 2);
		return c;
	}

	public Customer peekLast() throws NoSuchElementException {
		Customer b = queueList.whoIsLast();
		return b;
	}

	public int size() {
		return queueList.size();
	}

}
