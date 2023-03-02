package JavaMarket;

import java.util.LinkedList;
import java.util.Queue;

public class CustomerCreator extends Customer {

	private Queue<Customer> customerQueue;

	public CustomerCreator() {
		super();
		customerQueue = new LinkedList<>();
	}

	public CustomerCreator(int arrivalTime, double serviceTime, double endTime, double waitTime) {
		super(arrivalTime, serviceTime, endTime, waitTime);

		customerQueue = new LinkedList<>();
	}

	public void Simulator() {

	}

	public int size() {
		return customerQueue.size();
	}

	public void enqueue(Customer customer) {
        customerQueue.add(customer);
    }


	public Customer frontPeek() {
        return customerQueue.peek();
    }

	public Customer rearPeek() {
        Customer[] customers = customerQueue.toArray(new Customer[0]);
        return customers[customers.length - 1];
    }
}
