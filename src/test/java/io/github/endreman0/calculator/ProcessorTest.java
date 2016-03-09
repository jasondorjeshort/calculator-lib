package io.github.endreman0.calculator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.github.endreman0.calculator.expression.Expression;

public class ProcessorTest extends BaseTest{
	@Test
	public void testOperators(){
		test("3 * 2", op(integer(3), "*", integer(2)));
		test("3 * 2 + 5", op(op(integer(3), "*", integer(2)), "+", integer(5)));
		test("seven = 2 * 2 + 1 * 3", op(
			var("seven"), "=", op(
				op(integer(2), "*", integer(2)),
				"+",
				op(integer(1), "*", integer(3))
			)
		));
	}
	
	@Test
	public void testInstanceFunctions(){
		test("5.reciprocal()", fn(integer(5), "reciprocal"));
	}
	
	@Test
	public void testStaticFunctions(){
		test("abs(-2.0)", fn("abs", decimal(-2)));
	}
	private void test(String input, Expression output){
		assertEquals(output, Processor.process(Parser.parse(input)));
	}
}
