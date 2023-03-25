package SimulatorTest;

public class Customer {
	private int id;
	private int arrivalTime;
	private int serviceTime;
	private int startTime;
	private int finishTime;
	private int waitTime;

	public Customer(int id, int arrivalTime, int serviceTime) {
		this.id = id;
		this.arrivalTime = arrivalTime;
		this.serviceTime = serviceTime;

		startTime = arrivalTime + waitTime;
		finishTime = startTime + serviceTime;
		

	}
	
	public String toString() {
		return "Customer " + id + " arrived at time " + arrivalTime + ", was served at time " + startTime
				+ " with a total service time of " + serviceTime + " minutes, and left at time " + finishTime + ".";
	}

	public int getId() {
		return id;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public int getServiceTime() {
		return serviceTime;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;
	}

	public int getWaitTime() {
		return startTime - arrivalTime;
	}
	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}

	public int getTurnaroundTime() {
		return finishTime - arrivalTime;
	}
}
