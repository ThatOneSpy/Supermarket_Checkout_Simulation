package customercreatoradjust;

public class CustomerCreator {

	private int minArrive;
	private int maxArrive;
	private int minService;
	private int maxService;
	private int currentTime;
	static int noUse;
	private Customer previous;

	public CustomerCreator() {
		minArrive = 0;
		maxArrive = 0;
		minService = 0;
		maxService = 0;
		currentTime = 0;
		previous = null;
	}

	public CustomerCreator(int minA, int maxA, int minS, int maxS, int time) {
		minArrive = minA;
		maxArrive = maxA;
		minService = minS;
		maxService = maxS;
		currentTime = time;
		previous = null;
	}

	public Customer callFirstCustomer() {
		int service = genServiceTime(minService, maxService);
		currentTime = currentTime + genArrivalTime(minArrive, maxArrive);
		Customer a = new Customer(currentTime, service);
		return a;
	}

	public Customer callNextCustomer() {
		int service = genServiceTime(minService, maxService);
		currentTime = currentTime + genArrivalTime(minArrive, maxArrive);
		int wait = getWait(previous, currentTime);
		Customer b = new Customer(currentTime, service, wait);
		return b;

	}

	// Get wait
	public int getWait(Customer a, int cusBArrival) {
		int finish = a.getFinishTime();
		int wait = finish - cusBArrival;
		if (wait < 0) {
			wait = (-wait);
			noUse = noUse + wait;
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

	public int getTurnaround(int wait, int service) {
		int turnaround = wait + service;
		return turnaround;
	}

	public void resetFinishTime(Customer b, int wait) {
		int newFinishTime = (b.getArrivalTime() + b.getServiceTime() + wait);
		b.setFinishTime(newFinishTime);
	}

	public void resetServeTime(Customer b, int wait) {
		int newServeTime = (b.getArrivalTime() + wait);
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
