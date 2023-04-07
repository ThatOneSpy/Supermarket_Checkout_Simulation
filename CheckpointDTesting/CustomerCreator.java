package CheckpointDTesting;

import java.util.ArrayList;

public class CustomerCreator {

	private int minArrive;
	private int maxArrive;
	private double minService;
	private double maxService;
	private double currentTime;
	private Customer previous;
	private double percentSlow;
	private int numSelfLanes;

	public CustomerCreator(int minA, int maxA, int minS, int maxS, double time, double percent, int self) {
		minArrive = minA;
		maxArrive = maxA;
		minService = minS;
		maxService = maxS;
		currentTime = time;
		previous = null;
		percentSlow = percent;
		numSelfLanes = self;
	}

	public Customer callFirstCustomer() {
		double service = genServiceTimeWithPercent(minService, maxService, percentSlow);
		currentTime = 0.0 + (double) genArrivalTime(minArrive, maxArrive);
		Customer a = new Customer(currentTime, service);
		return a;
	}

	public Customer callNextCustomer(Queue<Customer> checkout) {
		double service = genServiceTimeWithPercent(minService, maxService, percentSlow);
		currentTime = currentTime + (double) genArrivalTime(minArrive, maxArrive);
		double wait = getWaitNew(checkout, currentTime);
		Customer b = new Customer(currentTime, service, wait);
		return b;

	}

	public Customer callNextCustomerFull(int arrival, int service, Customer a) {
		double bWait = getWait(a, currentTime);
		Customer b = new Customer(currentTime, service, bWait);
		return b;
	}

	public double getWait(Customer a, double currentTime) {
		double finish = a.getFinishTime();
		double wait = finish - currentTime;
		if (wait < 0) {
			wait = (-wait);
			wait = 0;
			return wait;
		} else {
			return wait;
		}
	}

	public double getWaitNew(Queue<Customer> checkout, double cusBArrival) {
		// figure out the next two ahead and who has the smallest time
		ArrayList<Customer> customers = checkout.toArrayList();
		double finish = 0;
		if (customers.size() >= numSelfLanes && numSelfLanes > 1) {
			double aFinish = customers.get(customers.size() - 1).getFinishTime();
			double bFinish = customers.get(customers.size() - 2).getFinishTime();
			if (aFinish < bFinish) {
				finish = aFinish;
				checkout.dequeue(customers.get(customers.size() - 1));
			} else if (bFinish < aFinish) {
				finish = bFinish;
				checkout.dequeue(customers.get(customers.size() - 2));
			} else {
				finish = aFinish;
				checkout.dequeue(customers.get(customers.size() - 2));
			}
		} else if (customers.size() >= numSelfLanes && numSelfLanes == 1) {
			double aFinish = checkout.peekLast().getFinishTime();
			finish = aFinish;
		} else {
			finish = 0;
		}
		double wait = finish - cusBArrival;
		if (wait < 0) {
			wait = 0;
			return wait;
		} else {
			return wait;
		}
	}

	// Generates an arrival time given the two range parameters determined by user
	public int genArrivalTime(int min, int max) {
		int r = (int) (Math.random() * (max - min)) + min;
		return r;
	}

	// Generates a service time given two range parameters and a percentage slower
	// by user
	public double genServiceTimeWithPercent(double minService2, double maxService2, double percent) {
		double r = (int) (Math.random() * (maxService2 - minService2)) + minService2;
		double newSer = (r * percent) + r;
		return newSer;
	}

	public void resetFinishTime(Customer b, double wait) {
		double newFinishTime = (b.getArrivalTime() + b.getServiceTime() + wait);
		b.setFinishTime(newFinishTime);
	}

	public void resetServeTime(Customer b, double wait) {
		double newServeTime = (b.getArrivalTime() + wait);
		b.setServeTime(newServeTime);
	}

	public double getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(double currentTime) {
		this.currentTime = currentTime;
	}

	public Customer getPrevious() {
		return previous;
	}

	public void setPrevious(Customer previous) {
		this.previous = previous;
	}

	public void setPercentSlow(double percentSlow) {
		this.percentSlow = percentSlow;
	}

}
