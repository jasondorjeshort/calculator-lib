package io.github.endreman0.calculator.expression;

import java.lang.reflect.Method;

import io.github.endreman0.calculator.expression.type.Type;
import io.github.endreman0.calculator.util.ReflectionUtils;
import io.github.endreman0.calculator.util.Utility;

public class OperatorExpression extends Expression{
	private Expression e1, e2;
	private String op;
	public OperatorExpression(Expression i1, String op, Expression i2){
		this.e1 = i1; this.op = op; this.e2 = i2;
	}
	protected Type eval() throws ReflectiveOperationException{
		Type i1 = e1.evaluate(), i2 = e2.evaluate();
		Method m = ReflectionUtils.operator(i1, i2, op);
		return m == null ? null : Utility.wrap(m.invoke(i1, i2));
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
