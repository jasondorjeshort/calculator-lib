package io.github.endreman0.calculator.expression;

import java.lang.reflect.Method;
import java.util.Arrays;

import io.github.endreman0.calculator.expression.type.Type;
import io.github.endreman0.calculator.util.ReflectionUtils;
import io.github.endreman0.calculator.util.Utility;

public class InstanceFunctionExpression extends Expression{
	private Expression obj;
	private Expression[] args;
	private String function;
	public InstanceFunctionExpression(Expression obj, String function, Expression... args){
		this.obj = obj; this.function = function; this.args = args;
	}
	public Expression object(){return obj;}
	public String function(){return function;}
	public Expression[] arguments(){return args.clone();}
	protected Object eval() throws ReflectiveOperationException{
		Type object = obj.evaluate();
		Type[] arguments = new Type[args.length];
		for(int i=0; i<args.length; i++) arguments[i] = args[i].evaluate();
		Method m = ReflectionUtils.instanceFunction(object, function, arguments);
		return m != null ? Utility.wrap(m.invoke(object, (Object[])arguments)) : this;
	}
	@Override
	public boolean isEvaluatable(){
		if(!obj.isEvaluatable()) return false;//Object must evaluate
		Type[] arguments = new Type[args.length];
		for(int i=0; i<args.length; i++){
			if(!args[i].isEvaluatable()) return false;//Each argument must evaluate
			else arguments[i] = args[i].evaluate();
		}
		if(ReflectionUtils.instanceFunction(obj.evaluate(), function, arguments) == null) return false;//Function must exist
		return true;
	}

	@Override
	public String toParseableString(){
		StringBuilder sb = new StringBuilder(obj.toParseableString()).append('.').append(function).append('(');
		for(int i=0; i<args.length; i++){
			sb.append(args[i].toParseableString());
			if(i+1 < args.length) sb.append(", ");
		}
		return sb.append(')').toString();
	}

	@Override
	public String toDisplayString(){
		StringBuilder sb = new StringBuilder(obj.toDisplayString()).append('.').append(function).append('(');
		for(int i=0; i<args.length; i++){
			sb.append(args[i].toDisplayString());
			if(i+1 < args.length) sb.append(", ");
		}
		return sb.append(')').toString();
	}

	@Override
	public String toDescriptorString(){
		StringBuilder sb = new StringBuilder("InstanceFunction[").append(obj.toDescriptorString()).append(',').append(function).append(',');
		for(int i=0; i<args.length; i++){
			sb.append(args[i].toDescriptorString());
			if(i+1 < args.length) sb.append(',');
		}
		return sb.append(']').toString();
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof InstanceFunctionExpression)) return false;
		InstanceFunctionExpression exp = (InstanceFunctionExpression)o;
		return obj.equals(exp.obj) && function.equals(exp.function) && Arrays.equals(args, exp.args);
	}
}
