package SimulatorTest;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import java.util.*;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class Simulator {

	private int minArrivalTime;
	private int maxArrivalTime;
	private int minServiceTime;
	private int maxServiceTime;
	private int numCustomers;

	private Queue<Customer> queueA;
	private Queue<Customer> queueB;
	private Queue<Customer> queueC;

	private int clockTime;
	private int idleTime;

	private int totalWaitingTime;
	private int totalTurnaroundTime;

	private Random random;

	public Simulator(int minArrivalTime, int maxArrivalTime, int minServiceTime, int maxServiceTime, int numCustomers) {
		this.minArrivalTime = minArrivalTime;
		this.maxArrivalTime = maxArrivalTime;
		this.minServiceTime = minServiceTime;
		this.maxServiceTime = maxServiceTime;
		this.numCustomers = numCustomers;

		queueA = new LinkedList<Customer>();
		queueB = new LinkedList<Customer>();
		queueC = new LinkedList<Customer>();

		this.clockTime = 0;
		this.idleTime = 0;

		this.totalWaitingTime = 0;
		this.totalTurnaroundTime = 0;

		this.random = new Random();
	}

	public void run() {
		for (int i = 0; i < numCustomers; i++) {
			// Generate a new customer and add them to the appropriate queue
			Customer customer = generateCustomer();
			if (queueA.size() <= queueB.size() && queueA.size() <= queueC.size()) {

				queueA.offer(customer);

			} else if (queueB.size() <= queueC.size()) {

				queueB.offer(customer);

			} else {

				queueC.offer(customer);

			}

			// Check if there are any idle queues, and if so, start serving the next
			// customer in line
			if (queueA.isEmpty() && queueB.isEmpty() && queueC.isEmpty()) {
				
				idleTime++;
				
			} else {
				
				if (!queueA.isEmpty()) {
					
					serveCustomer(queueA);
					
				} else if (!queueB.isEmpty()) {
					
					serveCustomer(queueB);
					
				} else {
					
					serveCustomer(queueC);
					
				}
				
			}

			// Increment the clock time
			clockTime++;
			
		}

		// Print the simulation results
		System.out.println("Simulation Results:");
		System.out.println("Total Customers Served: " + numCustomers);
		System.out.println("Total Idle Time: " + idleTime);
		System.out.println("Average Waiting Time: " + (totalWaitingTime / numCustomers));
		System.out.println("Average Turnaround Time: " + (totalTurnaroundTime / numCustomers));

	}

	private Customer generateCustomer() {		
		int arrivalTime = (int) (Math.random() * (maxArrivalTime - minArrivalTime)) + minArrivalTime;
		
		int serviceTime = (int) (Math.random() * (maxServiceTime - minServiceTime)) + minServiceTime;
		
		return new Customer(arrivalTime, serviceTime);
		
	}

	private void serveCustomer(Queue<Customer> queue) {
		Customer customer = queue.poll();
		customer.toString();

		System.out.println(customer);
		int waitingTime = clockTime - customer.getArrivalTime();
		totalWaitingTime += waitingTime;
		int turnaroundTime = waitingTime + customer.getServiceTime();
		totalTurnaroundTime += turnaroundTime;
	}

}
