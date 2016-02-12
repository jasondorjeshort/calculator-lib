package io.github.endreman0.calculator.expression;

import io.github.endreman0.calculator.expression.type.Type;

public abstract class Expression{
	public abstract Type evaluate();
	public abstract String toParseableString();
	public abstract String toDisplayString();
	public abstract String toDescriptorString();
}
