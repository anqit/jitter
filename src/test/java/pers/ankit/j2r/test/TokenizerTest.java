package pers.ankit.j2r.test;

import static org.junit.Assert.*;

import org.junit.Test;

import pers.ankit.j2r.core.Tokenizer;
import pers.ankit.j2r.tokens.JSONObject;

public class TokenizerTest {
	private static final String SIMPLE_JSON = "{ \"this\" : \"works\" }";
	private static final String COMPLEX_JSON = "{"
													+"\"id\": \"0001\","
													+"\"type\": \"donut\","
													+"\"name\": \"Cake\","
													+"\"ppu\": 0.55\t,"
													+"\"batters\":"
													+	"{"
													+		"\"batter\":"
													+			"["
													+				"{ \"id\": \"1001\", \"type\": \"Re\\\"gular\" },"
													+				"{ \"id\": \"1002\", \"type\": \"Chocolate\" },"
													+				"{ \"id\": \"1003\", \"type\": \"Blueberry\" },"
													+				"{ \"id\": \"1004\", \"type\": \"Devil's Food\" }"
													+			"]"
													+ "},"
													+ "\"topping\":"
														+ "["
														+	"{ \"id\": \"5001\", \"type\": \"\\u0048\" },"
														+	"{ \"id\": \"5002\", \"type\": \"Glazed\" },"
														+	"{ \"id\": \"5005\", \"type\": \"Sugar\" },"
														+	"{ \"id\": \"5007\", \"type\": \"Powdered Sugar\" },"
														+	"{ \"id\": \"5006\", \"type\": \"Chocolate with Sprinkles\" },"
														+	"{ \"id\": \"5003\", \"type\": \"Chocolate\" },"
														+	"{ \"id\": \"5004\", \"type\": \"Maple\" }"
													+ "]"
											 + "}";
	
	@Test
	public void tokenizerTest() throws Exception {
		Tokenizer t1 = new Tokenizer(SIMPLE_JSON);
		JSONObject obj = t1.tokenize();
		
		assertEquals(1, obj.size());
		System.out.println(obj.asString());
		
		Tokenizer t2 = new Tokenizer(COMPLEX_JSON);
		
		JSONObject obj2 = t2.tokenize();
		
		assertEquals(6, obj2.size());
		System.out.println(obj2.asString());
	}
}