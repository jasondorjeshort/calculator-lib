package io.github.endreman0.calculator.expression.type;

public abstract class NumericType extends Type{
	public abstract double value();
	public abstract NumericType add(NumericType other);
	public abstract NumericType subtract(NumericType other);
	public abstract NumericType multiply(NumericType other);
	public abstract NumericType divide(NumericType other);
	public abstract NumericType modulus(NumericType other);
	public abstract NumericType abs();
	public abstract NumericType reciprocal();
}
