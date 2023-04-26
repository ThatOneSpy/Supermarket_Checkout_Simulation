package aardvarksCheckpointETest;

import java.util.InputMismatchException;
import java.util.Scanner;

public class JavaMarketDriver {

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
		System.out.println("Recommended amount of customers to serve: 100");
		System.out.println("Recommended slow percentage for self-checkout customers: 20");
		System.out.println("Recommended amount of lanes for full service checkout (per 20 customers): 2");
		System.out.println("Recommended amount of lanes for self service checkout (per 20 customers): 2\n");

		int minArrivalTime = 0;
		int maxArrivalTime = 0;
		int minServiceTime = 0;
		int maxServiceTime = 0;
		int numCustomers = 0;
		int percentSlower = 0;
		int numFullLanes = 0;
		int numSelfLanes = 0;

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
			System.out.print("Percentage(%) slower for SELF (Must be greater than zero): ");
			percentSlower = scan.nextInt();
			System.out.println("Enter number of full service checkout lanes (Must be greater than zero):");
			numFullLanes = scan.nextInt();
			System.out.println("Enter number of self service checkout lanes (Must be greater than zero):");
			numSelfLanes = scan.nextInt();

			if (minArrivalTime < 0 || maxArrivalTime <= 0 || minServiceTime <= 0 || maxServiceTime <= 0
					|| numCustomers <= 0 || percentSlower <= 0 || numFullLanes <= 0 || numSelfLanes <= 0) {
				System.out.println("Invalid input was entered. Program will close.");
				System.exit(0);
			}

		} catch (InputMismatchException ime) {
			System.out.println("Input Mismatch Exception. You did not enter an integer. Program needs to exit.");
			System.exit(0);

		}

		System.out.println();
		supermarketDB.addTestData(minArrivalTime, maxArrivalTime, minServiceTime, maxServiceTime, numCustomers,
				percentSlower, numFullLanes, numSelfLanes);
		Simulator simulate = new Simulator(minArrivalTime, maxArrivalTime, minServiceTime, maxServiceTime, numCustomers,
				percentSlower, numFullLanes, numSelfLanes);
		simulate.run();

		System.out.println("Simulation complete.");
		scan.close();
		System.exit(0);

	}
}
