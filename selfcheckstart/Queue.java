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
	public boolean dequeue() throws NoSuchElementException {

		Customer a = queueList.whoIsInFront();
		System.out.println("Customer " + a.getCustomerId() + " has been removed.");
		queueList.remove(a);

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

	public Customer peekSecond() throws NoSuchElementException {
		Customer c = queueList.whoIsSecond();
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
