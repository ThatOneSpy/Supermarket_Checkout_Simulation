package simulatoraddition;

import java.text.DecimalFormat;

public class Customer {

	private double arrivalTime; // 0..59, the minute of the hour when a customer arrives

	private double serviceTime; // 1, 2, or 3 minutes

	private double finishTime;

	private double serveTime; // When the customer is served in clock time

	private int customerId;

	private static int idCounter = 0;

	private double wait;

	public Customer() // default constructor
	{
		arrivalTime = 0;
		serviceTime = 0;
		finishTime = 0;
		serveTime = 0;
		customerId = ++idCounter;
	}

	public Customer(double arrTime, double serTime) // Constructor takes in the very first customer or a customer with
													// no
													// wait
													// time
	{
		arrivalTime = arrTime;
		serviceTime = serTime;
		finishTime = arrTime + serTime;
		serveTime = arrTime;
		customerId = ++idCounter;
		wait = 0;
	}

	public Customer(double arrTime, double serTime, double wait) { // Constructor takes in any other customer with a
																	// wait
																	// time
																	// determined in the driver
		arrivalTime = arrTime;
		serviceTime = serTime;
		finishTime = arrTime + serTime + wait;
		serveTime = finishTime - serTime;
		customerId = ++idCounter;
		this.wait = wait;
	}

	public String toString() {
		DecimalFormat df = new DecimalFormat("0.0");
		return "Customer " + customerId + " arrived at time " + arrivalTime + ", waited " + df.format(wait)
				+ " minutes to be served, was served at time " + df.format(serveTime) + " with a total service time of "
				+ df.format(serviceTime) + " minutes, and left at time " + df.format(finishTime) + ".";
	}

	public void setArrivalTime(double arrTime) {
		arrivalTime = arrTime;
	}

	public double getArrivalTime() {
		return arrivalTime;
	}

	public void setServiceTime(double ser) {
		serviceTime = ser;
	}

	public double getServiceTime() {
		return serviceTime;
	}

	public double getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(double finishTime) {
		this.finishTime = finishTime;
	}

	public double getServeTime() {
		return serveTime;
	}

	public void setServeTime(double serveTime) {
		this.serveTime = serveTime;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public double getWait() {
		return wait;
	}

	public void setWait(double wait) {
		this.wait = wait;
	}

}
