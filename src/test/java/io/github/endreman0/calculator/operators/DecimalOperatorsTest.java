package io.github.endreman0.calculator.operators;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.github.endreman0.calculator.token.type.Decimal;

public class DecimalOperatorsTest{
	@Test
	public void testAdd(){
		add(1.5, 2.4);
		add(-3.5, 0.45);
		add(1, -0.12);
	}
	private void add(double i1, double i2){
		assertEquals(Decimal.valueOf(i1 + i2), Decimal.valueOf(i1).add(Decimal.valueOf(i2)));
	}
	@Test
	public void testSubtract(){
		subtract(1.5, 2.4);
		subtract(-3.5, 0.45);
		subtract(1, -0.12);
	}
	private void subtract(double i1, double i2){
		assertEquals(Decimal.valueOf(i1 - i2), Decimal.valueOf(i1).subtract(Decimal.valueOf(i2)));
	}
	@Test
	public void testMultiply(){
		multiply(1.5, 2.4);
		multiply(-3.5, 0.45);
		multiply(1, -0.12);
		multiply(-10.5, 0);
	}
	private void multiply(double i1, double i2){
		assertEquals(Decimal.valueOf(i1 * i2), Decimal.valueOf(i1).multiply(Decimal.valueOf(i2)));
	}
	@Test
	public void testDivide(){
		divide(1.5, 2.4);
		divide(-3.5, 0.45);
		divide(1, -0.12);
	}
	private void divide(double i1, double i2){
		assertEquals(Decimal.valueOf(i1 / i2), Decimal.valueOf(i1).divide(Decimal.valueOf(i2)));
	}
	@Test
	public void testModulus(){
		modulus(1.5, 2.4);
		modulus(-3.5, 0.45);
		modulus(1, -0.12);
	}
	private void modulus(double i1, double i2){
		assertEquals(Decimal.valueOf(i1 % i2), Decimal.valueOf(i1).modulus(Decimal.valueOf(i2)));
	}
}
