package pers.ankit.j2r.tokens;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class JSONObject extends JSONValue {
	private Map<String, JSONValue> members = new HashMap<String, JSONValue>();
	private Iterator<Entry<String, JSONValue>> it;
	
	public void add(String key, JSONValue val) {
		members.put(key, val);
	}
	
//	public Map<String, JSONValue> getMembers() {
//		return members;
//	}

	public JSONValue get(String key) {
		return members.get(key);
	}
	
	public int size() {
		return members.size();
	}

	public void begin() {
		it = members.entrySet().iterator();
	}
	
	public boolean hasNext() {
		return it.hasNext();
	}
	
	public Entry<String, JSONValue> next() {
		return it.next();
	}
	
	@Override
	public String asString() {
		StringBuffer obj = new StringBuffer();
		obj.append("{");

		for(Entry<String, JSONValue> pair : members.entrySet()) {
			obj.append('\"').append(pair.getKey()).append('\"').append(":").append(pair.getValue().asString()).append(",");
		}
		
		return obj.substring(0, obj.length() - 1).concat("}");
	}
	
	@Override
	public boolean isObject() {
		return true;
	}
}