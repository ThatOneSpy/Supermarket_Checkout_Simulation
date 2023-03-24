package SimulatorTest;

import java.util.Scanner;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Get simulation parameters from user
        System.out.print("Enter minimum arrival time between customers: ");
        int minArrivalTime = scanner.nextInt();
        System.out.print("Enter maximum arrival time between customers: ");
        int maxArrivalTime = scanner.nextInt();
        System.out.print("Enter minimum service time: ");
        int minServiceTime = scanner.nextInt();
        System.out.print("Enter maximum service time: ");
        int maxServiceTime = scanner.nextInt();
        System.out.print("Enter number of customers to serve: ");
        int numCustomers = scanner.nextInt();
        
        // Create simulator object and run simulation
        Simulator simulator = new Simulator(minArrivalTime, maxArrivalTime, minServiceTime, maxServiceTime, numCustomers);
        simulator.run();
    }

}


