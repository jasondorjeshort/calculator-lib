package io.github.endreman0.calculator.expression.type;

import io.github.endreman0.calculator.expression.Expression;

public abstract class Type extends Expression{
	protected Object eval() throws ReflectiveOperationException{return this;}
	public boolean isEvaluatable(){return true;}
}
