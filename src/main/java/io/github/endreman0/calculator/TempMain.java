package io.github.endreman0.calculator;

import io.github.endreman0.calculator.expression.Expression;
import io.github.endreman0.calculator.expression.InstanceFunctionExpression;
import io.github.endreman0.calculator.expression.OperatorExpression;
import io.github.endreman0.calculator.expression.StaticFunctionExpression;
import io.github.endreman0.calculator.expression.Variable;
import io.github.endreman0.calculator.expression.type.Decimal;
import io.github.endreman0.calculator.expression.type.MixedNumber;

public class TempMain{
	public static void main(String[] args){
		printParses();
	}
	private static void printExpressions(){
		print(new OperatorExpression(MixedNumber.valueOf(1), "+", MixedNumber.valueOf(2, 3)));
		print(new OperatorExpression(Decimal.valueOf(3), "^", Decimal.valueOf(2)));
		
		print(new InstanceFunctionExpression(MixedNumber.valueOf(3), "reciprocal"));
		print(new InstanceFunctionExpression(MixedNumber.valueOf(3), "equals", MixedNumber.valueOf(3)));
		print(new InstanceFunctionExpression(MixedNumber.valueOf(3), "equals", MixedNumber.valueOf(4)));
		
		print(new StaticFunctionExpression("abs", MixedNumber.valueOf(-5)));
		print(new StaticFunctionExpression("abs", new InstanceFunctionExpression(Decimal.valueOf(-2), "reciprocal")));
		
		print(new OperatorExpression(Variable.get("three"), "=", Decimal.valueOf(3)));
		print(Variable.get("three"));
		print(new InstanceFunctionExpression(Variable.get("three"), "reciprocal"));
		print(new OperatorExpression(Variable.get("three"), "=", Decimal.valueOf(4)));
		print(Variable.get("three"));
		print(new OperatorExpression(
				Variable.get("three"),
				"=",
				new OperatorExpression(
						MixedNumber.valueOf(1),
						"+",
						MixedNumber.valueOf(2)
				)
		));
		print(Variable.get("three"));
		
		print(new OperatorExpression(Variable.get("five"), "=", new OperatorExpression(Variable.get("three"), "+", MixedNumber.valueOf(2))));
		print(Variable.get("five"));
		print(new OperatorExpression(Variable.get("one"), "=", new OperatorExpression(Variable.get("three"), "-", MixedNumber.valueOf(2))));
		print(Variable.get("one"));
		print(new OperatorExpression(Variable.get("three"), "=", new OperatorExpression(Variable.get("five"), "-", new OperatorExpression(Variable.get("one"), "*", MixedNumber.valueOf(2)))));
	}
	private static void printParses(){
		print(Parser.parse("3 * 2"));
		print(Parser.parse("3.reciprocal() * 2"));
		print(Parser.parse("abs(3.5)"));
		print(Parser.parse("[1, 2][3, 4]"));
		print(Parser.parse("<3, 4>.magnitude()"));
		print(Parser.parse("{x | x > 0}.contains(3.5)"));
		print(Parser.parse("([1][2] + [3][4]) * 2.5"));
	}
	private static void print(Expression e){
		System.out.println("\"" + e + "\" = " + e.evaluate());
	}
	private static void print(String[] comps){
		for(int i=0; i<comps.length; i++){
			System.out.print('"' + comps[i] + '"');
			if(i+1 < comps.length) System.out.print(", ");
		}
		System.out.println();
	}
}
