package io.github.endreman0.calculator.token.type;

import static org.junit.Assert.*;

import org.junit.Test;

import io.github.endreman0.calculator.token.type.Decimal;

public class DecimalTest{
	private double[] values ={3, 4.5, 123.456, -42.6};
	private Decimal[] tests = new Decimal[values.length];
	{for(int i=0; i<values.length; i++) tests[i] = Decimal.valueOf(values[i]);}
	@Test
	public void testValue(){
		for(int i=0; i<values.length; i++)
			assertEquals(values[i], tests[i].value(), 0);
	}
	@Test
	public void testToString(){
		for(int i=0; i<values.length; i++)
			assertEquals(String.valueOf(values[i]), tests[i].toString());
	}
	@Test
	public void testEquals(){
		for(int i=0; i<values.length; i++){
			assertTrue(tests[i].equals(tests[i]));
			assertFalse(tests[i].equals(tests[i+1<values.length ? i+1 : 0]));
		}
	}
	@Test
	public void testIsInteger(){
		assertTrue(tests[0].isInteger());
		for(int i=1; i<values.length; i++) assertFalse(tests[i].isInteger());
	}
	@Test
	public void testValueOf(){
		for(int i=0; i<values.length; i++) assertTrue(tests[i].equals(Decimal.valueOf(tests[i].value())));
	}
}
