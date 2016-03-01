package io.github.endreman0.calculator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import io.github.endreman0.calculator.expression.Expression;
import io.github.endreman0.calculator.expression.OperatorExpression;
import io.github.endreman0.calculator.util.ReflectionUtils;

public class Processor{
	private static String[][] operators = {
			{"="},
			{"&&", "||"},
			{">", "<", ">=", "<=", "==", "!="},
			{"+", "-"},
			{"*", "/", "%"},
			{"^"}
	};
	public static Expression process(String... tokens){
		return process(Arrays.asList(tokens));
	}
	public static Expression process(List<String> tokens){
		for(int prec=0; prec<operators.length; prec++){//For each precedence bracket, ascending
			int index = -1;//Find last index of the operators in the current precedence (overall, find least precedent operator in expression)
			for(String operator : operators[prec]) index = Math.max(index, tokens.lastIndexOf(operator));
			
			if(index > -1){
				List<String> before = tokens.subList(0, index), after = tokens.subList(index+1, tokens.size());
				return new OperatorExpression(process(before), tokens.get(index), process(after));
			}
		}
		
		if(tokens.size() == 1){
			Method factory = ReflectionUtils.factory(tokens.get(0));
			try{
				return factory == null ? null : (Expression)factory.invoke(null, tokens.get(0));
			}catch(IllegalAccessException | InvocationTargetException ex){ex.printStackTrace(); return null;}
		}
		
		return null;
	}
}
