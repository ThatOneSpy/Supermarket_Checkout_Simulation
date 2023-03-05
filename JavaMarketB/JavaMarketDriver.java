package JavaMarketB;

import java.util.ArrayList;
import java.util.LinkedList;
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

		System.out.print("Enter minimum arrival time between customers:");
		int minArrivalTime = scan.nextInt();
		System.out.print("Enter maximum arrival time between customers:");
		int maxArrivalTime = scan.nextInt();
		System.out.print("Enter minimum service time:");
		int minServiceTime = scan.nextInt();
		System.out.print("Enter maximum service time:");
		int maxServiceTime = scan.nextInt();
		System.out.print("Enter number of customers to serve:");
		int numCustomers = scan.nextInt();

		// Initialize queues
		LinkedList<Customer> queueA = new LinkedList<>();
		LinkedList<Customer> queueB = new LinkedList<>();
		LinkedList<Customer> queueC = new LinkedList<>();

		// ArrayList to keep track of Customer wait times
		ArrayList<Integer> waitList = new ArrayList<>();

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
			currentTime = a.getFinishTime();
			turnaround = getTurnaround(0, a.getServiceTime());
			System.out.println(a.toString());
			System.out.println("Average wait: 0");
			System.out.println("Total time checkouts were not in use: 0 minutes");
			System.out.println("Customer satisfaction: 1 satisfied (<5 minutes)  0 dissatisfied (>=5 minutes)");

		} else {
			queueA.add(a);
			if (!queueA.isEmpty()) {
				serveCustomer(queueA, currentTime);

			}
			turnaround = getTurnaround(0, a.getServiceTime());
			waitList.add(0);
			System.out.println(a.toString());
			System.out.println("Customer went into Queue A.");
			Customer b;
			// Use a for loop to go through all customers (make sure to subtract one from
			// numCustomers because we have a basis)
			for (int i = 0; i < numCustomers; i++) {

				int bAT = genArrivalTime(minArrivalTime, maxArrivalTime);
				int bST = genServiceTime(minServiceTime, maxServiceTime);
				currentTime = currentTime + bAT;
				int bWait = getWait(a, currentTime);
				turnaround = getTurnaround(bWait, bST);

				waitList.add(bWait);
				b = new Customer(currentTime, bST, bWait);
				System.out.println(b.toString());

				if (queueA.isEmpty() || (queueA.size() <= queueB.size() && queueA.size() <= queueC.size())) {
					queueA.add(b);
					if (!queueA.isEmpty()) {
						serveCustomer(queueA, currentTime);
						System.out.println("Customer went into Queue A.");
					}
				} else if (queueB.isEmpty() || queueB.size() <= queueA.size() && queueB.size() <= queueC.size()) {
					queueB.add(b);
					if (!queueB.isEmpty()) {
						serveCustomer(queueB, currentTime);
						System.out.println("Customer went into Queue B.");
					}
				} else {
					queueC.add(b);
					if (!queueC.isEmpty()) {
						serveCustomer(queueC, currentTime);
						System.out.println("Customer went into Queue C.");
					}
				}
				a = b;
			}

			System.out.println("Average wait: " + waitAvg(waitList, (numCustomers + 1)));
			System.out.println("Total time checkouts were not in use: " + noUse);
			satisfactionCalc(waitList);

			scan.close();
		}

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
		int finish = a.getFinishTime();
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

		return (sum / numCustomers);

	}

	public static int getTurnaround(int wait, int service) {
		int turnaround = wait + service;
		return turnaround;
	}

	public static void serveCustomer(LinkedList<Customer> queue, int currentTime) {
		// Serve the first customer in the queue
		Customer customer = queue.getFirst();
		if (customer.getServiceTime() == -1) {
			customer.setServiceTime(currentTime);
		}
		customer.setServiceTime(customer.getServiceTime() - 1);

		// Remove the customer from the queue if they have been served
		if (customer.getServiceTime() == 0) {

			queue.remove();

		}
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
