package io.github.endreman0.calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import io.github.endreman0.calculator.expression.Expression;
import io.github.endreman0.calculator.expression.InstanceFunctionExpression;
import io.github.endreman0.calculator.expression.OperatorExpression;
import io.github.endreman0.calculator.expression.StaticFunctionExpression;
import io.github.endreman0.calculator.expression.Variable;
import io.github.endreman0.calculator.expression.type.Type;
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
		try{
			return process(Arrays.asList(tokens));
		}catch(ReflectiveOperationException ex){
			return null;
		}
	}
	private static Expression process(List<String> tokens) throws ReflectiveOperationException{
		return process((Queue<String>)new LinkedList<>(tokens));
	}
	private static Expression process(Queue<String> tokens) throws ReflectiveOperationException{
		List<Object> operators = processFunctions(tokens);
		return processOperators(operators);
	}
	private static List<Object> processFunctions(Queue<String> tokens) throws ReflectiveOperationException{
		List<Object> operators = new ArrayList<>();//To return
		
		String token;
		while((token = tokens.peek()) != null && !token.equals(")") && !token.equals(",")){
			operators.add(processOperand(tokens));
			if(isOperator(tokens.peek())) operators.add(tokens.poll());
		}
		
		return operators;
	}
	private static Expression processOperand(Queue<String> tokens) throws ReflectiveOperationException{
		String t = tokens.poll();
		Expression obj = null;//To-be-returned
		if(ReflectionUtils.factory(t) != null){//Type literal
			obj = (Type)ReflectionUtils.factory(t).invoke(null, t);
		}else if(t.equals("(")){//Parentheses
			obj = process(tokens);
			tokens.poll();//Read ")"
		}else if(tokens.peek() != null && tokens.peek().equals("(")){//Static function
			List<Expression> argList = new ArrayList<>();
			while(tokens.peek() != null && !tokens.peek().equals(")")){//For each argument
				argList.add(process(tokens));
				if(tokens.peek() != null && tokens.peek().equals(",")) tokens.poll();//More arguments (remove comma)
				else break;//Out of arguments
			}
			tokens.poll();//Remove ")"
			
			Expression[] args = argList.toArray(new Expression[0]);
			obj = new StaticFunctionExpression(t, args);
		}else{//Variable
			obj = Variable.get(t);
		}
		
		//Process instance functions
		while(tokens.peek() != null && tokens.peek().equals(".")){
			tokens.poll();//Remove "."
			String fn = tokens.poll();
			tokens.poll();//Remove "("
			List<Expression> argsList = new ArrayList<>();
			while(tokens.peek() != null && !tokens.peek().equals(")")){
				argsList.add(process(tokens));
				if(tokens.peek() != null && tokens.peek().equals(",")) tokens.poll();
				else break;
			}
			tokens.poll();//Remove ")"
			
			obj = new InstanceFunctionExpression(obj, fn, argsList.toArray(new Expression[0]));
		}
		
		return obj;
	}
	private static Expression processOperators(List<Object> tokens){
		for(int prec=0; prec<operators.length; prec++){//For each precedence bracket, ascending
			int index = -1;//Find last index of the operators in the current precedence (overall, find least precedent operator in expression)
			for(String operator : operators[prec]) index = Math.max(index, tokens.lastIndexOf(operator));
			
			if(index > -1){
				List<Object> before = tokens.subList(0, index), after = tokens.subList(index+1, tokens.size());
				return new OperatorExpression(processOperators(before), (String)tokens.get(index), processOperators(after));
			}
		}
		
		if(tokens.size() == 1){
			return (Expression)tokens.get(0);
		}
		
		return null;
	}
	private static boolean isOperator(String token){
		for(String[] ops : operators)
			for(String op : ops)
				if(op.equals(token)) return true;
		return false;
	}
}
