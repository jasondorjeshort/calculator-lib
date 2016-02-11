package io.github.endreman0.calculator.operators;

import static io.github.endreman0.calculator.token.type.BasedNumber.valueOf;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BasedNumberOperatorsTest{
	@Test
	public void testAdd(){
		add(2, 10, 5);
		add(36, 100, 14);
	}
	private void add(int base, int value1, int value2){
		assertEquals(valueOf(base, value1 + value2), valueOf(base, value1).add(valueOf(base, value2)));
	}
	@Test
	public void testSubtract(){
		subtract(2, 10, 5);
		subtract(36, 100, 14);
	}
	private void subtract(int base, int value1, int value2){
		assertEquals(valueOf(base, value1 - value2), valueOf(base, value1).subtract(valueOf(base, value2)));
	}
	@Test
	public void testMultiply(){
		multiply(2, 10, 5);
		multiply(36, 100, 14);
	}
	private void multiply(int base, int value1, int value2){
		assertEquals(valueOf(base, value1 * value2), valueOf(base, value1).multiply(valueOf(base, value2)));
	}
	@Test
	public void testDivide(){
		subtract(2, 10, 5);
		divide(36, 100, 14);
	}
	private void divide(int base, int value1, int value2){
		assertEquals(valueOf(base, value1 / value2), valueOf(base, value1).divide(valueOf(base, value2)));
	}
	@Test
	public void testModulus(){
		modulus(2, 10, 5);
		modulus(36, 100, 14);
	}
	private void modulus(int base, int value1, int value2){
		assertEquals(valueOf(base, value1 % value2), valueOf(base, value1).modulus(valueOf(base, value2)));
	}
}
