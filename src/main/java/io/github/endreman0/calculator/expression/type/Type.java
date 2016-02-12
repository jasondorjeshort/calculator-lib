package io.github.endreman0.calculator.expression.type;

import io.github.endreman0.calculator.expression.Expression;

public abstract class Type extends Expression{
	@Override public Type evaluate(){return this;}
}
