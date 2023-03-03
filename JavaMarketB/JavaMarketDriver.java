package JavaMarketB;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import JavaMarketB.Customer;

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

	public static void main(String[] args) {

		LinkedList<Customer> queueA = new LinkedList<>();
		LinkedList<Customer> queueB = new LinkedList<>();
		LinkedList<Customer> queueC = new LinkedList<>();

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

		// ArrayList to keep track of Customer wait times
		ArrayList<Integer> waits = new ArrayList<>();

		// Clock variable to determine arrival time for Customer constructor
		int clock = 0;
		int aEmpty = 0;
		int bEmpty = 0;
		int cEmpty = 0;

		// Need at least one customer for basis
		int aAT = genArrivalTime(minArrivalTime, maxArrivalTime);
		int aST = genServiceTime(minServiceTime, maxServiceTime);
		clock = clock + aAT;
		Customer a = new Customer(clock, aST);

		numCustomers--;
		if (numCustomers == 0) {
			clock = a.getFinishTime();
			System.out.println("Average wait: 0");
			System.out.println("Total time checkouts were not in use: 0 minutes");
			System.out.println("Customer satisfaction: 1 satisfied (<5 minutes)  0 dissatisfied (>=5 minutes)");

		} else {
			Customer b = new Customer();
			// Use a for loop to go through all customers (make sure to subtract one from
			// numCustomers because we have a basis)
			for (int i = 0; i < numCustomers; i++) {
				if (queueA.isEmpty() || (queueA.size() < queueB.size() && queueA.size() < queueC.size())) {

				} else if (queueB.isEmpty() || (queueB.size() < queueA.size() && queueB.size() < queueC.size())) {

				} else if (queueC.isEmpty() || (queueC.size() < queueA.size() && queueC.size() < queueB.size())) {

				} else {

				}
			}
		}
		Customer customer = new Customer(aAT, aST);
		serveCustomer(queueA, clock);

		// Use a for loop to go through all customers (make sure to subtract one from
		// numCustomers because we have a basis)

		// Requirements: three full service queues labeled A, B, C
		// Arrival and service times are randomly generated within a given range
		// Ranges are determined by user input, but we suggest ranges that worked best
		// for the most optimal solution
		// A virtual clock must be kept track of (use integer values)

		// Customers going through queue: They enter a queue with the least amount of
		// people already in it, get served, and then leave. If the queues are tied,
		// then the person joins the queue in alphabetical order

		// Required time data for each customer
		// a. the clock time when the customer arrives at the queue(arrivalTime)
		// b. the clock time when the customer is served (finishTime-serviceTime in
		// blueprint)
		// c. the clock time when the customer leaves(finishTime)

		// The program must remember the wait time of that customer even after they
		// leave (possibly use an array or an ArrayList?)

		// Customers also have a wait time and a service time that must be kept track
		// of. Service time begins when they reach the front of the queue. Turnaround
		// time is the total time that the customer waited and was served combined.

		// Figure out how to calculate wait time. Take the first person in the queue,
		// figure out their service time. Take the next person and their interarrival
		// time. DONE

		// Keep track of time where checkouts are not being used. This needs to be
		// minimized.

	}

	// Generates an arrival time given the two range parameters determined by user
	public static int genArrivalTime(int min, int max) {
		int r = (int) (Math.random()) * (max - min) + min;
		return r;
	}

	// Generates a service time given the two range parameters determined by user
	public static int genServiceTime(int min, int max) {
		int r = (int) (Math.random()) * (max - min) + min;
		return r;
	}

	// Generates the wait time of a single customer given that there has been at
	// least one customer served before them
	public static int getWait(Customer a, int arTime) {
		int finish = a.getFinishTime();
		int wait = finish - arTime;
		return wait;
	}

	public static void serveCustomer(LinkedList<Customer> queue, int clock) {

		if (!queue.isEmpty()) {
			Customer customer = queue.element(); // get the first customer in the queue
			if (customer.getServiceTime() == -1) { // if the customer has not been served yet
				customer.setServiceTime(clock); // set the service start time to the current clock time
				int serviceTime = customer.getServiceTime(); // get the customer's service time
				customer.setFinishTime(clock + serviceTime); // set the service end time to the current clock time plus
																// the service time
			}
			if (customer.getFinishTime() == clock) { // if the customer has finished being served
				queue.remove(); // remove the customer from the queue
			}
		}
	}

}
