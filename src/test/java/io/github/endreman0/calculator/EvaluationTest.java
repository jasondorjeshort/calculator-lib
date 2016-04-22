package io.github.endreman0.calculator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.github.endreman0.calculator.expression.Expression;
import io.github.endreman0.calculator.expression.Variable;
import io.github.endreman0.calculator.expression.type.Type;

public class EvaluationTest extends BaseTest{
	@Test
	public void testOperators(){
		test(op(integer(3), "+", integer(2)), integer(5));
		test(op(decimal(3), "^", decimal(4)), decimal(81));
	}
	
	@Test
	public void testInstanceFunctions(){
		test(fn(decimal(3), "reciprocal"), decimal(1D/3));
		test(fn(integer(3), "equals", integer(3)), bool(true));
		test(fn(integer(3), "equals", integer(4)), bool(false));
	}
	@Test
	public void testStaticFunctions(){
		test(fn("abs", decimal(-6)), decimal(6));
		test(fn("not", bool(false)), bool(true));
		test(fn("abs", fn(decimal(-2), "reciprocal")), decimal(0.5));
	}
	@Test
	public void testVariables(){
		test(op(var("three"), "=", decimal(3)), decimal(3));
		test(var("three"), decimal(3));
		test(fn(var("three"), "reciprocal"), decimal(1D/3));
		test(op(var("three"), "=", decimal(4)), decimal(4));
		test(var("three"), decimal(4));
		test(op(var("three"), "=", op(integer(1), "+", integer(2))), integer(3)); //three = 1 + 2
		test(var("three"), integer(3));
		
		test(op(var("five"), "=", op(var("three"), "+", integer(2))), integer(5));
		test(var("five"), integer(5));
		test(op(var("one"), "=", op(var("three"), "-", integer(2))), integer(1));
		test(Variable.get("one"), integer(1));
		test(op(var("three"), "=", op(var("five"), "-", op(var("one"), "*", integer(2)))), integer(3));//three = five - one * 2
		test(var("three"), integer(3));
	}
	
	private void test(Expression e, Type ret){
		assertEquals(ret, e.evaluate());
	}
}
