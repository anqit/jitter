package pers.ankit.j2r.core;

import java.util.Map.Entry;

import pers.ankit.j2r.tokens.*;

public abstract class JSONVisitor {

	public void visit(JSONObject object) {
		traverseObject(object, 0);
	}
	
	protected void visitJSONValue(JSONValue value, String key, int depth) {
		if(value.isArray()) {
			visitJSONArray((JSONArray) value, key, depth);
		} else if(value.isBoolean()) {
			visitJSONBoolean((JSONBoolean) value, key, depth);
		} else if(value.isNull()) {
			visitJSONNull((JSONNull) value, key, depth);
		} else if(value.isNumber()) {
			visitJSONNumber((JSONNumber) value, key, depth);
		} else if(value.isObject()) {
			visitJSONObject((JSONObject) value, key, depth);
		} else if(value.isString()) {
			visitJSONString((JSONString) value, key, depth);
		}
	}
	
	protected void visitJSONValue(JSONValue value, int index, int depth) {
		if(value.isArray()) {
			visitJSONArray((JSONArray) value, index, depth);
		} else if(value.isBoolean()) {
			visitJSONBoolean((JSONBoolean) value, index, depth);
		} else if(value.isNull()) {
			visitJSONNull((JSONNull) value, index, depth);
		} else if(value.isNumber()) {
			visitJSONNumber((JSONNumber) value, index, depth);
		} else if(value.isObject()) {
			visitJSONObject((JSONObject) value, index, depth);
		} else if(value.isString()) {
			visitJSONString((JSONString) value, index, depth);
		}
	}
	
	protected void traverseObject(JSONObject object, int depth) {
		object.begin();
		while(object.hasNext()) {
			Entry<String, JSONValue> pair = object.next();
			JSONValue value = pair.getValue();
			String key = pair.getKey();
			
			visitJSONValue(value, key, depth + 1);
		}
	}
	
	protected void traverseArray(JSONArray array, int depth) {
		for(int index = 0; index < array.size(); index++) {
			JSONValue el = array.get(index);
			
			visitJSONValue(el, index, depth + 1);
		}
	}
	
	protected abstract void visitJSONArray(JSONArray array, String key, int depth);
	protected abstract void visitJSONArray(JSONArray array, int index, int depth);
	protected abstract void visitJSONBoolean(JSONBoolean bool, String key, int depth);
	protected abstract void visitJSONBoolean(JSONBoolean bool, int index, int depth);
	protected abstract void visitJSONNull(JSONNull nil, String key, int depth);
	protected abstract void visitJSONNull(JSONNull nil, int index, int depth);
	protected abstract void visitJSONNumber(JSONNumber no, String key, int depth);
	protected abstract void visitJSONNumber(JSONNumber no, int index, int depth);
	protected abstract void visitJSONObject(JSONObject obj, String key, int depth);
	protected abstract void visitJSONObject(JSONObject obj, int index, int depth);
	protected abstract void visitJSONString(JSONString string, String key, int depth);
	protected abstract void visitJSONString(JSONString string, int index, int depth);
}