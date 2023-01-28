package JavaMarket;

public class Customer {

	private int arrivalTime;
	private double serviceTime;
	private double endTime;
	private double waitTime;
	
	public Customer() {
		
	}
	
	public Customer(int arrivalTime, double serviceTime, double endTime, double waitTime) {
		
		this.arrivalTime = arrivalTime;
		this.serviceTime = serviceTime;
		this.endTime = endTime;
		this.waitTime = waitTime;
		
	}
	
	public String toString() {
		return "null";
	}

	public double getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public double getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(double serviceTime) {
		this.serviceTime = serviceTime;
	}

	public double getEndTime() {
		return endTime;
	}

	public void setEndTime(double endTime) {
		this.endTime = endTime;
	}

	public double getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(double waitTime) {
		this.waitTime = waitTime;
	}
	
	
	
}
