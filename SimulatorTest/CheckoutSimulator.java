package SimulatorTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CheckoutSimulator {
	private int minArrivalTime;
	private int maxArrivalTime;
	private int minServiceTime;
	private int maxServiceTime;
	private int numCustomers;
	private List<Customer> customers;
	private CheckoutQueue queueA;
	private CheckoutQueue queueB;
	private CheckoutQueue queueC;
	private Random random;

	public CheckoutSimulator(int minArrivalTime, int maxArrivalTime, int minServiceTime, int maxServiceTime,
			int numCustomers) {
		this.minArrivalTime = minArrivalTime;
		this.maxArrivalTime = maxArrivalTime;
		this.minServiceTime = minServiceTime;
		this.maxServiceTime = maxServiceTime;
		this.numCustomers = numCustomers;
		customers = new ArrayList<>();
		queueA = new CheckoutQueue();
		queueB = new CheckoutQueue();
		queueC = new CheckoutQueue();
		random = new Random();
	}

	public void runSimulation() {
		int virtualClock = 0;
		int idleTime = 0;
		int clock = 0;
		int count = 0;
		int totalWaitTime = 0;
		int totalTurnaroundTime = 0;

		System.out.println("\nCreating Customer(s):");
		// generate random arrival and service times for each customer
		for (int i = 0; i < numCustomers; i++) {

			int arrivalTime = clock + random.nextInt(maxArrivalTime - minArrivalTime + 1) + minArrivalTime;
			int serviceTime = random.nextInt(maxServiceTime - minServiceTime + 1) + minServiceTime;
			Customer customer = new Customer(i + 1, arrivalTime, serviceTime);
			customers.add(customer);
			System.out.println(customer.toString());
			clock = arrivalTime;
		}

		// serve customers

		while (!customers.isEmpty() || !queueA.isEmpty() || !queueB.isEmpty() || !queueC.isEmpty()) {

			// check if new customers arrive
			if (!customers.isEmpty() && customers.get(0).getArrivalTime() == virtualClock) {
				
				Customer customer = customers.remove(0);

				// add customer to the smallest queue available
				if (queueA.size() <= queueB.size() && queueA.size() <= queueC.size()) {
					queueA.addCustomer(customer);

					System.out.println("Customer " + customer.getId() + " added to queue A at time " + virtualClock);

				} else if (queueB.size() <= queueC.size()) {
					queueB.addCustomer(customer);
					System.out.println("Customer " + customer.getId() + " added to queue B at time " + virtualClock);

				} else {
					queueC.addCustomer(customer);
					System.out.println("Customer " + customer.getId() + " added to queue C at time " + virtualClock);

				}

			}
			

			   // serve customers in queue A
		    if (!queueA.isEmpty() && queueA.getFirstCustomer().getFinishTime() == virtualClock) {
		        Customer customer = queueA.removeFirstCustomer();
		        idleTime += customer.getStartTime() - virtualClock - customer.getServiceTime();
		        virtualClock = customer.getFinishTime();
		        System.out.println("Customer " + customer.getId() + " removed from queue A at time " + virtualClock);
		        totalWaitTime += customer.getWaitTime();
		        totalTurnaroundTime += customer.getTurnaroundTime();
		    }

		    // serve customers in queue B
		    if (!queueB.isEmpty() && queueB.getFirstCustomer().getFinishTime() == virtualClock) {
		        Customer customer = queueB.removeFirstCustomer();
		        idleTime += customer.getStartTime() - virtualClock - customer.getServiceTime();
		        virtualClock = customer.getFinishTime();
		        System.out.println("Customer " + customer.getId() + " removed from queue B at time " + virtualClock);
		        totalWaitTime += customer.getWaitTime();
		        totalTurnaroundTime += customer.getTurnaroundTime();
		    }

		    // serve customers in queue C
		    if (!queueC.isEmpty() && queueC.getFirstCustomer().getFinishTime() == virtualClock) {
		        Customer customer = queueC.removeFirstCustomer();
		        idleTime += customer.getStartTime() - virtualClock - customer.getServiceTime();
		        virtualClock = customer.getFinishTime();
		        System.out.println("Customer " + customer.getId() + " removed from queue C at time " + virtualClock);
		        totalWaitTime += customer.getWaitTime();
		        totalTurnaroundTime += customer.getTurnaroundTime();
		    }

			// increment virtual clock
			virtualClock++;

		}

		// calculate statistics
		//loool
		for (Customer customer : customers) {
			totalWaitTime += customer.getWaitTime();
			totalTurnaroundTime += customer.getTurnaroundTime();
		}
		double avgWaitTime = (double) totalWaitTime / numCustomers;
		double avgTurnaroundTime = (double) totalTurnaroundTime / numCustomers;

		// display results
		System.out.println("Simulation results:");
		System.out.println("Number of customers served: " + numCustomers);
		System.out.println("Average waiting time: " + avgWaitTime);
		System.out.println("Average turnaround time: " + avgTurnaroundTime);
		System.out.println("Idle time: " + idleTime);
	}
}
