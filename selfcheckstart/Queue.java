package selfcheckstart;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Queue<E> {
	private LinkedList<Customer> queueList;
	private int capacity;

	public int getCapacity() {
		return capacity;
	}

	// constructor, empty
	public Queue() {
		queueList = new LinkedList<Customer>();
		capacity = 0;
	}

	// enqueue
	public void enqueue(Customer cus) {
		queueList.add(cus);
		capacity++;
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

	// Determines who needs to dequeue, returns a list of indexes where the current
	// customer's arrival time matches or is less than the arrival time of a
	// customer already in the queue
	public void needToDequeue(double currentArrival) {
		int size = queueList.size();
		ArrayList<Customer> customers = queueList.toArrayList();
		for (int i = 0; i < size; i++) {
			if (customers.get(i).getFinishTime() <= currentArrival)
				dequeue(customers.get(i));
		}
	}

	public Customer peekFirst() throws NoSuchElementException {
		Customer a = queueList.whoIsInFront();
		return a;
	}

	public Customer peekLast() throws NoSuchElementException {
		Customer b = queueList.whoIsLast();
		return b;
	}

	public int size() {
		return queueList.size();
	}

	public ArrayList<Customer> toArrayList() {
		return queueList.toArrayList();
	}

}
