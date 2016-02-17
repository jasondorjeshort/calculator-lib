package io.github.endreman0.calculator.expression;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.github.endreman0.calculator.expression.type.Decimal;
import io.github.endreman0.calculator.expression.type.MixedNumber;
import io.github.endreman0.calculator.expression.type.Switch;
import io.github.endreman0.calculator.expression.type.Type;

public class ExpressionTests{
	@Test
	public void testOperators(){
		test(new OperatorExpression(MixedNumber.valueOf(1), "+", MixedNumber.valueOf(2, 3)), MixedNumber.valueOf(1, 2, 3));
		test(new OperatorExpression(Decimal.valueOf(3), "^", Decimal.valueOf(2)), Decimal.valueOf(9));
	}
	@Test
	public void testInstanceFunctions(){
		test(new InstanceFunctionExpression(MixedNumber.valueOf(3), "reciprocal"), MixedNumber.valueOf(1, 3));
		test(new InstanceFunctionExpression(MixedNumber.valueOf(3), "equals", MixedNumber.valueOf(3)), Switch.valueOf(true));
		test(new InstanceFunctionExpression(MixedNumber.valueOf(3), "equals", MixedNumber.valueOf(4)), Switch.valueOf(false));
		
	}
	@Test
	public void testStaticFunctions(){
		test(new StaticFunctionExpression("abs", MixedNumber.valueOf(-5)), MixedNumber.valueOf(5));
		test(new StaticFunctionExpression("abs", Decimal.valueOf(-5)), Decimal.valueOf(5));
	}
	@Test
	public void testCompound(){
		test(new StaticFunctionExpression("abs", new InstanceFunctionExpression(Decimal.valueOf(-2), "reciprocal")), Decimal.valueOf(0.5));
	}
	@Test
	public void testVariables() throws Throwable{
		test(new OperatorExpression(Variable.get("three"), "=", Decimal.valueOf(3)), Decimal.valueOf(3));
		test(Variable.get("three"), Decimal.valueOf(3));
		test(new InstanceFunctionExpression(Variable.get("three"), "reciprocal"), Decimal.valueOf(1D/3));
		test(new OperatorExpression(Variable.get("three"), "=", Decimal.valueOf(4)), Decimal.valueOf(4));
		test(Variable.get("three"), Decimal.valueOf(4));
		test(new OperatorExpression(
				Variable.get("three"),
				"=",
				new OperatorExpression(
						MixedNumber.valueOf(1),
						"+",
						MixedNumber.valueOf(2)
				)
		), MixedNumber.valueOf(3));
		test(Variable.get("three"), MixedNumber.valueOf(3));
	}
	private void test(Expression e, Type result){
		assertEquals(result, e.evaluate());
	}
}
