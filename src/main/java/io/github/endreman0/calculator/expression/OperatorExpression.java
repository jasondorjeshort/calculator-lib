package io.github.endreman0.calculator.expression;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.github.endreman0.calculator.expression.type.Type;
import io.github.endreman0.calculator.util.ReflectionUtils;

public class OperatorExpression extends Expression{
	private Expression e1, e2;
	private String op;
	public OperatorExpression(Expression i1, String op, Expression i2){
		this.e1 = i1; this.op = op; this.e2 = i2;
	}
	@Override
	public Type evaluate(){
		Type i1 = e1.evaluate(), i2 = e2.evaluate();
		Method m = ReflectionUtils.operator(i1, i2, op);
		try{
			return m == null ? null : (Type)m.invoke(i1, i2); 
		}catch(IllegalAccessException | InvocationTargetException ex){
			return null;
		}
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
