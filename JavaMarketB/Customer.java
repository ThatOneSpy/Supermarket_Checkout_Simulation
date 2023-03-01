package JavaMarketB;

import java.util.*;

public class Customer {

	private int arrivalTime; // 0..59, the minute of the hour when a customer arrives

	private int serviceTime; // 1, 2, or 3 minutes

	public Customer() // default constructor
	{
		arrivalTime = 0;
		serviceTime = 0;
	}

	public Customer(int arrTime) // one argument constructor
	{
		arrivalTime = arrTime;
		Random rand = new Random();
		serviceTime = rand.nextInt(3) + 1; // 1, 2, or 3 minutes
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
}
