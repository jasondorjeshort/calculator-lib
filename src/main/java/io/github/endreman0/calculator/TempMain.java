package io.github.endreman0.calculator;

import io.github.endreman0.calculator.expression.Expression;
import io.github.endreman0.calculator.expression.InstanceFunctionExpression;
import io.github.endreman0.calculator.expression.OperatorExpression;
import io.github.endreman0.calculator.expression.StaticFunctionExpression;
import io.github.endreman0.calculator.expression.type.Decimal;
import io.github.endreman0.calculator.expression.type.MixedNumber;

public class TempMain{
	public static void main(String[] args){
		print(new OperatorExpression(MixedNumber.valueOf(1), "+", MixedNumber.valueOf(2, 3)));
		print(new OperatorExpression(Decimal.valueOf(3), "^", Decimal.valueOf(2)));
		
		print(new InstanceFunctionExpression(MixedNumber.valueOf(3), "reciprocal"));
		print(new InstanceFunctionExpression(MixedNumber.valueOf(3), "equals", MixedNumber.valueOf(3)));
		print(new InstanceFunctionExpression(MixedNumber.valueOf(3), "equals", MixedNumber.valueOf(4)));
		
		print(new StaticFunctionExpression("abs", MixedNumber.valueOf(-5)));
		print(new StaticFunctionExpression("abs", new InstanceFunctionExpression(Decimal.valueOf(-2), "reciprocal")));
	}
	private static void print(Expression e){
		System.out.println(e + " = " + e.evaluate());
	}
}
