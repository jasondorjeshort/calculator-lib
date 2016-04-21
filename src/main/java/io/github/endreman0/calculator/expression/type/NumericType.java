package io.github.endreman0.calculator.expression.type;

public abstract class NumericType extends Type implements Comparable<NumericType>{
	public abstract double value();
	public abstract NumericType add(NumericType other);
	public abstract NumericType subtract(NumericType other);
	public abstract NumericType multiply(NumericType other);
	public abstract NumericType divide(NumericType other);
	public abstract NumericType modulus(NumericType other);
	public abstract NumericType abs();
	public abstract NumericType reciprocal();
	@Override
	public int compareTo(NumericType other){
		double myVal = value(), otherVal = other.value();
		if(myVal < otherVal) return -1;
		else if(myVal > otherVal) return 1;
		else return 0;
	}
}
