package io.github.endreman0.calculator.operators;

import static org.junit.Assert.*;

import org.junit.Test;

import io.github.endreman0.calculator.token.type.Time;

public class TimeOperatorsTest{
	@Test
	public void testAdd(){
		add(Time.valueOf(3, 15), Time.valueOf(14, 7, 29), Time.valueOf(17, 22, 29));
	}
	private void add(Time i1, Time i2, Time result){
		assertEquals(result, i1.add(i2));
	}
	@Test
	public void testSubtract(){
		subtract(Time.valueOf(3, 15), Time.valueOf(14, 7, 29), Time.valueOf(13, 7, 31));
	}
	private void subtract(Time i1, Time i2, Time result){
		assertEquals(result, i1.subtract(i2));
	}
}
