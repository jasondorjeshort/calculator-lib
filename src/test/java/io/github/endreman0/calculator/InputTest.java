package io.github.endreman0.calculator;

import static io.github.endreman0.calculator.expression.type.MixedNumber.valueOf;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.github.endreman0.calculator.expression.Expression;

public class InputTest extends BaseTest{
	@Test
	public void testAdd(){
		assertEquals(valueOf(8), Calculator.calculate("3 + 5"));
		assertEquals(valueOf(2, 1, 5), Calculator.calculate("1_4/5 + 2/5"));
		assertEquals(valueOf(3, 4, 15), Calculator.calculate("2_1/5 + 1_1/15"));
	}
	@Test
	public void testSubtract(){
		assertEquals(valueOf(1, 4, 21), Calculator.calculate("3_1/3 - 2_1/7"));
		assertEquals(valueOf(0), Calculator.calculate("1/8 - 1/8"));
	}
	@Test
	public void testMultiply(){
		assertEquals(valueOf(5, 21), Calculator.calculate("5/7 * 1/3"));
		assertEquals(valueOf(3, 7), Calculator.calculate("1/7 * 3"));
	}
	@Test
	public void testDivide(){
		assertEquals(valueOf(5, 9), Calculator.calculate("5 / 9"));
		assertEquals(valueOf(11), Calculator.calculate("3_1/7 / 2/7"));
	}
	@Test
	public void testModulus(){
		assertEquals(valueOf(2, 9), Calculator.calculate("1_1/9 % 4/9"));
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
		assertEquals(output, Calculator.calculate(input));
	}
}
