package io.github.endreman0.calculator;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import io.github.endreman0.calculator.expression.Variable;
import io.github.endreman0.calculator.expression.type.Type;

public class VariablesTest extends BaseTest{
	@BeforeClass
	public static void setUpBeforeClass(){
		Variable.initConstants();
	}
	@Test
	public void test(){
		test("pi", decimal(Math.PI));
		test("pi * pi.reciprocal()", decimal(1));
		test("a = 3", integer(3));
		test("a + 1", integer(4));
	}
	private void test(String input, Type output){
		assertEquals(output, Calculator.calculate(input));
	}
}
