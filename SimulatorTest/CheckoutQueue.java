package SimulatorTest;

import java.util.LinkedList;
import java.util.Queue;

public class CheckoutQueue {
	private Queue<Customer> queue;

	public CheckoutQueue() {
		queue = new LinkedList<>();
	}

	public void addCustomer(Customer customer) {
		queue.offer(customer);
	}

	public Customer getNextCustomer() {
		return queue.poll();
	}

	public boolean isEmpty() {
		return queue.isEmpty();
	}

	public int size() {
		return queue.size();
	}
}
