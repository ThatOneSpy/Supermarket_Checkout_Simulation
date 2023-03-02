package JavaMarketB;

public class Customer {

	private int arrivalTime; // 0..59, the minute of the hour when a customer arrives

	private int serviceTime; // 1, 2, or 3 minutes

	private int finishTime;

	public Customer() // default constructor
	{
		arrivalTime = 0;
		serviceTime = 0;
		finishTime = 0;
	}

	public Customer(int arrTime, int serTime) // Constructor takes in the very first customer or a customer with no wait
												// time
	{
		arrivalTime = arrTime;
		serviceTime = serTime;
		finishTime = arrTime + serTime;
	}

	public Customer(int arrTime, int serTime, int wait) { // Constructor takes in any other customer with a wait time
															// determined in the driver
		arrivalTime = arrTime;
		serviceTime = serTime;
		finishTime = arrTime + serTime + wait;
	}

	public String toString() {
		return "Customer arrived at time " + arrivalTime + " with a total service time of " + serviceTime
				+ " minutes and left at time " + finishTime + ".";
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
