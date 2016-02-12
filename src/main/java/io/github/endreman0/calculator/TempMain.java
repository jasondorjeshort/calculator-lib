package io.github.endreman0.calculator;

import io.github.endreman0.calculator.expression.Expression;
import io.github.endreman0.calculator.expression.OperatorExpression;
import io.github.endreman0.calculator.expression.type.MixedNumber;

public class TempMain{
	public static void main(String[] args){
		MixedNumber one = MixedNumber.valueOf(1);
		MixedNumber twoThirds = MixedNumber.valueOf(2, 3);
		System.out.println(one);
		System.out.println(twoThirds);
		Expression e = new OperatorExpression(one, "+", twoThirds);
		System.out.println(e.evaluate());
	}
}
