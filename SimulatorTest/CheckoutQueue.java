package SimulatorTest;

import java.util.Queue;

public class CheckoutQueue {
	private Queue<Customer> queue;

	// Constructor
	public CheckoutQueue(Queue<Customer> queue) {
		this.queue = queue;
	}

	// Add a customer to the queue
	public void addCustomer(Customer customer) {
		queue.add(customer);
	}

	// Remove the next customer from the queue
	public Customer removeCustomer() {
		return queue.remove();
	}

	// Get the size of the queue
	public int getSize() {
		return queue.size();
	}

	// Check if the queue is empty
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	// Get the next customer in the queue (without removing them)
	public Customer getNextCustomer() {
		return queue.peek();
	}
}
