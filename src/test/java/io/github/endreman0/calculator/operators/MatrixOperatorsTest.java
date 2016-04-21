package io.github.endreman0.calculator.operators;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;

import io.github.endreman0.calculator.Parser;
import io.github.endreman0.calculator.expression.type.Matrix;
import io.github.endreman0.calculator.expression.type.MixedNumber;

public class MatrixOperatorsTest{
	@Test
	public void testAdd(){
		add(valueOf("[3, 4][5, 6]"), valueOf("[1_4/5, 3][-1/3, 3_1/10]"), valueOf("[4_4/5, 7][4_2/3, 9_1/10]"));
	}
	private void add(Matrix i1, Matrix i2, Matrix result){
		assertEquals(result, i1.add(i2));
	}
	@Test
	public void testSubtract(){
		subtract(valueOf("[3, 4][5, 6]"), valueOf("[1_4/5, 3][-1/3, 3_1/10]"), valueOf("[1_1/5, 1][5_1/3, 2_9/10]"));
	}
	private void subtract(Matrix i1, Matrix i2, Matrix result){
		assertEquals(result, i1.subtract(i2));
	}
	@Test
	public void testMultiplyMatrix(){
		multiply(valueOf("[3, 4]"), valueOf("[1][2]"), valueOf("[11]"));
	}
	private void multiply(Matrix i1, Matrix i2, Matrix result){
		assertEquals(result, i1.multiply(i2));
	}
	@Test
	public void testMultiplyMixedNumber(){
		multiply(valueOf("[-1, 3_2/5][70, -13_1/2]"), MixedNumber.valueOf(4), valueOf("[-4, 13_3/5][280, -54]"));
	}
	private void multiply(Matrix i1, MixedNumber i2, Matrix result){
		assertEquals(result, i1.multiply(i2));
	}
	private static Matrix valueOf(String input){
		String[] tokens = Parser.parse(input);
		Queue<String> queue = new LinkedList<>(Arrays.asList(tokens));
		queue.poll();//Remove the leading "[", as the contract of @ComplexFactory says it won't be there
		return Matrix.valueOf(queue);
	}
	@Test
	public void testDivide(){
		divide(valueOf("[-1/5, 4_2/3, 5/7]"), MixedNumber.valueOf(-3), valueOf("[1/15, -1_5/9, -5/21]"));
	}
	private void divide(Matrix i1, MixedNumber i2, Matrix result){
		assertEquals(result, i1.divide(i2));
	}
}
