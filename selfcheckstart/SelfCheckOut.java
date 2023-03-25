package selfcheckstart;

import java.util.ArrayList;

public class SelfCheckOut {

	static int noUse;

	public static void main(String[] args) {

		// Make noUse static variable
		// Test variables
		Customer b = null;
		int numCustomers = 10;
		int minArrivalTime = 0;
		int maxArrivalTime = 5;
		int minServiceTime = 1;
		int maxServiceTime = 5;
		int percentSlower = 20;
		double slow = calculatePercentSlower(percentSlower);
		// Make ArrayList to keep track of wait times
		ArrayList<Double> waitList = new ArrayList<>();
		// Make queue
		Queue<Customer> checkout = new Queue<>();

		double currentTime = 0.0;
		CustomerCreator create = new CustomerCreator(minArrivalTime, maxArrivalTime, minServiceTime, maxServiceTime,
				currentTime, slow);
		Customer a = create.callFirstCustomer();
		checkout.enqueue(a);
		waitList.add(0.0);
		System.out.println(a.toString());
		System.out.println("Customer " + a.getCustomerId() + " entered the checkout with a wait of 0 minutes.");
		create.setPrevious(a);

		for (int i = 0; i < (numCustomers - 1); i++) {

			b = create.callNextCustomer(checkout);
			double bWait = 0;

			System.out.println("Size of the checkout is currently: " + checkout.size());

//			if (!checkout.isEmpty()) {
//				if (checkout.size() == 1) {
//					if (b.getArrivalTime() >= checkout.peekFirst().getFinishTime()) {
//						checkout.dequeue(checkout.peekFirst());
//						System.out.println("Dequeue 1");
//					}
//				} else {
//					if (b.getArrivalTime() >= checkout.peekLast().getFinishTime()
//							&& b.getArrivalTime() >= checkout.peekFirst().getFinishTime()) {
//						checkout.dequeue(checkout.peekLast());
//						checkout.dequeue(checkout.peekFirst());
//						System.out.println("Dequeue 2");
//					} else if (b.getArrivalTime() >= checkout.peekPrevious().getFinishTime()) {
//						checkout.dequeue(checkout.peekPrevious());
//						System.out.println("Dequeue 3");
//					} else if (b.getArrivalTime() >= checkout.peekFirst().getFinishTime()) {
//						checkout.dequeue(checkout.peekFirst());
//						System.out.println("Dequeue 4");
//					} else {
//						checkout.dequeue(checkout.peekLast());
//						System.out.println("Dequeue 5");
//					}
//				}
//			}

			if (!checkout.isEmpty()) {
				checkout.needToDequeue(b.getArrivalTime());
			}

//			if (!checkout.isEmpty()) {
//				if (checkout.size() > 1) {
//					bWait = create.getWaitNew(checkout, b.getArrivalTime());
//				} else if (checkout.size() == 1) {
//					bWait = 0;
//				}
//				create.resetFinishTime(b, bWait);
//				create.resetServeTime(b, bWait);
//			} else if (checkout.isEmpty()) {
//				bWait = 0;
//				create.resetFinishTime(b, bWait);
//				create.resetServeTime(b, bWait);
//			}

			if (checkout.size() == 1 || checkout.isEmpty()) {
				bWait = 0;
				create.resetFinishTime(b, bWait);
				create.resetServeTime(b, bWait);
			}
			waitList.add(bWait);
			checkout.enqueue(b);
			System.out.println(b.toString());
			System.out.println(
					"Customer " + b.getCustomerId() + " entered checkout with a wait of " + bWait + " minutes.");

			a = b;
		}

		System.out.println("Average wait: " +

				waitAvg(waitList, (numCustomers + 1)));
		System.out.println("Total time checkouts were not in use: 0");
		satisfactionCalc(waitList);

		System.out.println("Simulation complete.");
		System.exit(0);

	}

	// waitAvg

	// sum up noUse

	// Enqueue

	// Service/dequeue

	public static double waitAvg(ArrayList<Double> wait, int numCustomers) {
		double sum = 0;
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

	public static void satisfactionCalc(ArrayList<Double> wait) {
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

	public static double calculatePercentSlower(int givenPercent) {
		double newPercent = (double) givenPercent / 100;
		return newPercent;
	}

}
