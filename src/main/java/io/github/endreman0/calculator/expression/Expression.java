package io.github.endreman0.calculator.expression;

import io.github.endreman0.calculator.expression.type.Type;

public abstract class Expression{
	public Type evaluate(){
		try{
			return eval();
		}catch(ReflectiveOperationException ex){return null;}
	}
	protected abstract Type eval() throws ReflectiveOperationException;
	
	public abstract String toParseableString();
	public abstract String toDisplayString();
	public abstract String toDescriptorString();
	@Override public String toString(){return toDisplayString();}
}
