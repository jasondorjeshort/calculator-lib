package io.github.endreman0.calculator.operators;

import static io.github.endreman0.calculator.token.type.MixedNumber.valueOf;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.github.endreman0.calculator.token.type.MixedNumber;
import io.github.endreman0.calculator.token.type.Set;

public class MixedNumberOperatorsTest{
	@Test
	public void testAdd(){
		add(valueOf(1, 1, 5), valueOf(2, 5), valueOf(1, 3, 5));
		add(valueOf(1, 8), valueOf(3, 8), valueOf(1, 2));
		add(valueOf(1, 6, 7), valueOf(5, 7), valueOf(2, 4, 7));
		add(valueOf(3), valueOf(-1, 2), valueOf(2, 1, 2));
		add(valueOf(0), valueOf(-1, 2), valueOf(-1, 2));
	}
	private void add(MixedNumber i1, MixedNumber i2, MixedNumber result){
		assertEquals(result, i1.add(i2));
	}
	@Test
	public void testSubtract(){
		subtract(valueOf(1, 3, 5), valueOf(1, 5), valueOf(1, 2, 5));
		subtract(valueOf(1, 2), valueOf(1, 3), valueOf(1, 6));
		subtract(valueOf(1, 2), valueOf(1, 7), valueOf(5, 14));
		subtract(valueOf(1, 6), valueOf(1, 6), valueOf(0));
		subtract(valueOf(1, 6), valueOf(1, 3), valueOf(-1, 6));
	}
	private void subtract(MixedNumber i1, MixedNumber i2, MixedNumber result){
		assertEquals(result, i1.subtract(i2));
	}
	@Test
	public void testMultiply(){
		multiply(valueOf(1, 3), valueOf(1, 2), valueOf(1, 6));
		multiply(valueOf(1, 1, 3), valueOf(6, 2), valueOf(4));
		multiply(valueOf(1, 2, 3), valueOf(0), valueOf(0));
	}
	private void multiply(MixedNumber i1, MixedNumber i2, MixedNumber result){
		assertEquals(result, i1.multiply(i2));
	}
	@Test
	public void testDivide(){
		divide(valueOf(3, 5), valueOf(3), valueOf(1, 5));
		divide(valueOf(4, 7), valueOf(1, 7), valueOf(4));
	}
	private void divide(MixedNumber i1, MixedNumber i2, MixedNumber result){
		assertEquals(result, i1.divide(i2));
	}
	@Test
	public void testModulus(){
		modulus(valueOf(1), valueOf(3, 5), valueOf(2, 5));
		modulus(valueOf(1, 2), valueOf(1, 3), valueOf(1, 6));
	}
	private void modulus(MixedNumber i1, MixedNumber i2, MixedNumber result){
		assertEquals(result, i1.modulus(i2));
	}
	@Test
	public void testPlusOrMinus(){
		plusOrMinus(valueOf(3), valueOf(5), valueOf(-2), valueOf(8));
		plusOrMinus(valueOf(2, 1, 5), valueOf(0), valueOf(2, 1, 5));
	}
	private void plusOrMinus(MixedNumber i1, MixedNumber i2, MixedNumber... results){
		assertEquals(Set.valueOf(results), i1.plusOrMinus(i2));
	}
}
