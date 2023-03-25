package selfcheckstart;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Queue<E> {
	private LinkedList<Customer> queueList;
	private int size;

	// constructor, empty
	public Queue() {
		queueList = new LinkedList<Customer>();
		size = 0;
	}

	// enqueue
	public void enqueue(Customer cus) {
		queueList.add(cus);
		size++;
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

	public void dequeue(int index) {
		Customer c = queueList.remove(index);
		System.out.println("Customer " + c.getCustomerId() + " has been removed.");
		size--;
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

	// Determines who needs to dequeue, returns a list of indexes where the current
	// customer's arrival time matches or is less than the arrival time of a
	// customer already in the queue
	public void needToDequeue(double currentArrival) {
		int size = this.size;
		ArrayList<Customer> customers = queueList.toArrayList();
		for (int i = 0; i < size - 1; i++) {
			if (customers.get(i).getFinishTime() <= currentArrival)
				dequeue(customers.get(i));
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
		return size;
	}

}
