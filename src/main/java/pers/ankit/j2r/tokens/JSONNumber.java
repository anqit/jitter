package pers.ankit.j2r.tokens;

public class JSONNumber extends JSONValue {
	private double number;
	
	public JSONNumber(double number) {
		this.number = number;
	}
	
	public JSONNumber(String number) {
		this.number = Double.valueOf(number);
	}
	
	public double getNumber() {
		return number;
	}

	@Override
	public String asString() {
		// TODO Auto-generated method stub
		return Double.toString(number);
	}
	
	@Override
	public boolean isNumber() {
		return true;
	}
}