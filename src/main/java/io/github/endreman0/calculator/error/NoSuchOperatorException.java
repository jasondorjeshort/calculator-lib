package io.github.endreman0.calculator.error;

import io.github.endreman0.calculator.token.type.Type;

public class NoSuchOperatorException extends EvaluatorException{
	private static final long serialVersionUID = 1L;
	public NoSuchOperatorException(Class<? extends Type> i1, Class<? extends Type> i2, String symbol){
		super("No operator \"" + symbol + "\" between " + i1.getSimpleName() + " and " + i2.getSimpleName());
	}
}
