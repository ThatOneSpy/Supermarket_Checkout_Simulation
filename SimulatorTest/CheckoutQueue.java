package SimulatorTest;

import java.util.LinkedList;
import java.util.Queue;

public class CheckoutQueue {
	private LinkedList<Customer> queue;

	public CheckoutQueue() {
		queue = new LinkedList<>();
	}

	public void addCustomer(Customer customer) {
		queue.add(customer);
	}

	public Customer removeFirstCustomer() {
		return queue.poll();
	}
	
	public Customer getFirstCustomer() {
		return queue.peek();
	}

	public boolean isEmpty() {
		return queue.isEmpty();
	}

	public int size() {
		return queue.size();
	}
	
}
