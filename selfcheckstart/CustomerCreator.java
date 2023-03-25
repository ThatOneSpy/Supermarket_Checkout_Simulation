package selfcheckstart;

import java.text.DecimalFormat;

public class CustomerCreator {

	private int minArrive;
	private int maxArrive;
	private int minService;
	private int maxService;
	private double currentTime;
	private Customer previous;
	private double percentSlow;

	public CustomerCreator() {
		minArrive = 0;
		maxArrive = 0;
		minService = 0;
		maxService = 0;
		currentTime = 0;
		previous = null;
		percentSlow = 0;
	}

	public CustomerCreator(int minA, int maxA, int minS, int maxS, double time, double percent) {
		minArrive = minA;
		maxArrive = maxA;
		minService = minS;
		maxService = maxS;
		currentTime = time;
		previous = null;
		percentSlow = percent;
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

	public double getWaitNew(Queue<Customer> checkout, double cusBArrival) {
		// figure out the next two ahead and who has the smallest time
		DecimalFormat df = new DecimalFormat("0.0");
		double finish = 0;
		if (checkout.size() > 1) {
			double aFinish = checkout.peekFirst().getFinishTime();
			Customer b = checkout.peekLast();
			if (aFinish < b.getFinishTime())
				finish = aFinish;
			else if (b.getFinishTime() < aFinish)
				finish = b.getFinishTime();
			System.out.println("The customer that leaves first has a finish time of " + finish);
		} else {
			finish = checkout.peekFirst().getFinishTime();
		}
		double wait = finish - cusBArrival;
		System.out.println("Wait is: " + df.format(wait));
		if (wait < 0) {
			wait = 0;
			System.out.println("Wait set to zero.");
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
	public double genServiceTimeWithPercent(int min, int max, double percent) {
		double r = (int) (Math.random() * (max - min)) + min;
		double newSer = (r * percent) + r;
		return newSer;
	}

	public double getTurnaround(double wait, double service) {
		double turnaround = wait + service;
		return turnaround;
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

}
