package io.github.endreman0.calculator.expression;

import io.github.endreman0.calculator.expression.type.Type;
import io.github.endreman0.calculator.util.Utility;

public abstract class Expression{
	public Type evaluate(){
		try{
			return Utility.wrap(eval());
		}catch(ReflectiveOperationException ex){ex.printStackTrace();return null;}
	}
	protected abstract Object eval() throws ReflectiveOperationException;
	public abstract boolean isEvaluatable();
	
	public abstract String toParseableString();
	public abstract String toDisplayString();
	public abstract String toDescriptorString();
	@Override public String toString(){return toDisplayString();}
}
