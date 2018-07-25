package pers.ankit.j2r.tokens;

public class JSONString extends JSONValue {
	private String string;
	
	public JSONString(String string) {
		this.string = string;
	}
	
	public String getString() {
		return string;
	}

	@Override
	public String asString() {
		return String.format("\"%s\"", string);
	}
	
	@Override
	public boolean isString() {
		return true;
	}
}