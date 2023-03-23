package SimulatorTest;

public class Customer {
    private int arrivalTime;
    private int serviceTime;
    private int startServiceTime;
    private int finishTime;
    private int turnaroundTime;
    private int ID;
    private static int counter;
    
    public Customer() {
    	this.arrivalTime = 0;
    	this.serviceTime = 0;
    	this.ID = ++counter;
    	
    }
    
    // Constructor
    public Customer(int a, int s) {
        this.arrivalTime = a;
        this.serviceTime = s;
        this.ID = ++counter;
        
    }
    
    public String toString() {
		return "Cust ID: " + ID + " ArrivalTime: " + arrivalTime + " ServiceTime: " + serviceTime + ".";
    	
    }
    
    
    // Getters and setters
    public int getArrivalTime() {
        return arrivalTime;
    }
    
    public int getServiceTime() {
        return serviceTime;
    }
    
    public int getStartServiceTime() {
        return startServiceTime;
    }
    
    public void setStartServiceTime(int startServiceTime) {
        this.startServiceTime = startServiceTime;
    }
    
    public int getFinishTime() {
        return finishTime;
    }
    
    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }
    
    public int getTurnaroundTime() {
        return turnaroundTime;
    }
    
    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
    
    
}

