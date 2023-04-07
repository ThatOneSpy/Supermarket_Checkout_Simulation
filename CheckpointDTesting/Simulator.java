package CheckpointDTesting;

import java.text.DecimalFormat;
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
	private int numFullLanes;
	private int numSelfLanes;

	public Simulator(int minAT, int maxAT, int minST, int maxST, int cus, int slow, int full, int self) {
		minArrivalTime = minAT;
		maxArrivalTime = maxAT;
		minServiceTime = minST;
		maxServiceTime = maxST;
		numCustomers = cus;
		percentSlower = calculatePercentSlower(slow);
		numFullLanes = full;
		numSelfLanes = self;
	}

	static NoUse nu = new NoUse();
	static NoUse nu2 = new NoUse();

	public void run() {

		ArrayList<LinkedList<Customer>> full = new ArrayList<>();
		for (int index = 0; index < numFullLanes; index++) {
			LinkedList<Customer> queue = new LinkedList<>();
			full.add(queue);
		}

		// Initialize queues
//		LinkedList<Customer> queueA = new LinkedList<>();
//		LinkedList<Customer> queueB = new LinkedList<>();
//		LinkedList<Customer> queueC = new LinkedList<>();
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

		if (numCustomers == 0) {
			currentTime = a.getFinishTime();
			System.out.println(a.toString());
			System.out.println("Average wait: 0");
			System.out.println("Total time checkouts were not in use: 0 minutes");
			System.out.println("Customer satisfaction: 1 satisfied (<5 minutes)  0 dissatisfied (>=5 minutes)");

		} else {
			int fullCount = 0;
			int selfCount = 0;
			if (chance == 0) {
				fullCount++;
				LinkedList<Customer> queueA = full.get(0);
				queueA.add(a);
				System.out.println(a.toString());
				System.out.println("Customer " + a.getCustomerId() + " entered Queue 1 with a wait of 0 minutes.");
			} else if (chance == 1) {
				selfCount++;
				selfQueue.enqueue(a);
				System.out.println(a.toString());
				System.out
						.println("Customer " + a.getCustomerId() + " entered self-checkout with a wait of 0 minutes.");
			}
			waitList.add(0.0);
			create.setPrevious(a);
			Customer b;
			// Use a for loop to go through all customers (make sure to subtract one from
			// numCustomers because we have a basis)

			ArrayList<Customer> previous = new ArrayList<>();
			ArrayList<Customer> selfprevious = new ArrayList<>();

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

					runFullService(full, b, previous);

//					if ((queueA.size() <= queueB.size() && queueA.size() <= queueC.size())) {
//						bWait = adjustFullService(bWait, b, queueA);
//						waitList.add(bWait);
//						if (!previous.isEmpty()) {
//							calculateNoUse(b, previous, 1);
//						}
//						addtoQueue(b, bWait, queueA);
//						System.out.println("Customer " + b.getCustomerId() + " entered Queue A with a wait of " + bWait
//								+ " minutes.");
//					}
//
//					else if ((queueB.size() <= queueA.size() && queueB.size() <= queueC.size())) {
//						bWait = adjustFullService(bWait, b, queueB);
//						waitList.add(bWait);
//						if (!previous.isEmpty()) {
//							calculateNoUse(b, previous, 2);
//						}
//						addtoQueue(b, bWait, queueB);
//						System.out.println("Customer " + b.getCustomerId() + " entered Queue B with a wait of " + bWait
//								+ " minutes.");
//					}
//
//					else {
//						bWait = adjustFullService(bWait, b, queueC);
//						waitList.add(bWait);
//						if (!previous.isEmpty()) {
//							calculateNoUse(b, previous, 3);
//						}
//						addtoQueue(b, bWait, queueC);
//						System.out.println("Customer " + b.getCustomerId() + " entered Queue C with a wait of " + bWait
//								+ " minutes.");
//					}

					// Find the smallest queue..
					LinkedList<Customer> smallest = full.get(0);
					int queueName = 0;
					for (int q = 1; q < full.size() - 1; q++) {
						if (full.get(i).size() < smallest.size()) {
							smallest = full.get(i);
							queueName = i;
						}
					}
					bWait = adjustFullService(bWait, b, smallest);
					waitList.add(bWait);
					if (!previous.isEmpty()) {
						calculateNoUse(b, previous, queueName);
					}
					addtoQueue(b, bWait, smallest);
					System.out.println("Customer " + b.getCustomerId() + " entered Queue " + (queueName + 1)
							+ " with a wait of " + bWait + " minutes.");

				} else {
					b = create.callNextCustomer(selfQueue);
					selfCount++;
					create.setPercentSlow(percentSlower);
					System.out.println("\nCustomer entering SELF-Checkout");
					bWait = b.getWait();

					if (!selfQueue.isEmpty()) {
						ArrayList<Customer> prev = selfQueue.needToDequeue(b.getArrivalTime());
						if (!prev.isEmpty()) {
							for (int r = 0; r < prev.size(); r++) {
								selfprevious.add(prev.get(r));
							}
							calculateSelfNoUse(b, selfprevious);
						}
					}

					if (selfQueue.getCapacity() < numSelfLanes || selfQueue.isEmpty()) {
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
			DecimalFormat df = new DecimalFormat("##.##");
			System.out.println("\nSimulation Results:");
			System.out.println("Average full checkout wait: " + df.format(waitAvg(waitList, (fullCount + 1))));
			System.out.println("Average self checkout wait: " + df.format(waitAvg(selfWaitList, (selfCount))));

			System.out.println("Total time full checkouts were not in use: " + df.format(nu.getNoUse()));
			System.out.println("Total time self checkouts were not in use: " + df.format(nu2.getNoUse()));
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

	public void runFullService(ArrayList<LinkedList<Customer>> full, Customer b, ArrayList<Customer> prev) {
//		if (!queueA.isEmpty()) {
//			if (b.getArrivalTime() >= queueA.getFirst().getFinishTime()) {
//				if (queueA.size() == 1) {
//					queueA.getFirst().setQueue(1);
//					prev.add(queueA.getFirst());
//				}
//				serveCustomer(queueA);
//			}
//		}
//		if (!queueB.isEmpty()) {
//			if (b.getArrivalTime() >= queueB.getFirst().getFinishTime()) {
//				if (queueB.size() == 1) {
//					queueB.getFirst().setQueue(2);
//					prev.add(queueB.getFirst());
//				}
//				serveCustomer(queueB);
//			}
//		}
//		if (!queueC.isEmpty()) {
//			if (b.getArrivalTime() >= queueC.getFirst().getFinishTime()) {
//				if (queueC.size() == 1) {
//					queueC.getFirst().setQueue(3);
//					prev.add(queueC.getFirst());
//				}
//				serveCustomer(queueC);
//			}
//		}
		for (int i = 0; i < full.size(); i++) {
			LinkedList<Customer> queue = full.get(i);
			if (!queue.isEmpty()) {
				if (b.getArrivalTime() >= queue.getFirst().getFinishTime()) {
					if (queue.size() == 1) {
						queue.getFirst().setQueue(i);
						prev.add(queue.getFirst());
					}
					serveCustomer(queue);
				}
			}
		}
	}

	public double adjustFullService(double bWait, Customer b, LinkedList<Customer> queue) {
		if (!queue.isEmpty()) {
			bWait = getWait(queue.getLast(), b.getArrivalTime());
			b.setWait(bWait);
			double newFinishTime = (b.getArrivalTime() + b.getServiceTime() + bWait);
			b.setFinishTime(newFinishTime);
			double newServeTime = (b.getArrivalTime() + bWait);
			b.setServeTime(newServeTime);
		} else if (queue.isEmpty()) {
			bWait = 0.0;
			b.setWait(bWait);
			b.setFinishTime(b.getArrivalTime() + b.getServiceTime());
			double newServeTime = (b.getArrivalTime() + bWait);
			b.setServeTime(newServeTime);
		}
		return bWait;
	}

	public void addtoQueue(Customer b, double bWait, LinkedList<Customer> queue) {
		queue.add(b);
		System.out.println(b.toString());
	}

	public void calculateNoUse(Customer b, ArrayList<Customer> prev, int queue) {
		Customer a = null;
		for (int i = 0; i < prev.size(); i++) {
			if (prev.get(i).getQueue() == queue) {
				a = prev.get(i);
				prev.remove(i);
			}
		}
		nu.setA(a);
		nu.setB(b);
		nu.calculate();
	}

	public void calculateSelfNoUse(Customer b, ArrayList<Customer> prev) {
		Customer a = prev.get(0);
		// Figure out the customer who leaves last
		for (int i = 0; i < prev.size(); i++) {
			if (prev.get(i).getFinishTime() > a.getFinishTime()) {
				a = prev.get(i);
			}
		}
		nu2.setA(a);
		nu2.setB(b);
		nu2.calculate();
	}
}
