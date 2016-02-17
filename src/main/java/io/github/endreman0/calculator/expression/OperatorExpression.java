package io.github.endreman0.calculator.expression;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import io.github.endreman0.calculator.expression.type.Type;
import io.github.endreman0.calculator.util.ReflectionUtils;

public class OperatorExpression extends Expression{
	private List<String> commutativeOperators = Arrays.asList("+", "*");
	private Expression e1, e2;
	private String op;
	public OperatorExpression(Expression i1, String op, Expression i2){
		this.e1 = i1; this.op = op; this.e2 = i2;
	}
	protected Object eval() throws ReflectiveOperationException{
		Type i1 = e1.evaluate(), i2 = e2.evaluate();
		boolean commutative = commutativeOperators.contains(op);
		Method m;
		//Attempt 1: apply to results of expressions
		if((m = ReflectionUtils.operator(i1, i2, op)) != null) return m.invoke(i1, i2);
		else if(commutative && (m = ReflectionUtils.operator(i2, i1, op)) != null) return m.invoke(i2, i1);
		//Attempt 2: apply to both expressions (setting variables to constants)
		else if((m = ReflectionUtils.operator(e1, e2, op)) != null) return m.invoke(e1, e2);
		else if(commutative && (m = ReflectionUtils.operator(e2, e1, op)) != null) return m.invoke(e2, e1);
		//Attempt 3: apply to the first evaluated and the second expression (no practical use; included for completeness)
		else if((m = ReflectionUtils.operator(i1, e2, op)) != null) return m.invoke(i1, e2);
		else if(commutative && (m = ReflectionUtils.operator(e2, i1, op)) != null) return m.invoke(e2, i1);
		//Attempt 4: apply to first expression and second evaluated (setting variables to expressions)
		else if((m = ReflectionUtils.operator(e1, i2, op)) != null) return m.invoke(e1, i2);
		else if(commutative && (m = ReflectionUtils.operator(i2, e1, op)) != null) return m.invoke(e1, i2);
		//All attempts failed: return this (unevaluatable)
		else return this;
	}
	@Override
	public boolean isEvaluatable(){
		if(!e1.isEvaluatable() || !e2.isEvaluatable()) return false;//Arguments must be evalutable
		//Find if operator exists
		Type i1 = e1.evaluate(), i2 = e2.evaluate();
		boolean commutative = commutativeOperators.contains(op);
		//Attempt 1: apply to results of expressions
		if(ReflectionUtils.operator(i1, i2, op) != null) return true;
		else if(commutative && ReflectionUtils.operator(i2, i1, op) != null) return true;
		//Attempt 2: apply to both expressions (setting variables to constants)
		else if(ReflectionUtils.operator(e1, e2, op) != null) return true;
		else if(commutative && ReflectionUtils.operator(e2, e1, op) != null) return true;
		//Attempt 3: apply to the first evaluated and the second expression (no practical use; included for completeness)
		else if(ReflectionUtils.operator(i1, e2, op) != null) return true;
		else if(commutative && ReflectionUtils.operator(e2, i1, op) != null) return true;
		//Attempt 4: apply to first expression and second evaluated (setting variables to expressions)
		else if(ReflectionUtils.operator(e1, i2, op) != null) return true;
		else if(commutative && ReflectionUtils.operator(i2, e1, op) != null) return true;
		else return false;
	}
	@Override
	public String toParseableString(){
		return e1 + " " + op + " " + e2;
	}
	@Override
	public String toDisplayString(){
		return toParseableString();
	}
	@Override
	public String toDescriptorString(){
		return "OperatorExpression[" + e1.toDescriptorString() + "," + op + "," + e1.toDescriptorString() + "]";
	}
}
