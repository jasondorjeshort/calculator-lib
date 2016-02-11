package io.github.endreman0.calculator.token.type;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import io.github.endreman0.calculator.error.CalculatorException;
import io.github.endreman0.calculator.token.type.MixedNumber;
import io.github.endreman0.calculator.token.type.Set;

public class SetTest{
	private final MixedNumber[] data = {MixedNumber.valueOf(1), MixedNumber.valueOf(2, 3), MixedNumber.valueOf(-5, 1 ,3)};
	private final Set test = Set.valueOf(data);
	@Test
	public void testHashCode(){
		assertEquals(test.hashCode(), test.clone().hashCode());
	}
	@Test
	public void testSize(){
		assertEquals(data.length, test.size());
	}
	@Test
	public void testGet(){
		for(int i=0; i<data.length; i++){
			assertEquals(data[i], test.get(i));
			assertFalse(data[i] == test.get(i));
		}
	}
	@Test
	public void testContains(){
		for(int i=0; i<data.length; i++) assertTrue(test.contains(data[i]));
		assertFalse(test.contains(null));
	}
	@Test
	public void testEquals(){
		assertTrue(test.equals(test));
		assertTrue(test.equals(test.clone()));
		assertFalse(test.equals(null));
	}
	@Test
	public void testToString(){
		assertEquals("{1 | 2/3 | -5_1/3}", test.toDisplayString());
		assertEquals("{1|2/3|-5_1/3}", test.toParseableString());
		assertEquals("Set[3]", test.toDescriptorString());
	}
	@Test
	public void testToArray(){
		assertTrue(Arrays.equals(data, test.toArray()));
	}
	@Test
	public void testValueOfMixedNumberArray(){
		assertEquals(test, Set.valueOf(test.toArray()));
	}
	@Test
	public void testValueOf() throws CalculatorException{
		assertEquals(test, Set.valueOf(test.toParseableString()));
	}
}
