package io.github.endreman0.calculator.expression.type;

import io.github.endreman0.calculator.annotation.Factory;
import io.github.endreman0.calculator.annotation.Function;
import io.github.endreman0.calculator.annotation.Operator;
import io.github.endreman0.calculator.util.Patterns;
import io.github.endreman0.calculator.util.Utility;

public class Decimal extends Type{
	private double value;
	private Decimal(double value){
		this.value = value;
	}
	
	@Operator("+") public Decimal add(Decimal other){return valueOf(value + Utility.checkNull(other, "Cannot add null").value);}
	@Operator("-") public Decimal subtract(Decimal other){return valueOf(value - Utility.checkNull(other, "Cannot subtract null").value);}
	@Operator("*") public Decimal multiply(Decimal other){return valueOf(value * Utility.checkNull(other, "Cannot multiply by null").value);}
	@Operator("/") public Decimal divide(Decimal other){return valueOf(value / Utility.checkNull(other, "Cannot divide by null").value);}
	@Operator("%") public Decimal modulus(Decimal other){return valueOf(value % Utility.checkNull(other, "Cannot modulate by null").value);}
	@Operator("^") public Decimal exponentate(Decimal other){return valueOf(Math.pow(value, Utility.checkNull(other, "Cannot exponentate by null").value));}
	
	@Function public static Decimal abs(Decimal input){return valueOf(Math.abs(Utility.checkNull(input).value));}
	@Function
	public Decimal reciprocal(){
		if(value == 0) throw new IllegalArgumentException("Cannot take reciprocal of 0");
		else return valueOf(1 / value);
	}
	@Function public static Decimal sqrt(Decimal input){return valueOf(Math.sqrt(Utility.checkNull(input).value));}
	@Function public static Decimal toDegrees(Decimal input){return valueOf(Math.toDegrees(Utility.checkNull(input).value));}
	@Function public static Decimal toRadians(Decimal input){return valueOf(Math.toRadians(Utility.checkNull(input).value));}
	
	@Function public static Decimal sin(Decimal input){return valueOf(Math.sin(Utility.checkNull(input).value));}
	@Function public static Decimal cos(Decimal input){return valueOf(Math.cos(Utility.checkNull(input).value));}
	@Function public static Decimal tan(Decimal input){return valueOf(Math.tan(Utility.checkNull(input).value));}
	@Function public static Decimal csc(Decimal input){return valueOf(1 / Math.sin(Utility.checkNull(input).value));}
	@Function public static Decimal sec(Decimal input){return valueOf(1 / Math.cos(Utility.checkNull(input).value));}
	@Function public static Decimal cot(Decimal input){return valueOf(1 / Math.tan(Utility.checkNull(input).value));}
	
	@Function public static Decimal asin(Decimal input){return valueOf(Math.asin(Utility.checkNull(input).value));}
	@Function public static Decimal acos(Decimal input){return valueOf(Math.acos(Utility.checkNull(input).value));}
	@Function public static Decimal atan(Decimal input){return valueOf(Math.atan(Utility.checkNull(input).value));}
	@Function public static Decimal acsc(Decimal input){return valueOf(1 / Math.asin(Utility.checkNull(input).value));}
	@Function public static Decimal asec(Decimal input){return valueOf(1 / Math.acos(Utility.checkNull(input).value));}
	@Function public static Decimal acot(Decimal input){return valueOf(1 / Math.atan(Utility.checkNull(input).value));}
	
	public static Decimal cast(MixedNumber input){return valueOf(Utility.checkNull(input).value());}
	
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
