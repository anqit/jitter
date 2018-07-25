package pers.ankit.j2r.tokens;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JSONArray extends JSONValue {
	private List<JSONValue> data = new ArrayList<JSONValue>();
	private Iterator<JSONValue> it;
	
	public void add(JSONValue value) {
		data.add(value);
	}

	public JSONValue get(int index) {
		return data.get(index);
	}
	
	public int size() {
		return data.size();
	}
	
	public void begin() {
		it = data.iterator();
	}
	
	public boolean hasNext() {
		return it.hasNext();
	}
	
	public JSONValue next() {
		return it.next();
	}
	
	public Iterator<JSONValue> iterator() {
		return data.iterator();
	}
	
	@Override
	public String asString() {
		StringBuffer obj = new StringBuffer();
		obj.append("[");

		for(JSONValue value : data) {
			obj.append(value.asString()).append(",");
		}
		
		return obj.substring(0, obj.length() - 1).concat("]");
	}
	
	@Override
	public boolean isArray() {
		return true;
	}
}