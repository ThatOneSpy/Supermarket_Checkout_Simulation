package LineSimExample;

import java.util.*;

public class Customer {

	private int arrivalTime; // 0..59, the minute of the hour when a customer arrives

	private int serviceTime; // 1, 2, or 3 minutes

	private int leaveTime; // the minute in the simulation when the customer leaves

	private int waitTime; // total time spent waiting before being served

	
	public Customer() // default constructor
	{
		arrivalTime = 0;
		serviceTime = 0;
		leaveTime = 0;
		waitTime = 0;
	}

	public Customer(int arrTime) // one argument constructor
	{
		arrivalTime = arrTime;
		Random rand = new Random();
		serviceTime = rand.nextInt(3) + 1; // 1, 2, or 3 minutes
		leaveTime = arrTime + serviceTime;
		waitTime = 0;
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

	public int getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(int leaveTime) {
		this.leaveTime = leaveTime;
	}

	public int getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}

	
	
}
