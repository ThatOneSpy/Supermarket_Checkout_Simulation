package JavaMarketB;

public class JavaMarketDriver {

	public static void main(String[] args) {

		// Requirements: three full service queues labeled A, B, C
		// Arrival and service times are randomly generated within a given range
		// Ranges are determined by user input, but we suggest ranges that worked best
		// for the most optimal solution
		// A virtual clock must be kept track of (use integer values)

		// Customers going through queue: They enter a queue with the least amount of
		// people already in it, get served, and then leave. If the queues are tied,
		// then the person joins the queue in alphabetical order

		// Required time data for each customer
		// a. the clock time when the customer arrives at the queue
		// b. the clock time when the customer is served
		// c. the clock time when the customer leaves

		// The program must remember the wait time of that customer even after they
		// leave (possibly use an array or an ArrayList?), which is clock time when they
		// get served minus the clock time when they leave

		// Customers also have a wait time and a service time that must be kept track
		// of. Service time begins when they reach the front of the queue. Turnaround
		// time is the total time that the customer waited and was served combined.

		// Figure out how to calculate wait time.

		// Keep track of time where checkouts are not being used. This needs to be
		// minimized.

	}

}
