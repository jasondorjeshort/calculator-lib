package io.github.endreman0.calculator;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import io.github.endreman0.calculator.error.CalculatorException;
import io.github.endreman0.calculator.token.type.Decimal;
import io.github.endreman0.calculator.token.type.MixedNumber;
import io.github.endreman0.calculator.token.type.Type;

public class VariablesTest{
	@BeforeClass
	public static void setUpBeforeClass(){
		Calculator.initializeVariables();
	}
	@Test
	public void test() throws CalculatorException{
		test("pi", Decimal.valueOf(Math.PI));
		test("pi * pi.reciprocal()", Decimal.valueOf(1));
		test("a = 3", MixedNumber.valueOf(3));
		test("a + 1", MixedNumber.valueOf(4));
	}
	private void test(String input, Type output) throws CalculatorException{
		assertEquals(output, Calculator.calculate(input));
	}
}
