package LineSimExample;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class MarketSimulation2 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		// Prompt the user for input
		System.out.print("Enter minimum arrival time between customers: ");
		int minArrivalTime = scanner.nextInt();
		System.out.print("Enter maximum arrival time between customers: ");
		int maxArrivalTime = scanner.nextInt();
		System.out.print("Enter minimum service time: ");
		int minServiceTime = scanner.nextInt();
		System.out.print("Enter maximum service time: ");
		int maxServiceTime = scanner.nextInt();
		System.out.print("Number of customers to serve: ");
		int numCustomers = scanner.nextInt();

		// Initialize the queues for the three service lines
		Queue<Cust> queue1 = new LinkedList<>();
		Queue<Cust> queue2 = new LinkedList<>();
		Queue<Cust> queue3 = new LinkedList<>();

		// Initialize the random number generator
		Random rand = new Random();

		// Initialize the time and customer counters
		int currentTime = 0;
		int customerCount = 0;

		// Run the simulation
		while (customerCount < numCustomers) {
			// Determine the arrival time of the next customer
			int arrivalTime = rand.nextInt(maxArrivalTime - minArrivalTime + 1) + minArrivalTime;

		

			// Generate a random service time for the customer
			int serviceTime = rand.nextInt(maxServiceTime - minServiceTime + 1) + minServiceTime;

			// Create a new customer and add them to the shortest queue
			Cust customer = new Cust(serviceTime);
			if (queue1.size() <= queue2.size() && queue1.size() <= queue3.size()) {
				queue1.add(customer);
			} else if (queue2.size() <= queue1.size() && queue2.size() <= queue3.size()) {
				queue2.add(customer);
			} else {
				queue3.add(customer);
			}

			// Increment the customer count
			customerCount++;

			// Serve customers in each queue
			if (!queue1.isEmpty()) {
				serveCustomer(queue1, 1, currentTime);
			}
			if (!queue2.isEmpty()) {
				serveCustomer(queue2, 2, currentTime);
			}
			if (!queue3.isEmpty()) {
				serveCustomer(queue3, 3, currentTime);
			}

			// Increment the current time
			currentTime += serviceTime;
		}
	}

	private static void serveCustomer(Queue<Cust> queue, int queueNumber, int currentTime) {
		// Serve the first customer in the queue
		Cust customer = queue.peek();
		if (customer.getServiceStartTime() == -1) {
			customer.setServiceStartTime(currentTime);
		}
		customer.setServiceTime(customer.getServiceTime() - 1);

		// Remove the customer from the queue if they have been served
		if (customer.getServiceTime() == 0) {
			queue.remove();
			System.out.println("Customer " + customer.getId() + " served from queue " + queueNumber
					+ " starting at time " + customer.getServiceStartTime() + " ending at time " + (currentTime + 1));
		}
	}
}

class Cust {
	private static int nextId = 1;

	private final int id;
	private int serviceTime;
	private int serviceStartTime;

	public Cust(int serviceTime) {
		this.id = nextId++;
		this.serviceTime = serviceTime;
		this.serviceStartTime = -1;
	}

	public int getId() {
		return id;
	}

	public int getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(int serviceTime) {
		this.serviceTime = serviceTime;
	}

	public int getServiceStartTime() {
		return serviceStartTime;
	}

	public void setServiceStartTime(int serviceStartTime) {
		this.serviceStartTime = serviceStartTime;
	}
}
