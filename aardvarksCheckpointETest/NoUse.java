package aardvarksCheckpointETest;

public class NoUse {

	private Customer a;
	private Customer b;
	private double noUse;

	public NoUse() {
		a = null;
		b = null;
	}

	public void calculate() {
		double finish = a.getFinishTime();
		double arrive = b.getArrivalTime();
		double calc = finish - arrive;
		noUse = noUse + (-calc);
	}

	public double getNoUse() {
		return noUse;
	}

	public void setNoUse(double noUse) {
		this.noUse = noUse;
	}

	public Customer getA() {
		return a;
	}

	public void setA(Customer a) {
		this.a = a;
	}

	public Customer getB() {
		return b;
	}

	public void setB(Customer b) {
		this.b = b;
	}

}
