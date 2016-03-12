package io.github.endreman0.calculator.expression.type;

import io.github.endreman0.calculator.annotation.Caster;
import io.github.endreman0.calculator.annotation.Factory;
import io.github.endreman0.calculator.annotation.Function;
import io.github.endreman0.calculator.annotation.Operator;
import io.github.endreman0.calculator.util.Patterns;
import io.github.endreman0.calculator.util.Utility;

public class Decimal extends NumericType{
	private double value;
	private Decimal(double value){
		this.value = value;
	}
	
	@Operator("+") public Decimal add(NumericType other){return valueOf(value + Utility.checkNull(other, "Cannot add null").value());}
	@Operator("-") public Decimal subtract(NumericType other){return valueOf(value - Utility.checkNull(other, "Cannot subtract null").value());}
	@Operator("*") public Decimal multiply(NumericType other){return valueOf(value * Utility.checkNull(other, "Cannot multiply by null").value());}
	@Operator("/") public Decimal divide(NumericType other){return valueOf(value / Utility.checkNull(other, "Cannot divide by null").value());}
	@Operator("%") public Decimal modulus(NumericType other){return valueOf(value % Utility.checkNull(other, "Cannot modulate by null").value());}
	@Operator("^") public Decimal exponentate(NumericType other){return valueOf(Math.pow(value, Utility.checkNull(other, "Cannot exponentate by null").value()));}
	@Operator("<") public boolean lessThan(Decimal other){return value < Utility.checkNull(other).value;}
	@Operator(">") public boolean greaterThan(Decimal other){return value > Utility.checkNull(other).value;}
	@Operator("<=") public boolean lessThanOrEqual(Decimal other){return value <= Utility.checkNull(other).value;}
	@Operator(">=") public boolean greaterThanOrEqual(Decimal other){return value >= Utility.checkNull(other).value;}
	@Operator("==") public boolean equals(Decimal other){return value == Utility.checkNull(other).value;}
	@Operator("!=") public boolean unequals(Decimal other){return value != Utility.checkNull(other).value;}
	
	public Decimal abs(){return abs(this);}
	@Function public static Decimal abs(Decimal input){return valueOf(Math.abs(Utility.checkNull(input).value));}
	@Function
	public Decimal reciprocal(){
		if(value == 0) throw new IllegalArgumentException("Cannot take reciprocal of 0");
		else return valueOf(1 / value);
	}
	@Function public static Decimal sqrt(Decimal input){return valueOf(Math.sqrt(Utility.checkNull(input).value));}
	@Function public static Decimal toDegrees(Decimal input){return valueOf(Math.toDegrees(Utility.checkNull(input).value));}
	@Function public static Decimal toRadians(Decimal input){return valueOf(Math.toRadians(Utility.checkNull(input).value));}
	
	private static double checkRads(Decimal input){return Math.toRadians(Utility.checkNull(input).value);}
	@Function public static Decimal sin(Decimal input){return valueOf(Math.sin(checkRads(input)));}
	@Function public static Decimal cos(Decimal input){return valueOf(Math.cos(checkRads(input)));}
	@Function public static Decimal tan(Decimal input){return valueOf(Math.tan(checkRads(input)));}
	@Function public static Decimal csc(Decimal input){return valueOf(1 / Math.sin(checkRads(input)));}
	@Function public static Decimal sec(Decimal input){return valueOf(1 / Math.cos(checkRads(input)));}
	@Function public static Decimal cot(Decimal input){return valueOf(1 / Math.tan(checkRads(input)));}
	
	private static Decimal toDegrees(double input){return valueOf(Math.toDegrees(input));}
	@Function public static Decimal asin(Decimal input){return toDegrees(Math.asin(Utility.checkNull(input).value));}
	@Function public static Decimal acos(Decimal input){return toDegrees(Math.acos(Utility.checkNull(input).value));}
	@Function public static Decimal atan(Decimal input){return toDegrees(Math.atan(Utility.checkNull(input).value));}
	@Function public static Decimal acsc(Decimal input){return toDegrees(1 / Math.asin(Utility.checkNull(input).value));}
	@Function public static Decimal asec(Decimal input){return toDegrees(1 / Math.acos(Utility.checkNull(input).value));}
	@Function public static Decimal acot(Decimal input){return toDegrees(1 / Math.atan(Utility.checkNull(input).value));}
	
	@Caster @Function
	public MixedNumber toMixedNumber(){return MixedNumber.valueOf(value);}
	
	public double value(){return value;}
	public String toParseableString(){return String.valueOf(value);}
	public String toDescriptorString(){return "Decimal[" + value + "]";}
	public String toDisplayString(){return toParseableString();}
	public int hashCode(){return (int)value;}
	public boolean equals(Object obj){return obj instanceof Decimal && value == ((Decimal)obj).value;}
	public boolean isInteger(){return value % 1 == 0;}
	public Decimal clone(){return valueOf(value);}
	
	public static Decimal valueOf(double value){return new Decimal(value);}
	@Factory({Patterns.DECIMAL})
	public static Decimal valueOf(String string){return new Decimal(Double.parseDouble(string));}
}
