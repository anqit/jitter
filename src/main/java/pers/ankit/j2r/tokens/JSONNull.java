package pers.ankit.j2r.tokens;

public class JSONNull extends JSONValue {
	@Override
	public String asString() {
		return "null";
	}
	
	@Override
	public boolean isNull() {
		return true;
	}
}