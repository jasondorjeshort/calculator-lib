package io.github.endreman0.calculator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.github.endreman0.calculator.expression.Expression;

public class InputTest extends BaseTest{
	@Test
	public void testAdd(){
		test("3 + 5", integer(8));
		test("1_4/5 + 2/5", mixed(2, 1, 5));
		test("2_1/5 + 1_1/15", mixed(3, 4, 15));
	}
	@Test
	public void testSubtract(){
		test("3_1/3 - 2_1/7", mixed(1, 4, 21));
		test("1/8 - 1/8", integer(0));
	}
	@Test
	public void testMultiply(){
		test("5/7 * 1/3", fraction(5, 21));
		test("1/7 * 3", fraction(3, 7));
	}
	@Test
	public void testDivide(){
		test("5 / 9", fraction(5, 9));
		test("3_1/7 / 2/7", integer(11));
	}
	@Test
	public void testModulus(){
		test("1_1/9 % 4/9", fraction(2, 9));
	}
	@Test
	public void testCombinations(){
		test("3 + 2", integer(5));
		test("nine = 1 + 6 * 2 / 4 - -5", integer(9));
		test("nine", integer(9));
		test("three = 5.0 - 2.0", decimal(3));
		test("three", decimal(3));
		test("nine = three ^ 2.0", decimal(9));
		test("nine", decimal(9));
		test("true && false", bool(false));
		test("true || false", bool(true));
		test("x = 3.0 < 2.0 || 1.0 > 0.1 && 4 != 5", bool(true));
		test("x && 3_0/3 == 3", bool(true));
	}
	private void test(String input, Expression output){
		assertEquals(output, calculate(input));
	}
}
