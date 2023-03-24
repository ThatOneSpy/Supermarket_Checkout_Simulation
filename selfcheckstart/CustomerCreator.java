package selfcheckstart;

public class CustomerCreator {

	private int minArrive;
	private int maxArrive;
	private int minService;
	private int maxService;
	private int currentTime;
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

	public CustomerCreator(int minA, int maxA, int minS, int maxS, int time, double percent) {
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
		currentTime = currentTime + genArrivalTime(minArrive, maxArrive);
		Customer a = new Customer(currentTime, service);
		return a;
	}

	public Customer callNextCustomer() {
		double service = genServiceTimeWithPercent(minService, maxService, percentSlow);
		currentTime = currentTime + genArrivalTime(minArrive, maxArrive);
		double wait = getWait(previous, currentTime);
		Customer b = new Customer(currentTime, service, wait);
		return b;

	}

	// Get wait
	public double getWait(Customer a, int cusBArrival) {
		double finish = a.getFinishTime();
		double wait = finish - cusBArrival;
		if (wait < 0) {
			wait = 0;
			return wait;
		} else {
			return wait;
		}
	}

	public double getWaitNew(Queue<Customer> checkout, double cusBArrival) {
		// figure out the next two ahead and who has the smallest time
		Customer a = checkout.peekFirst();
		Customer b = checkout.peekSecond();
		double finish = 0;
		if (a.getFinishTime() < b.getFinishTime())
			finish = a.getFinishTime();
		else if (b.getFinishTime() < a.getFinishTime())
			finish = b.getFinishTime();
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

	// Generates a service time given the two range parameters determined by user
	public int genServiceTime(int min, int max) {
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

	public int getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(int currentTime) {
		this.currentTime = currentTime;
	}

	public Customer getPrevious() {
		return previous;
	}

	public void setPrevious(Customer previous) {
		this.previous = previous;
	}

}
