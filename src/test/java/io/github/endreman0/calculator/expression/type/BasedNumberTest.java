package io.github.endreman0.calculator.expression.type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BasedNumberTest{
	private int[][] values ={{10, 15}, {2, 128}, {16, 255}, {36, 1295}};
	private BasedNumber[] tests = new BasedNumber[values.length];
	{for(int i=0; i<values.length; i++) tests[i] = BasedNumber.valueOf(values[i][0], values[i][1]);}
	@Test
	public void testBase(){
		for(int i=0; i<values.length; i++)
			assertEquals(values[i][0], tests[i].base());
	}
	@Test
	public void testValue(){
		for(int i=0; i<values.length; i++)
			assertEquals(values[i][1], tests[i].value());
	}
	@Test
	public void testToString(){
		assertEquals("10x15", tests[0].toDisplayString());
		assertEquals("10x15", tests[0].toParseableString());
		
		assertEquals("2x10000000", tests[1].toDisplayString());
		assertEquals("2x10000000", tests[1].toParseableString());
		
		assertEquals("16xFF", tests[2].toDisplayString());
		assertEquals("16xff", tests[2].toParseableString());
		
		assertEquals("36xZZ", tests[3].toDisplayString());
		assertEquals("36xzz", tests[3].toParseableString());
	}
	@Test
	public void testEquals(){
		for(int i=0; i<values.length; i++){
			assertTrue(tests[i].equals(tests[i]));
			assertFalse(tests[i].equals(tests[i+1<values.length ? i+1 : 0]));
		}
	}
	@Test
	public void testValueOfInts(){
		for(int i=0; i<values.length; i++)
			assertTrue(tests[i].equals(BasedNumber.valueOf(tests[i].base(), tests[i].value())));
	}
	@Test
	public void testValueOfString(){
		for(int i=0; i<values.length; i++){
			String input = tests[i].toParseableString();
			BasedNumber res = BasedNumber.valueOf(input);
			assertTrue(tests[i].equals(res));
		}
	}
}
