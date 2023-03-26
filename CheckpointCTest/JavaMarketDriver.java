package CheckpointCTest;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class JavaMarketDriver {

	private class Node {
		Customer value;
		Node next;

		/**
		 * Constructor.
		 * 
		 * @param val The element to store in the node.
		 * @param n   The reference to the successor node.
		 */

		Node(Customer val, Node n) {
			value = val;
			next = n;
		}

		/**
		 * Constructor.
		 * 
		 * @param val The element to store in the node.
		 */

		@SuppressWarnings("unused")
		Node(Customer val) {
			// Call the other (sister) constructor.
			this(val, null);
		}
	}

	private Node first; // list head
	private Node last; // last element in list

	/**
	 * Constructor.
	 */

	public JavaMarketDriver() {
		first = null;
		last = null;
	}

	public boolean isEmpty() {
		return first == null;
	}

	public int size() {
		int count = 0;
		Node p = first;
		while (p != null) {
			// There is an element at p
			count++;
			p = p.next;
		}
		return count;
	}

	/**
	 * The add method adds an element to the end of the list.
	 * 
	 * @param e The value to add to the end of the list.
	 */

	public void add(Customer e) {
		if (isEmpty()) {
			first = new Node(e);
			last = first;
		} else {
			// Add to end of existing list
			last.next = new Node(e);
			last = last.next;
		}
	}

	/**
	 * The add method adds an element at a position.
	 * 
	 * @param e     The element to add to the list.
	 * @param index The position at which to add the element.
	 * @exception IndexOutOfBoundsException When index is out of bounds.
	 */

	public void add(int index, Customer e) {
		if (index < 0 || index > size()) {
			String message = String.valueOf(index);
			throw new IndexOutOfBoundsException(message);
		}

		// Index is at least 0
		if (index == 0) {
			// New element goes at beginning
			first = new Node(e, first);
			if (last == null)
				last = first;
			return;
		}

		// Set a reference pred to point to the node that
		// will be the predecessor of the new node
		Node pred = first;
		for (int k = 1; k <= index - 1; k++) {
			pred = pred.next;
		}

		// Splice in a node containing the new element
		pred.next = new Node(e, pred.next);

		// Is there a new last element ?
		if (pred.next.next == null)
			last = pred.next;
	}

	/**
	 * The toString method computes the string representation of the list.
	 * 
	 * @return The string form of the list.
	 */

	public String toString() {
		StringBuilder strBuilder = new StringBuilder();

		// Use p to walk down the linked list
		Node p = first;
		while (p != null) {
			strBuilder.append(p.value + "\n");
			p = p.next;
		}
		return strBuilder.toString();
	}

	/**
	 * The remove method removes the element at an index.
	 * 
	 * @param index The index of the element to remove.
	 * @return The element removed.
	 * @exception IndexOutOfBoundsException When index is out of bounds.
	 */

	public Customer remove(int index) {
		if (index < 0 || index >= size()) {
			String message = String.valueOf(index);
			throw new IndexOutOfBoundsException(message);
		}

		Customer element; // The element to return
		if (index == 0) {
			// Removal of first item in the list
			element = first.value;
			first = first.next;
			if (first == null)
				last = null;
		} else {
			// To remove an element other than the first,
			// find the predecessor of the element to
			// be removed.
			Node pred = first;

			// Move pred forward index - 1 times
			for (int k = 1; k <= index - 1; k++)
				pred = pred.next;

			// Store the value to return
			element = pred.next.value;

			// Route link around the node to be removed
			pred.next = pred.next.next;

			// Check if pred is now last
			if (pred.next == null)
				last = pred;
		}
		return element;
	}

	/**
	 * The remove method removes an element.
	 * 
	 * @param element The element to remove.
	 * @return true if the remove succeeded, false otherwise.
	 */

	public boolean remove(Customer element) {
		if (isEmpty())
			return false;

		if (element.equals(first.value)) {
			// Removal of first item in the list
			first = first.next;
			if (first == null)
				last = null;
			return true;
		}

		// Find the predecessor of the element to remove
		Node pred = first;
		while (pred.next != null && !pred.next.value.equals(element)) {
			pred = pred.next;
		}

		// pred.next == null OR pred.next.value is element
		if (pred.next == null)
			return false;

		// pred.next.value is element
		pred.next = pred.next.next;

		// Check if pred is now last
		if (pred.next == null)
			last = pred;

		return true;
	}

	// Variable to keep track of time where checkouts aren't in use
	static int noUse = 0;

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		System.out.println("Welcome to the Java Market Checkout Simulator.");
		System.out.println(
				"To get started, please enter some endpoints for ranges that will calculate interarrival time, service time for each customer, and number of customers to serve.");
		System.out.println("WARNING: Please enter only positive integers in order for the program to run correctly.\n");

		System.out.println("The following are recommended intervals and number to yield the best results.");
		System.out.println(
				"As the range of both interarrival time and service time increases, the likelihood of increased minutes of no checkouts being used goes up.");
		System.out.println(
				"This applies to expanding numbers of customers, where the more customers you serve, the likelihood of customer dissatisfication increases.");
		System.out.println("Recommended interval for interarrival and service times: 2-5");
		System.out.println("Recommended amount of customers to serve: 100\n");

		int minArrivalTime = 0;
		int maxArrivalTime = 0;
		int minServiceTime = 0;
		int maxServiceTime = 0;
		int numCustomers = 0;
		int percentSlower = 0;

		try {

			System.out.print("Enter minimum interarrival time between customers (Must be at least zero):");
			minArrivalTime = scan.nextInt();
			System.out.print("Enter maximum interarrival time between customers (Must be greater than zero):");
			maxArrivalTime = scan.nextInt();
			System.out.print("Enter minimum service time (Must be greater than zero):");
			minServiceTime = scan.nextInt();
			System.out.print("Enter maximum service time (Must be greater than zero):");
			maxServiceTime = scan.nextInt();
			System.out.print("Enter number of customers to serve (Must be greater than zero):");
			numCustomers = scan.nextInt();
			System.out.print("Percentage slower for SELF: ");
			percentSlower = scan.nextInt();
			double slow = calculatePercentSlower(percentSlower);

			if (minArrivalTime < 0 || maxArrivalTime <= 0 || minServiceTime <= 0 || maxServiceTime <= 0
					|| numCustomers <= 0 || percentSlower <= 0) {
				System.out.println("Invalid input was entered. Program will close.");
				System.exit(0);
			}

		} catch (InputMismatchException ime) {
			System.out.println("Input Mismatch Exception. You did not enter an integer. Program needs to exit.");
			System.exit(0);

		}

		System.out.println();

		// Initialize queues
		LinkedList<Customer> queueA = new LinkedList<>();
		LinkedList<Customer> queueB = new LinkedList<>();
		LinkedList<Customer> queueC = new LinkedList<>();
		Queue<Customer> selfQueue = new Queue<>();

		// ArrayList to keep track of Customer wait times
		ArrayList<Integer> waitList = new ArrayList<>();
		ArrayList<Integer> selfWaitList = new ArrayList<>();

		// Clock variable to determine arrival time for Customer constructor
		int currentTime = 0;

		// Need at least one customer for basis
		int aAT = genArrivalTime(minArrivalTime, maxArrivalTime);
		int aST = genServiceTime(minServiceTime, maxServiceTime);
		currentTime = currentTime + aAT;
		Customer a = new Customer(currentTime, aST);

		numCustomers--;

		// Tracks turn around
		int turnaround;

		if (numCustomers == 0) {
			currentTime = (int) a.getFinishTime();
			turnaround = getTurnaround(0, (int) a.getServiceTime());
			System.out.println(a.toString());
			System.out.println("Average wait: 0");
			System.out.println("Total time checkouts were not in use: 0 minutes");
			System.out.println("Customer satisfaction: 1 satisfied (<5 minutes)  0 dissatisfied (>=5 minutes)");

		} else {
			queueA.add(a);
			turnaround = getTurnaround(0, (int) a.getServiceTime());
			waitList.add(0);
			CustomerCreator create = new CustomerCreator(minArrivalTime, maxArrivalTime, minServiceTime, maxServiceTime,
					currentTime, percentSlower);
			System.out.println(a.toString());
			System.out.println("Customer " + a.getCustomerId() + " entered Queue A with a wait of 0 minutes.");
			Customer b;
			// Use a for loop to go through all customers (make sure to subtract one from
			// numCustomers because we have a basis)
			
			int fullCount = 0;
			int selfCount = 0;
			
			for (int i = 0; i < numCustomers; i++) {

				int bAT = genArrivalTime(minArrivalTime, maxArrivalTime);
				int bST = genServiceTime(minServiceTime, maxServiceTime);
				currentTime = currentTime + bAT;
				int bWait = getWait(a, currentTime);
				turnaround = getTurnaround(bWait, bST);

				b = new Customer(currentTime, bST, bWait);

				Random random = new Random();
				int chance = random.nextInt(2);
				


				if (chance == 0) {
					fullCount++;
					
					System.out.println("Customer entering FULL-Checkout");

					if (!queueA.isEmpty()) {
						if (b.getArrivalTime() >= queueA.getFirst().getFinishTime()) {
							serveCustomer(queueA);
						}
					}
					if (!queueB.isEmpty()) {
						if (b.getArrivalTime() >= queueB.getFirst().getFinishTime()) {
							serveCustomer(queueB);
						}
					}
					if (!queueC.isEmpty()) {
						if (b.getArrivalTime() >= queueC.getFirst().getFinishTime()) {
							serveCustomer(queueC);
						}
					}

					if ((queueA.size() <= queueB.size() && queueA.size() <= queueC.size())) {
						if (!queueA.isEmpty()) {
							if (queueA.size() > 1) {
								bWait = getWait(queueA.getLast(), currentTime);
							} else if (queueA.size() == 1) {
								bWait = getWait(queueA.getFirst(), currentTime);
							}
							int newFinishTime = (int) (b.getArrivalTime() + b.getServiceTime() + bWait);
							b.setFinishTime(newFinishTime);
							int newServeTime = (int) (b.getArrivalTime() + bWait);
							b.setServeTime(newServeTime);
						} else if (queueA.isEmpty()) {
							bWait = 0;
							b.setFinishTime(b.getArrivalTime() + b.getServiceTime());
							int newServeTime = (int) (b.getArrivalTime() + bWait);
							b.setServeTime(newServeTime);
						}
						waitList.add(bWait);
						queueA.add(b);
						System.out.println(b.toString());
						System.out.println("Customer " + b.getCustomerId() + " entered Queue A with a wait of " + bWait
								+ " minutes.");

					}

					else if ((queueB.size() <= queueA.size() && queueB.size() <= queueC.size())) {
						if (!queueB.isEmpty()) {
							if (queueB.size() > 1) {
								bWait = getWait(queueB.getLast(), currentTime);
							} else if (queueB.size() == 1) {
								bWait = getWait(queueB.getFirst(), currentTime);
							}
							int newFinishTime = (int) (b.getArrivalTime() + b.getServiceTime() + bWait);
							b.setFinishTime(newFinishTime);
							int newServeTime = (int) (b.getArrivalTime() + bWait);
							b.setServeTime(newServeTime);
						} else if (queueB.isEmpty()) {
							bWait = 0;
							b.setFinishTime(b.getArrivalTime() + b.getServiceTime());
							int newServeTime = (int) (b.getArrivalTime() + bWait);
							b.setServeTime(newServeTime);
						}
						waitList.add(bWait);
						queueB.add(b);
						System.out.println(b.toString());
						System.out.println("Customer " + b.getCustomerId() + " entered Queue B with a wait of " + bWait
								+ " minutes.");
					}

					else {
						if (!queueC.isEmpty()) {
							if (queueC.size() > 1) {
								bWait = getWait(queueC.getLast(), currentTime);
							} else if (queueC.size() == 1) {
								bWait = getWait(queueC.getFirst(), currentTime);
							}
							int newFinishTime = (int) (b.getArrivalTime() + b.getServiceTime() + bWait);
							b.setFinishTime(newFinishTime);
							int newServeTime = (int) (b.getArrivalTime() + bWait);
							b.setServeTime(newServeTime);
						} else if (queueC.isEmpty()) {
							bWait = 0;
							b.setFinishTime(b.getArrivalTime() + b.getServiceTime());
							int newServeTime = (int) (b.getArrivalTime() + bWait);
							b.setServeTime(newServeTime);
						}
						waitList.add(bWait);
						queueC.add(b);
						System.out.println(b.toString());
						System.out.println("Customer " + b.getCustomerId() + " entered Queue C with a wait of " + bWait
								+ " minutes.");
					}
					a = b;
				} else {
					selfCount++;
					System.out.println("Customer entering SELF-Checkout");

					bWait = (int) b.getWait();

					if (!selfQueue.isEmpty()) {
						selfQueue.needToDequeue(b.getArrivalTime());
					}

					if (selfQueue.getCapacity() < 2 || selfQueue.isEmpty()) {
						bWait = 0;
						create.resetFinishTime(b, bWait);
						create.resetServeTime(b, bWait);
					}
					selfWaitList.add((bWait));
					selfQueue.enqueue(b);
					System.out.println(b.toString());
					a = b;
				}
			}
			// End of full Service
			DecimalFormat df = new DecimalFormat("0.0");
			System.out.println("Average full checkout wait: " + waitAvg(waitList, (fullCount + 1)));
			System.out.println("Average self checkout wait: " + waitAvg(selfWaitList, (selfCount)));

			System.out.println("Total time checkouts were not in use: " + noUse);
			waitList.addAll(selfWaitList);
			satisfactionCalc(waitList);

			System.out.println("Simulation complete.");
			scan.close();
			System.exit(0);

		}

	}

	public static double calculatePercentSlower(int givenPercent) {
		double newPercent = (double) givenPercent / 100;
		return newPercent;
	}

	// Generates an arrival time given the two range parameters determined by user
	public static int genArrivalTime(int min, int max) {
		int r = (int) (Math.random() * (max - min)) + min;
		return r;
	}

	// Generates a service time given the two range parameters determined by user
	public static int genServiceTime(int min, int max) {
		int r = (int) (Math.random() * (max - min)) + min;
		return r;
	}

	// Generates the wait time of a single customer given that there has been at
	// least one customer served before them....
	public static int getWait(Customer a, int cusBArrival) {
		int finish = (int) a.getFinishTime();
		int wait = finish - cusBArrival;
		if (wait < 0) {
			wait = (-wait);
			noUse = noUse + wait;
			wait = 0;
			return wait;
		} else {
			return wait;
		}
	}

	public static double waitAvg(ArrayList<Integer> wait, int numCustomers) {
		int sum = 0;
		for (int i = 0; i < wait.size(); i++) {
			sum = sum + wait.get(i);

		}
		double avg = (sum / numCustomers);

		return avg;

	}

	public static int getTurnaround(int wait, int service) {
		int turnaround = wait + service;
		return turnaround;
	}

	public static void serveCustomer(LinkedList<Customer> queue) {
		Customer c = queue.getFirst();
		queue.remove(c);
	}

	public static void satisfactionCalc(ArrayList<Integer> wait) {
		int satisfied = 0;
		int dissatisfied = 0;

		for (int i = 0; i < wait.size(); i++) {

			if (wait.get(i) >= 5) {
				dissatisfied++;
			}

			else {
				satisfied++;
			}
		}

		System.out.println("Customer satisfaction: " + satisfied + " satisfied (<5 minutes)  " + dissatisfied
				+ " dissatisfied (>=5 minutes)");

	}
}
