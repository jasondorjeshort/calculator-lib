package io.github.endreman0.calculator.expression.type;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;

import io.github.endreman0.calculator.Parser;

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
		assertEquals("{-5_1/3, 2/3, 1}", test.toDisplayString());
		assertEquals("{-5_1/3, 2/3, 1}", test.toParseableString());
		assertEquals("Set[MixedNumber[-16,3],MixedNumber[2,3],MixedNumber[1,1]]", test.toDescriptorString());
	}
	@Test
	public void testValueOfMixedNumberArray(){
		assertEquals(test, Set.valueOf(data));
	}
	@Test
	public void testValueOf(){
		String[] tokens = Parser.parse(test.toString());
		Queue<String> queue = new LinkedList<>(Arrays.asList(tokens));
		queue.poll();//Get rid of the leading "{"; it won't be there when valueOf() is called by the Processor
		assertEquals(test, Set.valueOf(queue));
	}
}
