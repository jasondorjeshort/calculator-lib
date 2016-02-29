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
		printProcessing();
	}
	private static void printExpressions(){
		printEvaluation(new OperatorExpression(MixedNumber.valueOf(1), "+", MixedNumber.valueOf(2, 3)));
		printEvaluation(new OperatorExpression(Decimal.valueOf(3), "^", Decimal.valueOf(2)));
		
		printEvaluation(new InstanceFunctionExpression(MixedNumber.valueOf(3), "reciprocal"));
		printEvaluation(new InstanceFunctionExpression(MixedNumber.valueOf(3), "equals", MixedNumber.valueOf(3)));
		printEvaluation(new InstanceFunctionExpression(MixedNumber.valueOf(3), "equals", MixedNumber.valueOf(4)));
		
		printEvaluation(new StaticFunctionExpression("abs", MixedNumber.valueOf(-5)));
		printEvaluation(new StaticFunctionExpression("abs", new InstanceFunctionExpression(Decimal.valueOf(-2), "reciprocal")));
		
		printEvaluation(new OperatorExpression(Variable.get("three"), "=", Decimal.valueOf(3)));
		printEvaluation(Variable.get("three"));
		printEvaluation(new InstanceFunctionExpression(Variable.get("three"), "reciprocal"));
		printEvaluation(new OperatorExpression(Variable.get("three"), "=", Decimal.valueOf(4)));
		printEvaluation(Variable.get("three"));
		printEvaluation(new OperatorExpression(
				Variable.get("three"),
				"=",
				new OperatorExpression(
						MixedNumber.valueOf(1),
						"+",
						MixedNumber.valueOf(2)
				)
		));
		printEvaluation(Variable.get("three"));
		
		printEvaluation(new OperatorExpression(Variable.get("five"), "=", new OperatorExpression(Variable.get("three"), "+", MixedNumber.valueOf(2))));
		printEvaluation(Variable.get("five"));
		printEvaluation(new OperatorExpression(Variable.get("one"), "=", new OperatorExpression(Variable.get("three"), "-", MixedNumber.valueOf(2))));
		printEvaluation(Variable.get("one"));
		printEvaluation(new OperatorExpression(Variable.get("three"), "=", new OperatorExpression(Variable.get("five"), "-", new OperatorExpression(Variable.get("one"), "*", MixedNumber.valueOf(2)))));
	}
	private static void printEvaluation(Expression e){
		System.out.println("\"" + e + "\" = " + e.evaluate());
	}
	
	private static void printParses(){
		printParsed("3 * 2");
		printParsed("3.reciprocal() * 2");
		printParsed("abs(3.5)");
		printParsed("[1, 2][3, 4]");
		printParsed("<3, 4>.magnitude()");
		printParsed("{x | x > 0}.contains(3.5)");
		printParsed("([1][2] + [3][4]) * 2.5");
	}
	private static void printParsed(String input){
		System.out.print(input); System.out.print(": [");
		String[] comps = Parser.parse(input);
		for(int i=0; i<comps.length; i++){
			System.out.print('"' + comps[i] + '"');
			if(i+1 < comps.length) System.out.print(", ");
		}
		System.out.println("]");
	}
	
	private static void printProcessing(){
		printProcessed("3", "*", "2");
		printProcessed("3", "*", "2", "+", "5");
	}
	private static void printProcessed(String... tokens){
		System.out.print('[');
		for(int i=0; i<tokens.length; i++){
			System.out.print('"' + tokens[i] + '"');
			if(i+1 < tokens.length) System.out.print(", ");
		}
		System.out.print("]: ");
		System.out.println(Processor.process(tokens).toDescriptorString());
	}
}
