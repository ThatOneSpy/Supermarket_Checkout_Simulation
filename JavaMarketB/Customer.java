package JavaMarketB;

public class Customer {

	private int arrivalTime; // 0..59, the minute of the hour when a customer arrives

	private int serviceTime; // 1, 2, or 3 minutes

	private int finishTime;

	private int serveTime; // When the customer is served in clock time

	private int customerId;

	private static int idCounter = 0;

	public Customer() // default constructor
	{
		arrivalTime = 0;
		serviceTime = 0;
		finishTime = 0;
		serveTime = 0;
		customerId = ++idCounter;
	}

	public Customer(int arrTime, int serTime) // Constructor takes in the very first customer or a customer with no wait
												// time
	{
		arrivalTime = arrTime;
		serviceTime = serTime;
		finishTime = arrTime + serTime;
		serveTime = finishTime - serTime;
		customerId = ++idCounter;
	}

	public Customer(int arrTime, int serTime, int wait) { // Constructor takes in any other customer with a wait time
															// determined in the driver
		arrivalTime = arrTime;
		serviceTime = serTime;
		finishTime = arrTime + serTime + wait;
		serveTime = finishTime - serTime;
		customerId = ++idCounter;
	}

	public String toString() {
		return "Customer " + customerId + " arrived at time " + arrivalTime + ", was served at time " + serveTime
				+ " with a total service time of " + serviceTime + " minutes, and left at time " + finishTime + ".";
	}

	public void setArrivalTime(int arrTime) {
		arrivalTime = arrTime;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setServiceTime(int ser) {
		serviceTime = ser;
	}

	public int getServiceTime() {
		return serviceTime;
	}

	public int getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;
	}

}
