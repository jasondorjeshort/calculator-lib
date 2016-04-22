package io.github.endreman0.calculator;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class ParserTest{	
	@Test
	public void testOperators(){
		test("3 * 2", "3", "*", "2");
		test("1.5 / 72.0 + 5", "1.5", "/", "72.0", "+", "5");
	}
	@Test
	public void testInstanceFunctions(){
		test("3.reciprocal()", "3", ".", "reciprocal", "(", ")");
		test("<3, 4>.magnitude()", "<", "3", ",", "4", ">", ".", "magnitude", "(", ")");
		test("{x | x > 0}.contains(3.5)", "{", "x", "|", "x", ">", "0", "}", ".", "contains", "(", "3.5", ")");
	}
	@Test
	public void testStaticFunctions(){
		test("abs(-3.5)", "abs", "(", "-3.5", ")");
	}
	@Test
	public void testComplexTypes(){
		test("[1, 2][3, 4]", "[", "1", ",", "2", "]", "[", "3", ",", "4", "]");
	}
	private void test(String input, String... output){
		assertTrue(Arrays.equals(output, Parser.parse(input)));
	}
}
