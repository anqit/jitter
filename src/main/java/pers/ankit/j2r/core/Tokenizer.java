package pers.ankit.j2r.core;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import pers.ankit.j2r.exception.JSONParserException;
import pers.ankit.j2r.tokens.JSONArray;
import pers.ankit.j2r.tokens.JSONBoolean;
import pers.ankit.j2r.tokens.JSONNull;
import pers.ankit.j2r.tokens.JSONNumber;
import pers.ankit.j2r.tokens.JSONObject;
import pers.ankit.j2r.tokens.JSONString;
import pers.ankit.j2r.tokens.JSONValue;

public class Tokenizer {
	private static final String VALUE_TERMINATORS = " }],";
	private Reader reader;
	private char curr;
	private StringBuffer tokenValue = new StringBuffer();
	
	public Tokenizer(String json) throws JSONParserException {
		reader = new StringReader(json);
		
		initialize(json);
	}

	private void initialize(String json) throws JSONParserException {
		skipWhiteSpace();
		readNextChar();
		if (curr != '{') {
			throw new JSONParserException(String.format("Invalid JSON string: [%s] >> JSON objects should begin with '{'", json));
		}
	}
	
	public JSONObject tokenize() throws JSONParserException {
			return readObject();
	}
	
	private void readNextChar() {
		try {
			int i = reader.read();
			curr = i < 0 ? '\0' : (char) i;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String readNextChars(int n) {
		StringBuffer sb = new StringBuffer();
		
		for(int i = 0; i < n; i++) {
			readNextChar();
			sb.append(curr);
		}
		
		return sb.toString();
	}
	
	private void skipWhiteSpace() {
		while(Character.isSpaceChar(curr)) {
			readNextChar();
		}
	}
	
//	private boolean atEnd() {
//		return curr == '\0';
//	}
	
	private void storeCurr() {
		store(curr);
	}
	
	private void store(char c) {
		tokenValue.append(c);
		readNextChar();
	}
	
	private String getTokenValue() {
		String value = tokenValue.toString();
		tokenValue = new StringBuffer();	// TODO clear instead of new  
		
		return value;
	}
	
	private boolean valueUnterminated() {
		return VALUE_TERMINATORS.indexOf(curr) < 0 && !Character.isSpaceChar(curr);
	}
	
	private JSONObject readObject() throws JSONParserException {
		readNextChar();	// skip opening '{'
		skipWhiteSpace();
		
		JSONObject object = new JSONObject();
		
		while(curr != '}') {
			String key = readString().getString();
			
			skipWhiteSpace();
			readNextChar(); // skip ':'
			skipWhiteSpace();
			
			JSONValue value = readValue();
			object.add(key, value);
			
			skipWhiteSpace();
			if(curr == ',') {
				readNextChar();
				skipWhiteSpace();
			}
		}
		
		readNextChar();	// skip closing '}'
		
		return object;
	}

	private JSONValue readValue() throws JSONParserException {
		if(curr == '"') {
			return readString();
		}
		if(Character.isDigit(curr) || curr == '-') {
			return readNumber();
		}
		if(curr == '{') {
			return readObject();
		}
		if(curr == '[') {
			return readArray();
		}
		if(curr == 't') {
			return readBoolean(true);
		}
		if(curr == 'f') {
			return readBoolean(false);
		}
		if(curr == 'n') {
			return readNull();
		}
		
		throw new JSONParserException(String.format("Invalid character [%c] found while trying to identify value type", curr));
	}

	private JSONString readString() throws JSONParserException {
		readNextChar();	// skip opening '"'
		
		while(curr != '"') {
			if(curr == '\\') {
				readNextChar();
				
				switch(curr) {
				case '"':
				case '\\':
				case '/':
					store(curr);
					break;
				case 'b':
					store('\b');
					break;
				case 'f':
					store('\f');
					break;
				case 'n':
					store('\n');
					break;
				case 'r':
					store('\r');
					break;
				case 't':
					store('\t');
					break;
				case 'u':
					String number = readNextChars(4);
					try {
						store((char)Integer.parseInt(number, 16));
					} catch(NumberFormatException nfe) {
						throw new JSONParserException(String.format("Invalid hex code [\\u%s] encountered", number));
					}
					break;
				default:
					throw new JSONParserException(String.format("Illegal escape [\\%c] encountered", curr));
				}
			} else {
				storeCurr();
			}
		}

		readNextChar(); // skip closing '"'
		
		return new JSONString(getTokenValue());
	}
	
	private JSONNumber readNumber() throws JSONParserException {
		while(valueUnterminated()) {
			storeCurr();
		}
		
		String number = getTokenValue();
		
		try {
			return new JSONNumber(Double.valueOf(number));
		} catch(NumberFormatException nfe) {
			throw new JSONParserException(String.format("Invalid number format encountered [%s]", number));
		}
	}


	private JSONArray readArray() throws JSONParserException {
		readNextChar();	// skip opening '['
		skipWhiteSpace();
		
		JSONArray array = new JSONArray();
		
		while(curr != ']') {
			array.add(readValue());
			skipWhiteSpace();
			
			if(curr == ',') {
				readNextChar();
				skipWhiteSpace();
			}
		}
		
		readNextChar();	// skip closing ']'
		
		return array;
	}
	
	private JSONBoolean readBoolean(boolean b) throws JSONParserException {
		String bool;
		if(b) {
			bool = readNextChars(4);
			
			if(bool.equalsIgnoreCase("true")) {
				return new JSONBoolean(true);
			}
		} else {
			bool = readNextChars(5);
			
			if(bool.equalsIgnoreCase("false")) {
				return new JSONBoolean(false);
			}
		}
		
		throw new JSONParserException(String.format("Unable to parse symbol [%s]", bool));
	}
	
	private JSONNull readNull() throws JSONParserException {
		String n = readNextChars(4);
		
		if(n.equalsIgnoreCase("null")) {
			return new JSONNull();
		}
		
		throw new JSONParserException(String.format("Unable to parse symbol [%s]", n));
	}
}