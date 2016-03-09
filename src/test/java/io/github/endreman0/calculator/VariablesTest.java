package io.github.endreman0.calculator;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import io.github.endreman0.calculator.expression.Variable;
import io.github.endreman0.calculator.expression.type.Decimal;
import io.github.endreman0.calculator.expression.type.MixedNumber;
import io.github.endreman0.calculator.expression.type.Type;

public class VariablesTest{
	@BeforeClass
	public static void setUpBeforeClass(){
		Variable.initConstants();
	}
	@Test
	public void test(){
		test("pi", Decimal.valueOf(Math.PI));
		test("pi * pi.reciprocal()", Decimal.valueOf(1));
		test("a = 3", MixedNumber.valueOf(3));
		test("a + 1", MixedNumber.valueOf(4));
	}
	private void test(String input, Type output){
		assertEquals(output, Calculator.calculate(input));
	}
}
