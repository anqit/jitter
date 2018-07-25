package pers.ankit.j2r.tokens;

public class JSONBoolean extends JSONValue {
	private boolean value;
	
	public JSONBoolean(boolean value) {
		this.value = value;
	}

	public JSONBoolean(String value) {
		this.value = Boolean.valueOf(value);
	}
	
	public boolean getValue() {
		return value;
	}

	@Override
	public String asString() {
		return Boolean.toString(value);
	}
	
	@Override
	public boolean isBoolean() {
		return true;
	}
}