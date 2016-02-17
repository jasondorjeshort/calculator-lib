package io.github.endreman0.calculator.expression;

import java.lang.reflect.Method;

import io.github.endreman0.calculator.expression.type.Type;
import io.github.endreman0.calculator.util.ReflectionUtils;
import io.github.endreman0.calculator.util.Utility;

public class StaticFunctionExpression extends Expression{
	private Expression[] args;
	private String function;
	public StaticFunctionExpression(String function, Expression... args){
		this.function = function; this.args = args;
	}
	@Override
	protected Object eval() throws ReflectiveOperationException{
		Type[] arguments = new Type[args.length];
		for(int i=0; i<args.length; i++) arguments[i] = args[i].evaluate();
		Method m = ReflectionUtils.staticFunction(function, arguments);
		return m != null ? Utility.wrap(m.invoke(null, (Object[])arguments)) : this;
	}
	
	@Override
	public boolean isEvaluatable(){
		Type[] arguments = new Type[args.length];
		for(int i=0; i<args.length; i++){
			if(!args[i].isEvaluatable()) return false;
			else arguments[i] = args[i].evaluate();
		}
		return ReflectionUtils.staticFunction(function, arguments) != null;
	}

	@Override
	public String toParseableString(){
		StringBuilder sb = new StringBuilder(function).append('(');
		for(int i=0; i<args.length; i++){
			sb.append(args[i].toParseableString());
			if(i+1 < args.length) sb.append(", ");
		}
		return sb.append(')').toString();
	}

	@Override
	public String toDisplayString(){
		StringBuilder sb = new StringBuilder(function).append('(');
		for(int i=0; i<args.length; i++){
			sb.append(args[i].toDisplayString());
			if(i+1 < args.length) sb.append(", ");
		}
		return sb.append(')').toString();
	}

	@Override
	public String toDescriptorString() {
		StringBuilder sb = new StringBuilder("StaticFunction[").append(function).append(',');
		for(int i=0; i<args.length; i++){
			sb.append(args[i].toDescriptorString());
			if(i+1 < args.length) sb.append(',');
		}
		return sb.append(']').toString();
	}

}
