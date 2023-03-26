package simulatoraddition;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Simulator {

	private int minArrivalTime;
	private int maxArrivalTime;
	private int minServiceTime;
	private int maxServiceTime;
	private int numCustomers;
	private double percentSlower;
	static double noUse = 0;

	public Simulator(int minAT, int maxAT, int minST, int maxST, int cus, int slow) {
		minArrivalTime = minAT;
		maxArrivalTime = maxAT;
		minServiceTime = minST;
		maxServiceTime = maxST;
		numCustomers = cus;
		percentSlower = calculatePercentSlower(slow);
	}

	public void run() {

		// Initialize queues
		LinkedList<Customer> queueA = new LinkedList<>();
		LinkedList<Customer> queueB = new LinkedList<>();
		LinkedList<Customer> queueC = new LinkedList<>();
		Queue<Customer> selfQueue = new Queue<>();

		// ArrayList to keep track of Customer wait times
		ArrayList<Double> waitList = new ArrayList<>();
		ArrayList<Double> selfWaitList = new ArrayList<>();

		// Clock variable to determine arrival time for Customer constructor
		double currentTime = 0.0;

		// Need at least one customer for basis

		CustomerCreator create = new CustomerCreator(minArrivalTime, maxArrivalTime, minServiceTime, maxServiceTime,
				currentTime, percentSlower);

		Random random = new Random();
		int chance = random.nextInt(2);
		Customer a = null;

		if (chance == 0) {
			System.out.println("\nCustomer entering FULL-Checkout");
			create.setPercentSlow(1.0);
			a = create.callFirstCustomer();
		} else if (chance == 1) {
			System.out.println("\nCustomer entering SELF-Checkout");
			a = create.callFirstCustomer();
		}

		numCustomers--;

		// Turnaround time
		@SuppressWarnings("unused")
		double turnaround;

		if (numCustomers == 0) {
			currentTime = a.getFinishTime();
			turnaround = getTurnaround(0, (int) a.getServiceTime());
			System.out.println(a.toString());
			System.out.println("Average wait: 0");
			System.out.println("Total time checkouts were not in use: 0 minutes");
			System.out.println("Customer satisfaction: 1 satisfied (<5 minutes)  0 dissatisfied (>=5 minutes)");

		} else {
			int fullCount = 0;
			int selfCount = 0;
			if (chance == 0) {
				fullCount++;
				queueA.add(a);
				System.out.println(a.toString());
				System.out.println("Customer " + a.getCustomerId() + " entered Queue A with a wait of 0 minutes.");
			} else if (chance == 1) {
				selfCount++;
				selfQueue.enqueue(a);
				System.out.println(a.toString());
				System.out.println("Customer " + a.getCustomerId() + " entered Queue D with a wait of 0 minutes.");
			}
			turnaround = getTurnaround(0, a.getServiceTime());
			waitList.add(0.0);
			create.setPrevious(a);
			Customer b;
			// Use a for loop to go through all customers (make sure to subtract one from
			// numCustomers because we have a basis)

			for (int i = 0; i < numCustomers; i++) {

				chance = random.nextInt(2);
				double bWait = 0.0;

				if (chance == 0) {

					int bAT = genArrivalTime(minArrivalTime, maxArrivalTime);
					int bST = genServiceTime(minServiceTime, maxServiceTime);
					currentTime = currentTime + bAT;
					b = create.callNextCustomerFull(bAT, bST, a);

					fullCount++;
					create.setPercentSlow(1.0);

					System.out.println("\nCustomer entering FULL-Checkout");

					runFullService(queueA, queueB, queueC, b);

					if ((queueA.size() <= queueB.size() && queueA.size() <= queueC.size())) {
						bWait = adjustFullService(bWait, currentTime, b, queueA);
						waitList.add(bWait);
						addtoQueue(b, bWait, queueA);
						System.out.println("Customer " + b.getCustomerId() + " entered Queue A with a wait of " + bWait
								+ " minutes.");
					}

					else if ((queueB.size() <= queueA.size() && queueB.size() <= queueC.size())) {
						bWait = adjustFullService(bWait, currentTime, b, queueB);
						waitList.add(bWait);
						addtoQueue(b, bWait, queueB);
						System.out.println("Customer " + b.getCustomerId() + " entered Queue B with a wait of " + bWait
								+ " minutes.");
					}

					else {
						bWait = adjustFullService(bWait, currentTime, b, queueC);
						waitList.add(bWait);
						addtoQueue(b, bWait, queueC);
						System.out.println("Customer " + b.getCustomerId() + " entered Queue C with a wait of " + bWait
								+ " minutes.");
					}
				} else {
					b = create.callNextCustomer(selfQueue);
					selfCount++;
					create.setPercentSlow(percentSlower);
					System.out.println("\nCustomer entering SELF-Checkout");

					bWait = b.getWait();

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
				}
				a = b;
			}
			System.out.println("\nSimulation Results:");
			System.out.println("Average full checkout wait: " + waitAvg(waitList, (fullCount + 1)));
			System.out.println("Average self checkout wait: " + waitAvg(selfWaitList, (selfCount)));

			System.out.println("Total time checkouts were not in use: " + noUse);
			waitList.addAll(selfWaitList);
			satisfactionCalc(waitList);
		}

	}

	public double calculatePercentSlower(int givenPercent) {
		double newPercent = (double) givenPercent / 100;
		return newPercent;
	}

	// Generates an arrival time given the two range parameters determined by user
	public int genArrivalTime(int min, int max) {
		int r = (int) (Math.random() * (max - min)) + min;
		return r;
	}

	// Generates a service time given the two range parameters determined by user
	public int genServiceTime(int min, int max) {
		int r = (int) (Math.random() * (max - min)) + min;
		return r;
	}

	public double genServiceTimeWithPercent(int min, int max, double percent) {
		double r = (int) (Math.random() * (max - min)) + min;
		double newSer = (r * percent) + r;
		return newSer;
	}

	// Generates the wait time of a single customer given that there has been at
	// least one customer served before them....
	public double getWait(Customer a, double currentTime) {
		int finish = (int) a.getFinishTime();
		double wait = finish - currentTime;
		if (wait < 0) {
			wait = (-wait);
			noUse = noUse + wait;
			wait = 0;
			return wait;
		} else {
			return wait;
		}
	}

	public double waitAvg(ArrayList<Double> waitList, int numCustomers) {
		double sum = 0;
		for (int i = 0; i < waitList.size(); i++) {
			sum = sum + waitList.get(i);

		}
		double avg = (sum / (double) numCustomers);

		return avg;

	}

	public double getTurnaround(double bWait, double d) {
		double turnaround = bWait + d;
		return turnaround;
	}

	public void serveCustomer(LinkedList<Customer> queue) {
		Customer c = queue.getFirst();
		queue.remove(c);
	}

	public void satisfactionCalc(ArrayList<Double> waitList) {
		int satisfied = 0;
		int dissatisfied = 0;

		for (int i = 0; i < waitList.size(); i++) {

			if (waitList.get(i) >= 5) {
				dissatisfied++;
			}

			else {
				satisfied++;
			}
		}

		System.out.println("Customer satisfaction: " + satisfied + " satisfied (<5 minutes)  " + dissatisfied
				+ " dissatisfied (>=5 minutes)");

	}

	public void runFullService(LinkedList<Customer> queueA, LinkedList<Customer> queueB, LinkedList<Customer> queueC,
			Customer b) {
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
	}

	public double adjustFullService(double bWait, double currentTime, Customer b, LinkedList<Customer> queue) {
		if (!queue.isEmpty()) {
			if (queue.size() > 1) {
				bWait = getWait(queue.getLast(), currentTime);
			} else if (queue.size() == 1) {
				bWait = getWait(queue.getFirst(), currentTime);
			}
			int newFinishTime = (int) (b.getArrivalTime() + b.getServiceTime() + bWait);
			b.setFinishTime(newFinishTime);
			int newServeTime = (int) (b.getArrivalTime() + bWait);
			b.setServeTime(newServeTime);
		} else if (queue.isEmpty()) {
			bWait = 0;
			b.setFinishTime(b.getArrivalTime() + b.getServiceTime());
			int newServeTime = (int) (b.getArrivalTime() + bWait);
			b.setServeTime(newServeTime);
		}
		return bWait;
	}

	public void addtoQueue(Customer b, double bWait, LinkedList<Customer> queue) {
		queue.add(b);
		System.out.println(b.toString());
	}
}
