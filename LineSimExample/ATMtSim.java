package LineSimExample;

import java.util.*;

public class ATMtSim {
	Customer customer;
	int ATMisAvailable; // time the ATM is next available
	int numArrivals; // number of arrivals in any minute
	Queue<Customer> queue;
	// statistics
	int totalWaitingTime; // for all customers
	int numCustomersServed;

	public ATMtSim() // default constructor
	{
		ATMisAvailable = 0; // assume the ATM is available at time 0
		numArrivals = 0;
		totalWaitingTime = 0;
		numCustomersServed = 0;
		queue = new LinkedList<Customer>();
	}

	private int getArrivals()
	// generate a random integer in the range 0..9
	// if the random integer is 0,1,2,3,or 4, then no arrivals ( 50% chance)
	// if the random integer is 5,6,7, or 8, then 1 arrival (40 % chance)
	// if the random integer is 9, then 2 arrivals (10% chance)
	{
		Random rand = new Random();
		int randomInteger = rand.nextInt(10); // 0..9
		if (randomInteger <= 4) // 0..4
			return 0; // 50% chance of a single arrival
		if (randomInteger <= 8) // 5..8
			return 1; // 40% chance of a single arrival
		return 2; // 10% chance of 2 arrivals
	}

	private void displayStatistics() {
		System.out.println("Number of customers served " + numCustomersServed);
		System.out.println("Average wait is about " + totalWaitingTime / numCustomersServed + " minutes");
		System.out.println("Customers left in queue: " + queue.size());
	}

	public void simulate() {
		for (int time = 0; time < 60; time++) // for each minute
		{
			numArrivals = getArrivals(); // how many customers arrive?
			for (int i = 1; i <= numArrivals; i++) // place each arrival into the queue
				queue.add(new Customer(time));
			if (!queue.isEmpty() && ATMisAvailable <= time) {
				customer = queue.remove(); // remove the next customer from the line
				// Determine the next time that the ATM is available: current time+ service time
				ATMisAvailable = time + customer.getServiceTime();
				// how long did this customer wait?
				int timeCustomerWaited = time - customer.getArrivalTime();
				totalWaitingTime += timeCustomerWaited; // add customer's wait to total
				numCustomersServed++;
			}
		}
		
		displayStatistics();
	}

	public static void main(String[] args) {
		ATMtSim atmSim = new ATMtSim();
		atmSim.simulate();
	}
}
