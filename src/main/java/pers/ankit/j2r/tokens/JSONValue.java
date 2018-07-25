package pers.ankit.j2r.tokens;

public abstract class JSONValue implements JSONToken {	
	public boolean isArray() {
		return false;
	}
	
	public boolean isBoolean() {
		return false;
	}
	
	public boolean isNull() {
		return false;
	}
	
	public boolean isNumber() {
		return false;
	}
	
	public boolean isObject() {
		return false;
	}
	
	public boolean isString() {
		return false;
	}
}