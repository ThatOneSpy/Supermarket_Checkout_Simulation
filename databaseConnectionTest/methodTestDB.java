package databaseConnectionTest;

import java.util.Scanner;

import aardvarksCheckpointE.Customer;
import aardvarksCheckpointE.SupermarketDB;

public class methodTestDB {

	public static void main(String[] args) {

		Customer testCust = new Customer(10, 10);

		int minArrivalTime = 1;
		int maxArrivalTime = 2;
		int minServiceTime = 3;
		int maxServiceTime = 4;
		int numCustomers = 5;
		int percentSlower = 6;
		int numFullLanes = 7;
		int numSelfLanes = 8;

		double fullWait = 1.1;
		double selfWait = 2.2;
		double fullNoUse = 3.3;
		double selfNoUse = 4.4;
		int satisfied = 5;
		int dissatisfied = 6;

		int choice = 0;
		while (choice != 10) {
			
			choice = menu();
			
			if (choice == 1)
				SupermarketDB.printCustomers();
			
			else if (choice == 2)
				SupermarketDB.addCustomers(testCust);
			
			else if (choice == 3)
				SupermarketDB.addTestData(minArrivalTime, maxArrivalTime, minServiceTime, maxServiceTime, numCustomers,
						percentSlower, numFullLanes, numSelfLanes);
			
			else if (choice == 4)
				SupermarketDB.addResults(fullWait, selfWait, fullNoUse, selfNoUse, satisfied, dissatisfied);

			else if (choice == 10) {
				SupermarketDB.closeConnection();
				System.exit(0);
			}
		}

	}

	public static int menu() {
		Scanner scan = new Scanner(System.in);
		System.out.println("\nMenu:");
		System.out.println("1.  Print customers table");
		System.out.println("2.  Add customer to customer table");
		System.out.println("3.  Add testing data to testdata table ");
		System.out.println("4.  Add results data to results table");

		System.out.println("10.  END");
		int ans = scan.nextInt();
		return ans;

	}

}
