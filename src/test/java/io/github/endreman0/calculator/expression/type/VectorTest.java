package io.github.endreman0.calculator.expression.type;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class VectorTest{
	private final MixedNumber[] data = {MixedNumber.valueOf(1), MixedNumber.valueOf(2, 3), MixedNumber.valueOf(-5, 1 ,3)};
	private final Vector test = Vector.valueOf(data);
	@Test
	public void testHashCode(){
		assertEquals(test.hashCode(), test.clone().hashCode());//Equal objects must have the same hash code
	}
	@Test
	public void testSize(){
		assertEquals(data.length, test.size());
	}
	@Test
	public void testGet(){
		for(int i=0; i<data.length; i++)
			assertEquals(data[i], test.get(i));
	}
	@Test
	public void testEquals(){
		assertTrue(test.equals(test));
		assertTrue(test.equals(test.clone()));
		assertFalse(test.equals(null));
		Vector v = Vector.valueOf(new MixedNumber[]{MixedNumber.valueOf(3), MixedNumber.valueOf(5)});
		assertFalse(test.equals(v));
	}
	@Test
	public void testToString(){
		assertEquals("<1, 2/3, -5_1/3>", test.toDisplayString());
		assertEquals("<1, 2/3, -5_1/3>", test.toParseableString());
		assertEquals("Vector[MixedNumber[1,1],MixedNumber[2,3],MixedNumber[-16,3]]", test.toDescriptorString());
	}
	@Test
	public void testToArray(){
		assertTrue(Arrays.equals(data, test.toArray()));
	}
	@Test
	public void testValueOf(){
		assertEquals(test, Vector.valueOf(test.toArray()));
	}
}
