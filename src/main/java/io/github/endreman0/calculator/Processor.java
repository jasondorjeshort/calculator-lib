package io.github.endreman0.calculator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.endreman0.calculator.expression.Expression;
import io.github.endreman0.calculator.expression.OperatorExpression;
import io.github.endreman0.calculator.util.ReflectionUtils;

public class Processor{
	public static Expression process(String... tokens){
		return process(new ArrayList<>(Arrays.asList(tokens)));
	}
	public static Expression process(List<String> tokens){
		int ind = Math.max(tokens.lastIndexOf("+"), tokens.lastIndexOf("-"));
		if(ind > -1){
			List<String> before = tokens.subList(0, ind), after = tokens.subList(ind+1, tokens.size());
			return new OperatorExpression(process(before), tokens.get(ind), process(after));
		}
		
		ind = Math.max(tokens.lastIndexOf("*"), tokens.lastIndexOf("/"));
		if(ind > -1){
			List<String> before = tokens.subList(0, ind), after = tokens.subList(ind+1, tokens.size());
			return new OperatorExpression(process(before), tokens.get(ind), process(after));
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
